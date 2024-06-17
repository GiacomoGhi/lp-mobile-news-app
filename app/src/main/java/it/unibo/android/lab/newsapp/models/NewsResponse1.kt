package it.unibo.android.lab.newsapp.models

data class NewsResponse1(
  val articles: MutableList<Article>,
  val status: String,
  val totalResults: Int
)