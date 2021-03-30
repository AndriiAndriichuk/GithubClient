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
import com.ciuc.andrii.myapplication.client.models.gh_repository.RepositoryDTO
import kotlinx.android.synthetic.main.recycler_view_item_repository.view.*


class RepositoryAdapter(
    items: List<RepositoryDTO>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<RepositoryDTO> = items
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_repository, parent, false)
        return RepositoryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(parent: RecyclerView.ViewHolder, position: Int) {
        if (parent is RepositoryViewHolder) {
            parent.textRepositoryName.text = list[position].name
            parent.textOwnerName.text = list[position].owner?.login
            parent.textStars.text = list[position].stargazersCount.toString()
            parent.textLanguage.text = list[position].language.toString()

            Glide.with(context)
                .load(list[position].owner?.avatarUrl)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(7)))
                .into(parent.imageOwner)
        }
    }

    inner class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textRepositoryName: TextView = view.textRepositoryName
        val textOwnerName: TextView = view.textOwnerName
        val textStars: TextView = view.textStars
        val textLanguage: TextView = view.textLanguage
        val imageOwner: ImageView = view.imageOwner
    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }
}
