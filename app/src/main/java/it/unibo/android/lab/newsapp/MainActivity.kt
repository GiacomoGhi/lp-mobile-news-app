package it.unibo.android.lab.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import it.unibo.android.lab.newsapp.databinding.ActivityNewsBinding
import it.unibo.android.lab.newsapp.db.NewsBodyDatabase
import it.unibo.android.lab.newsapp.repository.NewsRepository
import it.unibo.android.lab.newsapp.repository.QuotesRepository
import it.unibo.android.lab.newsapp.ui.viewmodels.NewsViewModel
import it.unibo.android.lab.newsapp.ui.viewmodels.QuotesViewModel
import it.unibo.android.lab.newsapp.ui.viewmodels.providerfactory.NewsViewModelProviderFactory
import it.unibo.android.lab.newsapp.ui.viewmodels.providerfactory.QuotesViewModelProviderFactory

class MainActivity : AppCompatActivity() {

  lateinit var newsViewModel: NewsViewModel
  lateinit var quotesViewModel: QuotesViewModel

  private lateinit var binding: ActivityNewsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityNewsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // NewsViewModel
    val newsRepository = NewsRepository(NewsBodyDatabase(this))
    val newsViewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
    newsViewModel = ViewModelProvider(this, newsViewModelProviderFactory)[NewsViewModel::class.java]

    // QuotesViewModel
    val quotesRepository = QuotesRepository()
    val quoteViewModelProviderFactory = QuotesViewModelProviderFactory(application, quotesRepository)
    quotesViewModel = ViewModelProvider(this, quoteViewModelProviderFactory)[QuotesViewModel::class.java]

    // Navigation
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
    val navController = navHostFragment.navController
    binding.bottomNavigationView.setupWithNavController(navController)
  }
}