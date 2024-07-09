package it.unibo.android.lab.newsapp.repository

import it.unibo.android.lab.newsapp.api.RetrofitInstance

class QuotesRepository {
  suspend fun getQuotes(ticker: List<String>?) =
    RetrofitInstance.apiClient.getMarketQuotes(ticker)
}
