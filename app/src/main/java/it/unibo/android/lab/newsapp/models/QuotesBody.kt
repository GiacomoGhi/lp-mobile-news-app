package it.unibo.android.lab.newsapp.models

import java.io.Serializable

data class QuotesBody(
    val ask: Double,
    val bid: Double,
    val currency: String,
    val displayName: String,
    val longName: String,
    val symbol: String,
    val regularMarketChangePercent: Double
): Serializable