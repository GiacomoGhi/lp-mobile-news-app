package it.unibo.android.lab.newsapp.models

data class NewsResponse1(
  val articles: MutableList<NewsBody>,
  val status: String,
  val totalResults: Int
)