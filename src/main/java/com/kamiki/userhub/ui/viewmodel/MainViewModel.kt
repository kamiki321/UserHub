package com.kamiki.userhub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamiki.userhub.data.api.ApiClient
import com.kamiki.userhub.data.response.DetailUserResponse
import com.kamiki.userhub.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MainViewModel : ViewModel() {

    companion object {

        private const val TAG = "MainViewModel"
        private const val USERHUB_ID = "sidiq"

    }

    private val listUser = MutableLiveData<List<UserResponse.Item>>()
    val ListUser: LiveData<List<UserResponse.Item>> = listUser

    private val isLoading = MutableLiveData<Boolean>()
    val IsLoading: LiveData<Boolean> = isLoading

    private val detailUser = MutableLiveData<DetailUserResponse>()
    val DetailUser: LiveData<DetailUserResponse> = detailUser

    private val isError = MutableLiveData<Boolean>()
    val IsError: LiveData<Boolean> = isError

    private val isfollowers = MutableLiveData<List<UserResponse.Item>>()
    val followers: LiveData<List<UserResponse.Item>> = isfollowers

    private val isfollowing = MutableLiveData<List<UserResponse.Item>>()
    val following: LiveData<List<UserResponse.Item>> = isfollowing

    init {
        findUser(USERHUB_ID)
    }

    fun findUser(query: String) {
        isLoading.value = true
        isError.value = false
        val gitclient = ApiClient.getGitService().getGithubUser(query)
        gitclient.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    listUser.value = response.body()?.items
                } else {
                    isError.value = false
                    Log.e(TAG, "onFailureA: $response")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading.value = false
                Log.e(TAG, "onFailureB: ${t.message}")
            }
        })
    }

    fun findUserDetail(username: String) {
        isLoading.value = true
        isError.value = false
        val gitclient = ApiClient.getGitService().getDetailUser(username)
        gitclient.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    detailUser.value = response.body()
                } else {
                    isError.value = false
                    Log.e(TAG, "onFailureA: $response")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                isLoading.value = false
                Log.e(TAG, "onFailureB: ${t.message}")
            }
        })
    }

    fun getFollowers(username: String) {
        isLoading.value = true
        isError.value = false
        val gitclient = ApiClient.getGitService().getFollowers(username)
        gitclient.enqueue(object : Callback<List<UserResponse.Item>> {
            override fun onResponse(
                call: Call<List<UserResponse.Item>>,
                response: Response<List<UserResponse.Item>>,
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    isfollowers.value = response.body()
                } else {
                    isError.value = true
                    Log.e(TAG, "onFailureA getFollowers: $response")
                }
            }

            override fun onFailure(call: Call<List<UserResponse.Item>>, t: Throwable) {
                isLoading.value = false
                isError.value = true
                Log.e(TAG, "onFailureB getFollowers: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        isLoading.value = true
        isError.value = false
        val gitclient = ApiClient.getGitService().getFollowing(username)
        gitclient.enqueue(object : Callback<List<UserResponse.Item>> {
            override fun onResponse(
                call: Call<List<UserResponse.Item>>,
                response: Response<List<UserResponse.Item>>,
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    isfollowing.value = response.body()
                } else {
                    isError.value = true
                    Log.e(TAG, "onFailureA getFollowing: $response")
                }
            }

            override fun onFailure(call: Call<List<UserResponse.Item>>, t: Throwable) {
                isLoading.value = false
                isError.value = true
                Log.e(TAG, "onFailure getFollowing: ${t.message.toString()}")
            }
        })
    }


}
