package com.dicoding.github.submission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.github.submission2.adapter.ViewPagerDetailAdapter
import com.dicoding.github.submission2.model.DataUsers
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setData()
        viewPagerTab()
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<DataUsers>(EXTRA_DETAIL) as DataUsers
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions().override(130,130))
            .into(avatars)
        fullName.text = dataUser.name
        username.text = getString(R.string.yandiio, dataUser.username)
        company.text = dataUser.company
        location.text = dataUser.location
        followers.text = dataUser.followers
        following.text = dataUser.following
        repositories.text = dataUser.repository
    }

    private fun viewPagerTab() {
        val viewPagerFoll = ViewPagerDetailAdapter(this, supportFragmentManager)
        viewpager.adapter = viewPagerFoll
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }
}