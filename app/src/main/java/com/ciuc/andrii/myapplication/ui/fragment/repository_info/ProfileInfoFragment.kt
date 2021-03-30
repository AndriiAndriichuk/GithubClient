package com.ciuc.andrii.myapplication.ui.fragment.repository_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.databinding.FragmentProfileInfoBinding
import com.ciuc.andrii.myapplication.ui.fragment.base.BaseFragment
import com.ciuc.andrii.myapplication.ui.fragment.repository_info.adapter.RepositoryAdapter
import com.ciuc.andrii.myapplication.utils.gone
import com.ciuc.andrii.myapplication.utils.show
import com.ciuc.andrii.myapplication.utils.toast
import org.koin.android.ext.android.inject

class ProfileInfoFragment : BaseFragment() {

    private val args by navArgs<ProfileInfoFragmentArgs>()
    private val viewModel: ProfileInfoViewModel by inject()

    private val adapter = RepositoryAdapter()

    private lateinit var layout: FragmentProfileInfoBinding

    private var currentProfile: UserSearchItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        layout =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_info, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setOnClickListeners()
        subscribeToLiveData()

        getRepositories()

    }

    private fun setUpView() {
        val linearLayoutManager =
            GridLayoutManager(requireActivity(), 3)
        layout.recyclerViewFollowers.layoutManager = linearLayoutManager

        currentProfile = args.userItem

        if (currentProfile != null) {
            var requestOptions = RequestOptions()
            val radius = layout.root.resources.getDimensionPixelSize(R.dimen.user_info_image_corner)
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(radius))

            Glide.with(this)
                .load(currentProfile?.avatar_url)
                .centerCrop()
                .apply(requestOptions)
                .into(layout.imageUser)

            layout.textUserName.text = currentProfile?.login
        }
    }

    private fun setOnClickListeners() {
        layout.textSeeOnWeb.setOnClickListener {
            openWebBrowser(currentProfile?.html_url.toString())
        }
        layout.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        adapter.onRepositoryClick = { openWebBrowser(it.htmlUrl.toString()) }
    }

    private fun subscribeToLiveData() {
        viewModel.repositoriesLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                layout.textRepositoriesTitle.show()
                adapter.setData(it)
                layout.recyclerViewFollowers.adapter = adapter
            } else {
                layout.textRepositoriesTitle.gone()
                requireContext().toast(getString(R.string.user_not_have_repos))
            }
        })
    }

    private fun getRepositories() {
        if (connectivityManager.isOnline) {
            viewModel.getRepositories(currentProfile?.login.toString())
        } else {
            requireContext().toast(resources.getString(R.string.you_dont_have_internet))
        }
    }

    private fun openWebBrowser(url: String) {
        val direction = ProfileInfoFragmentDirections.toWebBrowserFragment(url)
        findNavController().navigate(direction)
    }
}
