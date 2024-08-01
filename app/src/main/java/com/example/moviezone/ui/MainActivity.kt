package com.example.moviezone.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.moviezone.R
import com.example.moviezone.ui.movieList.MovieListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Popular Movies"
        setSupportActionBar(toolbar)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.category_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.popular_menu_option -> {
                        toolbar.title = "Popular Movies"
                        mainViewModel.selectedCategory = POPULAR
                        setCategory(POPULAR)
                        true
                    }

                    R.id.top_rated_menu_option -> {
                        toolbar.title = "Top Rated Movies"
                        mainViewModel.selectedCategory = TOP_RATED
                        setCategory(TOP_RATED)
                        true
                    }

                    else -> false
                }
            }
        })
        if (savedInstanceState == null) {
            loadFragment(MovieListFragment())
        }
    }

    private fun setCategory(category: String) {
        supportFragmentManager.fragments.let { fragmentList ->
            if ((fragmentList.isNotEmpty()) && fragmentList.get(fragmentList.size - 1) is MovieListFragment) {
                (fragmentList.get(fragmentList.size - 1) as? MovieListFragment)?.setCategory(
                    category
                )
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    companion object {
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
    }

    fun hideToolbar() {
        findViewById<View>(R.id.mainToolbar).visibility = View.GONE
    }

    fun showToolbar() {
        findViewById<View>(R.id.mainToolbar).visibility = View.VISIBLE
    }
}