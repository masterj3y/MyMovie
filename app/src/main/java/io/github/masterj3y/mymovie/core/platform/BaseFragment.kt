package io.github.masterj3y.mymovie.core.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    Fragment() {

    lateinit var binding: B

    private var fragNavController: FragNavController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigationHost)
            fragNavController = context.fragNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        return binding.root
    }

    fun pushFragment(fragment: Fragment, transactionOptions: FragNavTransactionOptions? = null) =
        fragNavController?.pushFragment(fragment, transactionOptions)

    interface FragmentNavigationHost {
        fun fragNavController(): FragNavController
    }
}