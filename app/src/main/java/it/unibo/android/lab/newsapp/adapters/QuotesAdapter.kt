package it.unibo.android.lab.newsapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.models.QuotesBody

class QuotesAdapter : RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    inner class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyPair: TextView = itemView.findViewById(R.id.currency_pair)
        val percentageChange: TextView = itemView.findViewById(R.id.percentage_change)
        val bidPrice: TextView = itemView.findViewById(R.id.bid_price)
        val askPrice: TextView = itemView.findViewById(R.id.ask_price)
        val lowPrice: TextView = itemView.findViewById(R.id.low_price)
        val highPrice: TextView = itemView.findViewById(R.id.high_price)
        val symbol: TextView = itemView.findViewById(R.id.symbol)
    }

    private val differCallback = object : DiffUtil.ItemCallback<QuotesBody>() {
        override fun areItemsTheSame(oldItem: QuotesBody, newItem: QuotesBody): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: QuotesBody, newItem: QuotesBody): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var fullList: List<QuotesBody> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuotesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val quotesWindow = differ.currentList[position]

        holder.itemView.apply {
            holder.currencyPair.text = quotesWindow.currency
            holder.bidPrice.text = quotesWindow.bid.toString()
            holder.askPrice.text = quotesWindow.ask.toString()
            holder.symbol.text = quotesWindow.symbol

            val value = quotesWindow.regularMarketChangePercent
            val formattedPercentage = String.format("%.3f%%", value)
            holder.percentageChange.text = formattedPercentage

            val lp = quotesWindow.regularMarketDayLow
            val formattedLowPrice = "L: %.2f".format(lp)
            holder.lowPrice.text = formattedLowPrice

            val hp = quotesWindow.regularMarketDayHigh
            val formattedHighPrice = "H: %.2f".format(hp)
            holder.highPrice.text = formattedHighPrice

            if (value >= 0) {
                holder.percentageChange.setTextColor(Color.GREEN)
            } else {
                holder.percentageChange.setTextColor(Color.RED)
            }
        }
    }

    fun submitList(list: List<QuotesBody>) {
        fullList = list
        differ.submitList(list)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.currency.contains(query, true) ||
                        it.symbol.contains(query, true)
            }
        }
        differ.submitList(filteredList)
    }
}
