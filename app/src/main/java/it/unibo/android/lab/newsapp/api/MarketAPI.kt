package it.unibo.android.lab.newsapp.api

import it.unibo.android.lab.newsapp.models.NewsResponse
import it.unibo.android.lab.newsapp.models.QuotesResponse
import it.unibo.android.lab.newsapp.util.Costants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MarketAPI {
  @GET("api/v2/markets/news")
  suspend fun getMarketNews(
    @Header("X-RapidAPI-Key")
    apiKey: String = Costants.API_KEY
  ): Response<NewsResponse>

  @GET("api/v1/markets/stock/quotes")
  suspend fun getMarketQuotes(
    @Query("ticker")
    ticker: List<String> =
      listOf(
        "AAPL", "MSFT", "^SPX", "^NYA", "GAZP.ME", "SIBN.ME", "GEECEE.NS"
      ), // uses some default values
    @Header("X-RapidAPI-Key")
    apiKey: String = Costants.API_KEY
  ): Response<QuotesResponse>
}