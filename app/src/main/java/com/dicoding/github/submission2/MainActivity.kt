package com.dicoding.github.submission2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.submission2.adapter.ListDataUsersAdapter
import com.dicoding.github.submission2.model.DataUsers
import com.dicoding.github.submission2.ui.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private var listDataUser: ArrayList<DataUsers> = ArrayList()
    private lateinit var listAdapter: ListDataUsersAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = ListDataUsersAdapter(listDataUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchData()
        setRecyclerView()
        getDataFromAPI()
        mainViewModel(listAdapter)
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        listAdapter.notifyDataSetChanged()
        recyclerView.adapter = listAdapter
    }

    private fun getDataFromAPI() {
        mainViewModel.getDataGit(applicationContext)
        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.INVISIBLE
        }
    }

    private fun mainViewModel(adapter: ListDataUsersAdapter) {
        mainViewModel.getListUser().observe(this, Observer {listDataUser ->
            if (listDataUser != null) {
                adapter.setData(listDataUser)
                showLoading(false)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId ) {
            R.id.lang -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.about -> {
                Toast.makeText(this@MainActivity, getString(R.string.creator), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchData() {
        user_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                if (query.isNotEmpty()) {
                    listDataUser.clear()
                    setRecyclerView()
                    mainViewModel.getDataGitSearch(query, applicationContext)
                    showLoading(true)
                    mainViewModel(listAdapter)
                } else {
                    return true
                }

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })

    }


}