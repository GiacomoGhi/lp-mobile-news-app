package it.unibo.android.lab.newsapp.repository

import it.unibo.android.lab.newsapp.api.RetrofitInstance

class QuotesRepository {
  suspend fun getQuotes() =
    RetrofitInstance.apiClient.getMarketQuotes()
}
