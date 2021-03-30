package com.ciuc.andrii.myapplication.ui.activities.repository_info.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.client.models.followers.Subscriber
import kotlinx.android.synthetic.main.recycler_view_item_follower.view.*


class SubscribersAdapter(
    items: List<Subscriber>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<Subscriber> = items
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_follower, parent, false)
        return FollowerViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(parent: RecyclerView.ViewHolder, position: Int) {
        if (parent is FollowerViewHolder) {
            parent.textFollowerName.text = list[position].login

            Glide.with(context)
                .load(list[position].avatarUrl)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(7)))
                .into(parent.imageFollower)
        }
    }

    inner class FollowerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textFollowerName: TextView = view.textFollowerName
        val imageFollower: ImageView = view.imageFollower
    }
}
