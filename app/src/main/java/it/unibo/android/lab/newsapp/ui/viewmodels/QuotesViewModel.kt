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
): AndroidViewModel(app) {

  private val TAG = "QuotesViewModel"

  private val quotesResponse: MutableLiveData<Resource<QuotesResponse>> = MutableLiveData()

  init {
    getMarketQuotes()
  }

  fun getMarketQuotes() = viewModelScope.launch {
    getQuotes()
  }

  private fun handleQuotesResponse(response: Response<QuotesResponse>): Resource<QuotesResponse> {
    if (response.isSuccessful && response.body() != null){ //checks if network request is successful
      return Resource.Success(response.body() as QuotesResponse)
    }
    return Resource.Error(response.message())
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
  private suspend fun getQuotes() {
    quotesResponse.postValue(Resource.Loading())// Posts the loading state to the news live date
    try{
      // Checks if there is internet connection
      if (internetConnection(this.getApplication())) {
        // If so, response stores news fetched from the repository and posts the processed result
        val response = quotesRepository.getQuotes(null)
        Log.d(TAG, response.toString())
        quotesResponse.postValue(handleQuotesResponse(response))
      } else {
        // Else return error
        quotesResponse.postValue(Resource.Error("No internet connection"))
      }
    } catch(t: Throwable) {
      when(t) {
        is IOException -> quotesResponse.postValue(Resource.Error("Unable to connect"))
        else -> quotesResponse.postValue(Resource.Error("No signal"))
      }
    }
  }

}