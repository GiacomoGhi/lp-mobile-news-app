package it.unibo.android.lab.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.adapters.MarketNewsAdapter
import it.unibo.android.lab.newsapp.databinding.FragmentWatchlaterBinding
import it.unibo.android.lab.newsapp.MainActivity
import it.unibo.android.lab.newsapp.ui.viewmodels.NewsViewModel

/*
* Four main uses for this class:
* 1 - Displays all the favourite articles saved in the database
* 2- If a user clicks on an article, it navigates to that article ONLY
* 3 - The article is deleted when the user swipes left on it
* 4 - User can undo his delete action
*/
class WatchLaterFragment : Fragment(R.layout.fragment_watchlater) {

    private val TAG = "WatchLaterFragment"

    lateinit var newsViewModel: NewsViewModel
    lateinit var marketNewsAdapter: MarketNewsAdapter
    private lateinit var binding: FragmentWatchlaterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWatchlaterBinding.bind(view)

        newsViewModel = (activity as MainActivity).newsViewModel
        setUpWatchLaterRecycler()

        marketNewsAdapter.setOnItemClickListener { newsBody ->
            val bundle = Bundle().apply {
                putSerializable("NewsBody", newsBody)
            }
            Log.d(TAG, "Navigate to article with bundle content: $bundle")
            findNavController().navigate(R.id.action_watchLaterFragment_to_articleFragment, bundle)
        }

        // swipe left feature
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = marketNewsAdapter.differ.currentList[position]
                newsViewModel.deleteArticle(article)
                Snackbar.make(view, "Removed from favourites", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        newsViewModel.addToFavourites(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerWatchLater)
        }

        newsViewModel.getFavouriteNews().observe(viewLifecycleOwner, Observer { newsBody ->
            marketNewsAdapter.differ.submitList(newsBody)
        })
    }

    private fun setUpWatchLaterRecycler() {
        marketNewsAdapter = MarketNewsAdapter()
        binding.recyclerWatchLater.apply {
            adapter = marketNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}










//package it.unibo.android.lab.newsapp.ui.fragments
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.View
//import androidx.lifecycle.Observer
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.snackbar.Snackbar
//import it.unibo.android.lab.newsapp.R
//import it.unibo.android.lab.newsapp.adapters.MarketNewsAdapter
//import it.unibo.android.lab.newsapp.databinding.FragmentWatchlaterBinding
//import it.unibo.android.lab.newsapp.ui.NewsActivity
//import it.unibo.android.lab.newsapp.ui.viewmodels.NewsViewModel
//
///*
//* Four main uses for this class:
//* 1 - Displays all the favourite articles saved in the database
//* 2- If a user clicks on an article, it navigates to that article ONLY
//* 3 - The article is deleted when the user swipes left on it
//* 4 - User can undo his delete action
//*/
//class WatchLaterFragment : Fragment(R.layout.fragment_watchlater) {
//
//    private val TAG = "WatchLaterFragment"
//
//    lateinit var newsViewModel: NewsViewModel
//    lateinit var newsAdapter: MarketNewsAdapter
//    lateinit var binding: FragmentWatchlaterBinding
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentWatchlaterBinding.bind(view)
//
//        newsViewModel = (activity as NewsActivity).newsViewModel
//        setUpFavouriteRecycler()
//
//        MarketNewsAdapter.setOnItemClickListener {
//            val bundle = Bundle().apply {
//                putSerializable("NewsBody", it)
//            }
//            Log.d(TAG, "Navigate to article with bundle content: $bundle")
//            findNavController().navigate(R.id.action_WatchLaterFragment_to_articleFragment, bundle)
//        }
//
//        // swipe left feature
//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                val article = newsAdapter.differ.currentList[position]
//                newsViewModel.deleteArticle(article)
//                Snackbar.make(view, "Removed from favourites", Snackbar.LENGTH_LONG).apply {
//                    setAction("Undo"){
//                        newsViewModel.addToFavourites(article)
//                    }
//                    show()
//                }
//            }
//        }
//
//        ItemTouchHelper(itemTouchHelperCallback).apply {
//            attachToRecyclerView(binding.recyclerWatchLater)
//        }
//
//        newsViewModel.getFavouriteNews().observe(viewLifecycleOwner, Observer { NewsBody ->
//            newsAdapter.differ.submitList(NewsBody)
//        })
//    }
//
//    private fun setUpFavouriteRecycler() {
//        newsAdapter = MarketNewsAdapter()
//        binding.recyclerWatchLater.apply {
//            adapter = newsAdapter
//            layoutManager = LinearLayoutManager(activity)
//        }
//    }
//}