package it.unibo.android.lab.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.databinding.ActivityNewsBinding
import it.unibo.android.lab.newsapp.db.NewsBodyDatabase
import it.unibo.android.lab.newsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

  lateinit var newsViewModel: NewsViewModel
  lateinit var binding: ActivityNewsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityNewsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Creating an instance of newsRep. and providing it w an article database instance
    val newsRepository = NewsRepository(NewsBodyDatabase(this))
    val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
    newsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

    val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
    val navController = navHostFragment.navController
    binding.bottomNavigationView.setupWithNavController(navController)
  }
}