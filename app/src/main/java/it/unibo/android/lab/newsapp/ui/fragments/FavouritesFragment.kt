package it.unibo.android.lab.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.adapters.NewsAdapter
import it.unibo.android.lab.newsapp.databinding.FragmentFavouritesBinding
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


}