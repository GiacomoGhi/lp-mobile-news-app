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
    apiKey: String = Costants.API_KEY_YH
  ): Response<NewsResponse>

  @GET("api/v1/markets/stock/quotes")
  suspend fun getMarketQuotes(
    @Query("ticker")
    // uses some default values
    ticker: String? = "AAPL, MSFT, ^SPX, ^NYA, GAZP.ME, SIBN.ME, GEECEE.NS",
    @Header("X-RapidAPI-Key")
    apiKey: String = Costants.API_KEY_YH
  ): Response<QuotesResponse>
}