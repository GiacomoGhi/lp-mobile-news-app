package it.unibo.android.lab.newsapp.ui

import androidx.lifecycle.MutableLiveData
import it.unibo.android.lab.newsapp.util.Resource
import it.unibo.android.lab.newsapp.models.NewsResponse

class NewsViewModel {

    val headLines: MutableLiveData<Resource<NewsResponse>>
    var headlinesPage = 1
    var headlinesResponse: NewsResponse? = null
}