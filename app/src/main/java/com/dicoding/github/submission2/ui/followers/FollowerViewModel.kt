package com.dicoding.github.submission2.ui.followers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.github.submission2.model.DataFollowers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowerViewModel: ViewModel() {
    private val listFollowerNonMutable = ArrayList<DataFollowers>()
    private val listFollowersMutable = MutableLiveData<ArrayList<DataFollowers>>()

    fun getListFollowers(): LiveData<ArrayList<DataFollowers>> {
        return listFollowersMutable
    }

    fun gitDataGit(context: Context, id: String) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "4dda8eb575a5df4bbb627e11e0563afcdb3ab95a")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$id/followers"

        httpClient.get(urlClient, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowersFragment.TAG, result!!)
                try {
                    val jsonArr= JSONArray(result)
                    for (i in 0 until jsonArr.length()) {
                        val jsonObj = jsonArr.getJSONObject(i)
                        val usernameLogin  = jsonObj.getString("login")
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

        val httpClient  = AsyncHttpClient()

        httpClient.addHeader("Authorization", "4dda8eb575a5df4bbb627e11e0563afcdb3ab95a")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$usernameLogin"

        httpClient.get(urlClient, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowersFragment.TAG, result!!)

                try {
                    val jsonObj = JSONObject(result)
                    val userData = DataFollowers()
                    userData.name = jsonObj.getString("name")
                    userData.username = jsonObj.getString("login")
                    userData.avatar = jsonObj.getString("avatar_url")
                    userData.company = jsonObj.getString("company")
                    userData.repository = jsonObj.getString("public_repos")
                    userData.followers = jsonObj.getString("followers")
                    userData.following = jsonObj.getString("following")
                    listFollowerNonMutable.add(userData)
                    listFollowersMutable.postValue(listFollowerNonMutable)
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