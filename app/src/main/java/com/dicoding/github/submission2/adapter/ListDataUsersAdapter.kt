package com.dicoding.github.submission2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.github.submission2.DetailActivity
import com.dicoding.github.submission2.R
import com.dicoding.github.submission2.model.DataUsers
import kotlinx.android.synthetic.main.item_user.view.*

class ListDataUsersAdapter(private val listDataUsers: ArrayList<DataUsers>) :
    RecyclerView.Adapter<ListDataUsersAdapter.ListUserViewHolder>() {

    fun setData(items: ArrayList<DataUsers>) {
        listDataUsers.clear()
        listDataUsers.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataUsers: DataUsers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataUsers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(avatar)

                fullName.text = dataUsers.name
                userName.text = dataUsers.username
                count_following.text =
                    itemView.context.getString(R.string.following, dataUsers.following)
                count_followers.text =
                    itemView.context.getString(R.string.followers, dataUsers.followers)
                count_repos.text =
                    itemView.context.getString(R.string.repositories, dataUsers.repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        return ListUserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
       return listDataUsers.size
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.bind(listDataUsers[position])

        val data = listDataUsers[position]
        holder.itemView.setOnClickListener {
            val dataUser = DataUsers(
                data.username,
                data.name,
                data.avatar,
                data.followers,
                data.following,
                data.repository,
                data.company
            )
            val intent = Intent(it.context, DetailActivity::class.java )
            intent.putExtra(DetailActivity.EXTRA_DETAIL, dataUser)
            it.context.startActivity(intent)
        }
    }
}