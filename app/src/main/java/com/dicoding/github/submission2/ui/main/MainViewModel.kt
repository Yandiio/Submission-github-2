package com.dicoding.github.submission2.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.submission2.model.DataUsers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

class MainViewModel : ViewModel() {

    private val listFollowerNonMutable = ArrayList<DataUsers>()
    private val listFollowerMutable = MutableLiveData<ArrayList<DataUsers>>()

    fun getDataGit(context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "4dda8eb575a5df4bbb627e11e0563afcdb3ab95a")
        httpClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users"

        httpClient.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDataGitSearch(query: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "4dda8eb575a5df4bbb627e11e0563afcdb3ab95a")
        httpClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=$query"

        httpClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })
    }
}