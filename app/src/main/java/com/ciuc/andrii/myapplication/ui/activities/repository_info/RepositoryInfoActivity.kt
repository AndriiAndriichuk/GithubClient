package com.ciuc.andrii.myapplication.ui.activities.repository_info

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.utils.Constants.Companion.repositoryDTO
import com.ciuc.andrii.myapplication.client.models.followers.Subscriber
import com.ciuc.andrii.utils.toast
import com.ciuc.andrii.myapplication.ui.activities.repository_info.adapters.SubscribersAdapter
import kotlinx.android.synthetic.main.activity_repository_info.*

class RepositoryInfoActivity : AppCompatActivity() {

    private lateinit var repositoryInfoViewModel: RepositoryInfoViewModel
    private var listSubscribers: List<Subscriber> = arrayListOf()
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_info)

        repositoryInfoViewModel = ViewModelProviders.of(this).get(RepositoryInfoViewModel::class.java)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(resources.getString(R.string.loading))
        progressDialog.setMessage(resources.getString(R.string.please_wait))
        progressDialog.setCancelable(false)

        val linearLayoutManager =
            LinearLayoutManager(this@RepositoryInfoActivity, RecyclerView.HORIZONTAL, false)
        recyclerViewFollowers.layoutManager = linearLayoutManager

        if (repositoryDTO != null) {
            setInfoToLabels()
        }
    }

    private fun setInfoToLabels() {
        Glide.with(this)
            .load(repositoryDTO?.owner?.avatarUrl)
            .centerCrop()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(7)))
            .into(imageOwner)

        textOwnerName.text = repositoryDTO?.owner?.login
        textStars.text = repositoryDTO?.stargazersCount.toString()
        textForks.text = repositoryDTO?.forksCount.toString()
        textRepositoryName.text = repositoryDTO?.name

        textRepositoryDescription.text = repositoryDTO?.description
    }

    override fun onResume() {
        super.onResume()
        progressDialog.show()
        if (repositoryInfoViewModel.hasNetworkConnection()) {
            repositoryInfoViewModel.getSubscribers(repositoryDTO?.owner?.login.toString())
                .observe(this, androidx.lifecycle.Observer {
                    if (it != null) {
                        listSubscribers = it
                        textSubscribers.text = listSubscribers.size.toString()
                        val adapter = SubscribersAdapter(listSubscribers)
                        recyclerViewFollowers.adapter = adapter
                    }
                    progressDialog.cancel()
                })
        } else {
            progressDialog.cancel()
            toast(resources.getString(R.string.you_dont_have_internet))
        }
    }
}
