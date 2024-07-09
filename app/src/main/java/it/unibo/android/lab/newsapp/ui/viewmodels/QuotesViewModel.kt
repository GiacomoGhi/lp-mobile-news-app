package it.unibo.android.lab.newsapp.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import it.unibo.android.lab.newsapp.models.QuotesResponse
import it.unibo.android.lab.newsapp.repository.QuotesRepository
import it.unibo.android.lab.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class QuotesViewModel(
  app: Application,
  private val quotesRepository: QuotesRepository
) : BaseViewModel<QuotesResponse>(app, quotesRepository) {

  override val response: MutableLiveData<Resource<QuotesResponse>> = MutableLiveData()

  init {
    super.getData()
  }

  override suspend fun fetchData() {
    getData { quotesRepository.getQuotes(null) }
  }
}
