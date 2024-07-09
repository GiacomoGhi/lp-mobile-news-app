package it.unibo.android.lab.newsapp.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import it.unibo.android.lab.newsapp.models.NewsBody
import it.unibo.android.lab.newsapp.models.NewsResponse
import it.unibo.android.lab.newsapp.util.Resource
import it.unibo.android.lab.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class NewsViewModel (
    app: Application,
    private val newsRepository: NewsRepository
): AndroidViewModel(app){

    private val TAG = "NewsViewModel"

    val newsResponse: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    init {
        getMarketNews()
    }

    fun getMarketNews() = viewModelScope.launch {
        getNews()
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful && response.body() != null) { //checks if network request is successful
            return Resource.Success(response.body() as NewsResponse)
        }
        return Resource.Error(response.message())
    }

    fun addToFavourites(article: NewsBody) = viewModelScope.launch {
        newsRepository.upsert(article) //article being added to favourites
    }

    fun getFavouriteNews() = newsRepository.getFavouriteNews()

    fun deleteArticle(article: NewsBody) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    // This function checks if internet connection is available
    private fun internetConnection(context: Context): Boolean {
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

}