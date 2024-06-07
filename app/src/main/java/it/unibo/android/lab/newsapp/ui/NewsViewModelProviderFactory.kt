package it.unibo.android.lab.newsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import it.unibo.android.lab.newsapp.repository.NewsRepository

// ViewmodelProvider.Factory is a class that instantiate and return view model

class NewsViewModelProviderFactory(val app: Application, val newsRepository: NewsRepository): ViewModelProvider.Factory {

}