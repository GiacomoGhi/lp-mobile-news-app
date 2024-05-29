package it.unibo.android.lab.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import it.unibo.android.lab.newsapp.util.Resource
import it.unibo.android.lab.newsapp.models.NewsResponse
import it.unibo.android.lab.newsapp.repository.NewsRepository

class NewsViewModel (app: Application, val newsRepository: NewsRepository): AndroidViewModel(app){

    val headLines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null
}