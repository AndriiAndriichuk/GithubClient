package com.ciuc.andrii.myapplication.ui.fragment.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import com.ciuc.andrii.myapplication.R
import com.gbk.soft.connectivity_util.ConnectivityManager as Connect

val navOptionsBuilder = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)

open class BaseFragment : Fragment() {

    // protected lateinit var navController: NavController

    protected lateinit var connectivityManager: Connect

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        connectivityManager = Connect(requireContext())
    }
}
