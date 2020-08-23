package io.github.masterj3y.mymovie

import android.os.Bundle
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import dagger.hilt.android.AndroidEntryPoint
import io.github.masterj3y.mymovie.core.platform.BaseActivity
import io.github.masterj3y.mymovie.core.platform.BaseFragment
import io.github.masterj3y.mymovie.databinding.ActivityMainBinding
import io.github.masterj3y.mymovie.movie.search.SearchMovieFragment
import me.ibrahimsn.lib.OnItemReselectedListener

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), BaseFragment.FragmentNavigationHost {

    private val fragNavController: FragNavController by lazy {
        FragNavController(supportFragmentManager, R.id.fragmentContainer)
    }

    override fun layoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFragNavController(savedInstanceState)
    }

    private fun initFragNavController(savedInstanceState: Bundle?) {
        fragNavController.apply {
            createEager = false
            fragmentHideStrategy = FragNavController.HIDE
            rootFragments = listOf(SearchMovieFragment())
            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    selectTab(index)
                }
            })
        }
        fragNavController.initialize(SEARCH_TAB, savedInstanceState)
        val initial = savedInstanceState == null
        if (initial) {
            binding.mainBottomBar.itemActiveIndex = 0
        }
        binding.mainBottomBar.onItemSelected = {
            when (it) {
                0 -> fragNavController.switchTab(SEARCH_TAB)
            }
        }
        binding.mainBottomBar.onItemReselectedListener = object : OnItemReselectedListener {
            override fun onItemReselect(pos: Int) {
                fragNavController.clearStack()
            }
        }
    }

    private fun selectTab(index: Int) {
        binding.mainBottomBar.itemActiveIndex = index
        fragNavController.switchTab(index)
    }

    override fun onBackPressed() {
        if (fragNavController.popFragment().not())
            super.onBackPressed()
    }

    override fun fragNavController() = fragNavController

    companion object {

        private const val SEARCH_TAB = FragNavController.TAB1
    }
}