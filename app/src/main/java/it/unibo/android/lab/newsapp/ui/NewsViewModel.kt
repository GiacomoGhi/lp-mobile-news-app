package it.unibo.android.lab.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import it.unibo.android.lab.newsapp.models.Article
import it.unibo.android.lab.newsapp.util.Resource
import it.unibo.android.lab.newsapp.models.NewsResponse
import it.unibo.android.lab.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel (app: Application, val newsRepository: NewsRepository): AndroidViewModel(app){

    val headLines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null

    private fun HandleHeadlinesResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
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

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) { // Checks again if the network request is ok
            response.body()?.let { resultResponse ->
                // Searches if newsResponse is null or the searchQuery has changed
                if (searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    // If so, resets the page count to 1
                    // then updates tue query and assigns the result to searchNewsResponse
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    // If not, increments the page count and merges the new articles w the existing ones
                    // in searchNewsResponse
                    searchNewsPage++
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
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
}