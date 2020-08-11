package com.dicoding.github.submission2.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.submission2.R
import com.dicoding.github.submission2.adapter.ListDataFollowingAdapter
import com.dicoding.github.submission2.model.DataFollowing
import com.dicoding.github.submission2.model.DataUsers
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private var bool: Boolean = false
    private var listData: ArrayList<DataFollowing> = ArrayList()
    private lateinit var adapter: ListDataFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListDataFollowingAdapter(listData)
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra<DataUsers>(EXTRA_DETAIL) as DataUsers
        setRecycleView()

        followingViewModel.getDataGit(activity!!.applicationContext, dataUser.username.toString())
        showLoading(true)

        followingViewModel.getListFollowing().observe(activity!!, Observer { listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                showLoading(false)
            }
        })
    }

    private fun setRecycleView() {
        recyclerViewFollowing.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowing.setHasFixedSize(true)
        recyclerViewFollowing.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFollowing.visibility = View.VISIBLE
        } else {
            progressBarFollowing.visibility = View.INVISIBLE
        }
    }

}