package it.unibo.android.lab.newsapp.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.models.NewsBody
import it.unibo.android.lab.newsapp.models.QuotesBody

class QuotesAdapter : RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    inner class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyPair: TextView = itemView.findViewById(R.id.currency_pair)
        val percentageChange : TextView = itemView.findViewById(R.id.percentage_change)
        val bidPrice : TextView = itemView.findViewById(R.id.bid_price)
        val askPrice : TextView = itemView.findViewById(R.id.ask_price)
        val lowPrice : TextView = itemView.findViewById(R.id.low_price)
        val highPrice : TextView = itemView.findViewById(R.id.high_price)
    }

    private val differCallback = object : DiffUtil.ItemCallback<QuotesBody>() {
        override fun areItemsTheSame(oldItem: QuotesBody, newItem: QuotesBody): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: QuotesBody, newItem: QuotesBody): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}