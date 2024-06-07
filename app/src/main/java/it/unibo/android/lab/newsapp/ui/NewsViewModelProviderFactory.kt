package it.unibo.android.lab.newsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.unibo.android.lab.newsapp.repository.NewsRepository

/***********************************************************************************
   ViewmodelProvider.Factory is a class that instantiate and return view model
   This class is only used to create an instance of NewsViewModel
************************************************************************************/

class NewsViewModelProviderFactory(val app: Application, val newsRepository: NewsRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T // Ensuring that the written instance matches w/ generic type T
    }
}