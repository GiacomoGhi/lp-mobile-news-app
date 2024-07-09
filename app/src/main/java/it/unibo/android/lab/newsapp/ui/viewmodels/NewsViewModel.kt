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

class NewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository
) : BaseViewModel<NewsResponse>(app, newsRepository) {

    override val response: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    init {
        super.getData()
    }

    override suspend fun fetchData() {
        getData { newsRepository.getNews() }
    }

    fun addToFavourites(article: NewsBody) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getFavouriteNews() = newsRepository.getFavouriteNews()

    fun deleteArticle(article: NewsBody) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
