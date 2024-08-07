package it.unibo.android.lab.newsapp.api

import it.unibo.android.lab.newsapp.util.Costants.Companion.BASE_URL
import it.unibo.android.lab.newsapp.util.Costants.Companion.BASE_URL_YH
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
  companion object{

    private val retrofit by lazy {
      val logging = HttpLoggingInterceptor()
      logging.setLevel(HttpLoggingInterceptor.Level.BODY)
      val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

      Retrofit.Builder()
        .baseUrl(BASE_URL_YH)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    }

    val apiClient: MarketAPI by lazy {
      retrofit.create((MarketAPI::class.java))
    }
  }
}