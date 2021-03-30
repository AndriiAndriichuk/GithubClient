package com.ciuc.andrii.myapplication.ui.fragment.repository_info

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.client.models.followers.Subscriber
import com.ciuc.andrii.myapplication.databinding.FragmentRepositoryInfoBinding
import com.ciuc.andrii.myapplication.ui.activities.repository_info.RepositoryInfoViewModel
import com.ciuc.andrii.myapplication.ui.activities.repository_info.adapters.SubscribersAdapter
import com.ciuc.andrii.myapplication.ui.fragment.base.BaseFragment
import com.ciuc.andrii.utils.Constants
import com.ciuc.andrii.utils.toast
import org.koin.android.ext.android.inject

class RepositoryInfoFragment : BaseFragment() {

   // private val args by navArgs<ProfileInfoFragmentArgs>()
   private val viewModel: RepositoryInfoViewModel by inject()
    private var listSubscribers: List<Subscriber> = arrayListOf()
    lateinit var progressDialog: ProgressDialog

    private lateinit var layout: FragmentRepositoryInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_repository_info, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle(resources.getString(R.string.loading))
        progressDialog.setMessage(resources.getString(R.string.please_wait))
        progressDialog.setCancelable(false)

        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        layout.recyclerViewFollowers.layoutManager = linearLayoutManager

        if (Constants.repositoryDTO != null) {
            setInfoToLabels()
        }
        //layout.model = args.user


    }

    private fun setInfoToLabels() {
        Glide.with(this)
            .load(Constants.repositoryDTO?.owner?.avatarUrl)
            .centerCrop()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(7)))
            .into( layout.imageOwner)

        layout.textOwnerName.text = Constants.repositoryDTO?.owner?.login
        layout.textStars.text = Constants.repositoryDTO?.stargazersCount.toString()
        layout. textForks.text = Constants.repositoryDTO?.forksCount.toString()
        layout.textRepositoryName.text = Constants.repositoryDTO?.name

        layout.textRepositoryDescription.text = Constants.repositoryDTO?.description
    }

    override fun onResume() {
        super.onResume()
        progressDialog.show()
        if (viewModel.hasNetworkConnection()) {
            viewModel.getSubscribers(Constants.repositoryDTO?.owner?.login.toString())
                .observe(this, androidx.lifecycle.Observer {
                    if (it != null) {
                        listSubscribers = it
                        layout.textSubscribers.text = listSubscribers.size.toString()
                        val adapter = SubscribersAdapter(listSubscribers)
                        layout.recyclerViewFollowers.adapter = adapter
                    }
                    progressDialog.cancel()
                })
        } else {
            progressDialog.cancel()
            requireContext().toast(resources.getString(R.string.you_dont_have_internet))
        }
    }
}
