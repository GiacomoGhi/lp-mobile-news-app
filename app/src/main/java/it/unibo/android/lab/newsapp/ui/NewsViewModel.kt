package it.unibo.android.lab.newsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import it.unibo.android.lab.newsapp.models.Article
import it.unibo.android.lab.newsapp.models.NewsResponse
import it.unibo.android.lab.newsapp.util.Resource
import it.unibo.android.lab.newsapp.models.NewsResponse1
import it.unibo.android.lab.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class NewsViewModel (
    app: Application,
    val newsRepository: NewsRepository
): AndroidViewModel(app){
    
    private val TAG = "NewsViewModel"

    val headLines: MutableLiveData<Resource<NewsResponse1>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: NewsResponse1? = null

    val newsResponse: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    val searchNews: MutableLiveData<Resource<NewsResponse1>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse1: NewsResponse1? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null

    init {
        getHeadlines("us")
        getMarketNews()
    }

    fun getHeadlines(countryCode: String) = viewModelScope.launch {
        headlinesInternet(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsInternet(searchQuery)
    }

    fun getMarketNews() = viewModelScope.launch {
        getNews()
    }


    private fun handleHeadlinesResponse(response: Response<NewsResponse1>): Resource<NewsResponse1>{
        if (response.isSuccessful){ //checks if network request is successful
            response.body()?.let {resultResponse ->
                headlinesPage++ // If so, increments the variable
                if (headlinesResponse == null){
                    headlinesResponse = resultResponse
                // If headlineResponse is empty, assign it te value of resultResponse
                } else {
                    // Otherwise merge new articles with existing ones
                    val oldArticles = headlinesResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(headlinesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful && response.body() != null){ //checks if network request is successful
                return Resource.Success(response.body() as NewsResponse)
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse1>) : Resource<NewsResponse1> {
        if (response.isSuccessful) { // Checks again if the network request is ok
            response.body()?.let { resultResponse ->
                // Searches if newsResponse is null or the searchQuery has changed
                if (searchNewsResponse1 == null || newSearchQuery != oldSearchQuery) {
                    // If so, resets the page count to 1
                    // then updates tue query and assigns the result to searchNewsResponse
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse1 = resultResponse
                } else {
                    // If not, increments the page count and merges the new articles w the existing ones
                    // in searchNewsResponse
                    searchNewsPage++
                    val oldArticles = searchNewsResponse1?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse1 ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    fun addToFavourites(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article) //article being added to favourites
    }

    fun getFavouriteNews() = newsRepository.getFavouriteNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    // This function checks if internet connection is available
    fun internetConnection(context: Context): Boolean {
        // Retrieve the connectivity system service and cast it to ConnectivityManager
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            // Get the networkCapab. of the currently active network. If the expression is null returns false
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    // Else checks if these transport types are available, if not returns false
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    // Function that fetches headlines based on country code
    private suspend fun headlinesInternet(countryCode: String) {
        headLines.postValue(Resource.Loading())// Posts the loading state to the headlines live date
        try{
            // Checks if there is internet connection
            if (internetConnection(this.getApplication())) {
                // If so, response stores headLines fetched from the repository and posts the processed result
                val response = newsRepository.getHeadlines(countryCode, headlinesPage)
                headLines.postValue(handleHeadlinesResponse(response))
            } else {
                // Else return error
                headLines.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> headLines.postValue(Resource.Error("Unable to connect"))
                else -> headLines.postValue(Resource.Error("No signal"))
            }
        }
    }

    // Function that fetches market news
    private suspend fun getNews() {
        newsResponse.postValue(Resource.Loading())// Posts the loading state to the news live date
        try{
            // Checks if there is internet connection
            if (internetConnection(this.getApplication())) {
                // If so, response stores news fetched from the repository and posts the processed result
                val response = newsRepository.getNews()
                Log.d(TAG, response.toString())
                newsResponse.postValue(handleNewsResponse(response))
            } else {
                // Else return error
                newsResponse.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> newsResponse.postValue(Resource.Error("Unable to connect"))
                else -> newsResponse.postValue(Resource.Error("No signal"))
            }
        }
    }

    // Function that handles internet connection specifically for searchNews
    private suspend fun searchNewsInternet(searchQuery: String) {
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading()) //Posts the loading state
        try {
            if (internetConnection((this.getApplication()))) {
                // If there is connection fetches searchNews from the repo based on searchQuery
                // and posts it on searchNews
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resource.Error("Unable to connect"))
                else -> searchNews.postValue(Resource.Error("No signal"))
            }
        }
    }
}