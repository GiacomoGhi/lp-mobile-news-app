package it.unibo.android.lab.newsapp.ui.viewmodels.providerfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.unibo.android.lab.newsapp.repository.QuotesRepository
import it.unibo.android.lab.newsapp.ui.viewmodels.QuotesViewModel

/***********************************************************************************
ViewmodelProvider.Factory is a class that instantiate and return view model
This class is only used to create an instance of NewsViewModel
 ************************************************************************************/

class QuotesViewModelProviderFactory(val app: Application, private val quotesRepository: QuotesRepository): ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    return QuotesViewModel(app, quotesRepository) as T // Ensuring that the written instance matches w/ generic type T
  }
}