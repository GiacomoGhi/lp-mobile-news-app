package it.unibo.android.lab.newsapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        val symbol : TextView = itemView.findViewById((R.id.symbol))
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
            holder.lowPrice.text = quotesWindow.regularMarketDayLow.toString()
            holder.highPrice.text = quotesWindow.regularMarketDayHigh.toString()
            holder.symbol.text = quotesWindow.symbol

            val value = quotesWindow.regularMarketChangePercent
            val formattedPercentage = String.format("%.3f%%", value)
            holder.percentageChange.text = formattedPercentage

            if(value >= 0) {
                holder.percentageChange.setTextColor(Color.GREEN)
            } else {
                holder.percentageChange.setTextColor(Color.RED)

            }

        }
    }


}