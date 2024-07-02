package it.unibo.android.lab.newsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.models.NewsBody

class MarketNewsAdapter: RecyclerView.Adapter<MarketNewsAdapter.NewsBodyViewHolder>() {

  private val TAG = "MarketNewsAdapter"

  inner class NewsBodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  lateinit var articleImage: ImageView
  lateinit var articleSource: TextView
  lateinit var articleTitle: TextView
  lateinit var articleDescription: TextView
  lateinit var articleDateTime: TextView

  // Callback per calcolare le differenze tra vecchia e nuova lista di articoli
  private val differCallback = object : DiffUtil.ItemCallback<NewsBody>(){
    override fun areItemsTheSame(oldItem: NewsBody, newItem: NewsBody): Boolean {
      return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsBody, newItem: NewsBody): Boolean {
      return oldItem == newItem
    }

  }
  // Differ per gestire in modo efficiente le differenze nella lista degli articoli
  val differ = AsyncListDiffer(this, differCallback)


  // Metodo per creare e restituire un nuovo ViewHolder
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsBodyViewHolder {
    return NewsBodyViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    )
  }

  override fun getItemCount(): Int {
    return differ.currentList.size
  }

  private var onItemClickListener : ((NewsBody) -> Unit)? = null

  // Metodo per collegare i dati alle viste nel ViewHolder
  override fun onBindViewHolder(holder: NewsBodyViewHolder, position: Int) {
    val article = differ.currentList[position]

    // Associazione delle viste del ViewHolder agli ID delle viste nel layout
    articleImage = holder.itemView.findViewById(R.id.articleImage)
    articleSource = holder.itemView.findViewById(R.id.articleSource)
    articleTitle = holder.itemView.findViewById(R.id.articleTitle)
    articleDescription = holder.itemView.findViewById(R.id.articleDescription)
    articleDateTime = holder.itemView.findViewById(R.id.articleDateTime)

    holder.itemView.apply {
      // Caricamento dell'immagine dell'articolo usando Glide
      Glide.with(this).load(article.url).into(articleImage)
      // Impostazione dei dati dell'articolo nelle viste corrispondenti
      articleSource.text = article.source
      articleTitle.text = article.title
      articleDescription.text = article.text
      articleDateTime.text = article.time

      setOnClickListener{
        onItemClickListener?.let {
          if (article != null) {
            it(article)
          } else {
            Log.w(TAG, "NewsBody is null!")
          }
        }
      }
    }
  }
  fun setOnItemClickListener(listener: (NewsBody) -> Unit){
    onItemClickListener = listener
  }

}