package com.dicoding.github.submission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.github.submission2.R
import com.dicoding.github.submission2.model.DataFollowers
import kotlinx.android.synthetic.main.item_user.view.*

class ListDataFollowerAdapter(private val listDataFollower: ArrayList<DataFollowers>) :
    RecyclerView.Adapter<ListDataFollowerAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<DataFollowers>) {
        listDataFollower.clear()
        listDataFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(dataFollowers: DataFollowers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(avatar)

                fullName.text = dataFollowers.name
                userName.text = dataFollowers.username
                count_following.text =
                    itemView.context.getString(R.string.following, dataFollowers.following)
                count_followers.text =
                    itemView.context.getString(R.string.followers, dataFollowers.followers)
                count_repos.text =
                    itemView.context.getString(R.string.repositories, dataFollowers.repository)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int = listDataFollower.size

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollower[position])
    }
}