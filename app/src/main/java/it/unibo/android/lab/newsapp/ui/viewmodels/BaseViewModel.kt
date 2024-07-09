package it.unibo.android.lab.newsapp.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import it.unibo.android.lab.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

abstract class BaseViewModel<T>(
  app: Application,
  private val repository: Any
) : AndroidViewModel(app) {

  abstract val response: MutableLiveData<Resource<T>>

  protected abstract suspend fun fetchData()

  fun getData() = viewModelScope.launch {
    fetchData()
  }

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

  protected suspend fun getData(getResponse: suspend () -> Response<T>) {
    response.postValue(Resource.Loading())
    try {
      if (internetConnection(getApplication())) {
        val result = getResponse()
        Log.d("BaseViewModel", result.toString())
        response.postValue(handleResponse(result))
      } else {
        response.postValue(Resource.Error("No internet connection"))
      }
    } catch (t: Throwable) {
      when (t) {
        is IOException -> response.postValue(Resource.Error("Unable to connect"))
        else -> response.postValue(Resource.Error("No signal"))
      }
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun handleResponse(response: Response<T>): Resource<T> {
    return if (response.isSuccessful && response.body() != null) {
      Resource.Success(response.body() as T)
    } else {
      Resource.Error(response.message())
    }
  }
}
