package com.ciuc.andrii.myapplication.ui.fragment.repository_info.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO
import com.ciuc.andrii.myapplication.databinding.AdapterRepositoriesBinding

class RepositoryAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<RepositoryDTO> = listOf()
    lateinit var context: Context
    var onRepositoryClick: ((RepositoryDTO) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FollowerViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.adapter_repositories,
                parent,
                false
            )
        )
    }

    fun setData(items: List<RepositoryDTO>){
        list = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(parent: RecyclerView.ViewHolder, position: Int) {
        if (parent is FollowerViewHolder) {
            val repository = list[position]
            parent.bind(repository)
        }
    }

    inner class FollowerViewHolder(val layout: AdapterRepositoriesBinding) :
        RecyclerView.ViewHolder(layout.root) {

        private var requestOptions: RequestOptions = RequestOptions()

        init {
            val radius = layout.root.resources.getDimensionPixelSize(R.dimen.user_list_corner)
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(radius))
        }

        fun bind(repository: RepositoryDTO) {
            setUpView(repository)
            setClickListener(repository)
        }

        private fun setUpView(repository: RepositoryDTO) {
            layout.textRepositoryName.text = repository.name
            layout.textLanguage.text = repository.language
        }

        private fun setClickListener(repository: RepositoryDTO) {
            layout.repositoryRoot.setOnClickListener { onRepositoryClick?.invoke(repository) }
        }
    }

}
