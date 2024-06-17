package it.unibo.android.lab.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "newsBody")
data class NewsBody(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val ago: String,
    val img: String,
    val source: String,
    val text: String,
    val tickers: List<String>,
    val time: String,
    val title: String,
    val type: String,
    val url: String
): Serializable