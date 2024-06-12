package it.unibo.android.lab.newsapp.ui.fragments

import android.content.ClipData.Item
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.adapters.NewsAdapter
import it.unibo.android.lab.newsapp.databinding.FragmentFavouritesBinding
import it.unibo.android.lab.newsapp.ui.NewsActivity
import it.unibo.android.lab.newsapp.ui.NewsViewModel

/*
* Four main uses for this class:
* 1 - Displays all the favourite articles saved in the database
* 2- If a user clicks on an article, it navigates to that article ONLY
* 3 - The article is deleted when the user swipes left on it
* 4 - User can undo his delete action
*/
class FavouritesFragment : Fragment() {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentFavouritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        setUpFavouriteRecycler()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_favouritesFragment2_to_articleFragment, bundle)
        }

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

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {

            }
        }
    }

    private fun setUpFavouriteRecycler() {
        newsAdapter = NewsAdapter()
        binding.recyclerFavourites.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}