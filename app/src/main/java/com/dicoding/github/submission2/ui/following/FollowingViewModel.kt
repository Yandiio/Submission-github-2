package com.dicoding.github.submission2.ui.following

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.submission2.model.DataFollowing
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowingViewModel : ViewModel() {
    private val listFollowingNonMutable = ArrayList<DataFollowing>()
    private val listFollowingMutable = MutableLiveData<ArrayList<DataFollowing>>()

    fun getListFollowing(): MutableLiveData<ArrayList<DataFollowing>> {
        return listFollowingMutable
    }

    fun getDataGit(context: Context, id: String) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "4dda8eb575a5df4bbb627e11e0563afcdb3ab95a")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$id/following"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowingFragment.TAG, result!!)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDataGitDetail(usernameLogin, context)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDataGitDetail(usernameLogin: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "4dda8eb575a5df4bbb627e11e0563afcdb3ab95a")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$usernameLogin"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowingFragment.TAG, result!!)

                try {
                    val jsonObj = JSONObject(result)
                    val usersData = DataFollowing()
                    usersData.username = jsonObj.getString("login")
                    usersData.name = jsonObj.getString("name")
                    usersData.avatar = jsonObj.getString("avatar_url")
                    usersData.location = jsonObj.getString("location")
                    usersData.repository = jsonObj.getString("public_repos")
                    usersData.company = jsonObj.getString("company")
                    usersData.followers = jsonObj.getString("followers")
                    usersData.following = jsonObj.getString("following")
                    listFollowingNonMutable.add(usersData)
                    listFollowingMutable.postValue(listFollowingNonMutable)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}