package com.dicoding.github.submission2.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.submission2.R
import com.dicoding.github.submission2.adapter.ListDataFollowerAdapter
import com.dicoding.github.submission2.model.DataFollowers
import com.dicoding.github.submission2.model.DataUsers
import kotlinx.android.synthetic.main.fragment_followers.*


class FollowersFragment : Fragment() {

    companion object {
        val TAG = FollowersFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<DataFollowers> = ArrayList()
    private lateinit var adapter: ListDataFollowerAdapter
    private lateinit var followerViewModel : FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListDataFollowerAdapter(listData)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra<DataUsers>(EXTRA_DETAIL) as DataUsers
        setRecyclerView()

        followerViewModel.gitDataGit(activity!!.applicationContext, dataUser.username.toString())
        showLoading(true)

        followerViewModel.getListFollowers().observe(activity!!, Observer { listFolower ->
            if (listFolower != null) {
                adapter.setData(listFolower)
                showLoading(false)
            }
        })
    }

    private fun setRecyclerView() {
        recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowers.setHasFixedSize(true)
        recyclerViewFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFollowers.visibility = View.VISIBLE
        } else {
            progressBarFollowers.visibility = View.INVISIBLE
        }
    }
}