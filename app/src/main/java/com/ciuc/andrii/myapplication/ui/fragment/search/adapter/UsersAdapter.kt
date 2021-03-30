package com.ciuc.andrii.myapplication.ui.fragment.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.client.models.user.UserSearchItem
import com.ciuc.andrii.myapplication.databinding.AdapterUsersListBinding


class UsersAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<UserSearchItem> = listOf()
    var onUserClickListener: ((UserSearchItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepositoryViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.adapter_users_list,
                parent,
                false
            )
        )
    }

    fun setData(items: List<UserSearchItem>){
        list = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(parent: RecyclerView.ViewHolder, position: Int) {
        if (parent is RepositoryViewHolder) {
            val story = list[position]
            parent.bind(story)
        }
    }

    inner class RepositoryViewHolder(val layout: AdapterUsersListBinding) :
        RecyclerView.ViewHolder(layout.root) {

        private var requestOptions: RequestOptions = RequestOptions()

        init {
            val radius = layout.root.resources.getDimensionPixelSize(R.dimen.user_list_corner)
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(radius))
        }

        fun bind(user: UserSearchItem) {
            setUpView(user)
            setClickListener(user)
        }

        private fun setUpView(user: UserSearchItem) {

            layout.textUserName.text = user.login

            Glide.with(layout.imageUserAvatar)
                .load(user.avatar_url)
                .centerCrop()
                .apply(requestOptions)
                .into(layout.imageUserAvatar)
        }

        private fun setClickListener(user: UserSearchItem) {
            layout.itemRootView.setOnClickListener { onUserClickListener?.invoke(user) }
        }
    }

}
