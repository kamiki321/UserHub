package com.kamiki.userhub.ui.userview

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamiki.userhub.R
import com.kamiki.userhub.adapter.UserAdapter
import com.kamiki.userhub.data.response.UserResponse
import com.kamiki.userhub.databinding.ActivityMainBinding
import com.kamiki.userhub.ui.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title = "UserHub"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
        binding.rvUser.setHasFixedSize(true)

        val adapter = UserAdapter(emptyList())
        binding.rvUser.adapter = adapter



        mainViewModel.ListUser.observe(this) { items ->
            setUserData(items)
        }

        mainViewModel.IsLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.IsError.observe(this) {
            if (it) {
                Toast.makeText(this, "Failed to load API", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                if (q.isNotEmpty()) {
                    showLoading(true)
                    mainViewModel.findUser(q)
                    Toast.makeText(this@MainActivity, q, Toast.LENGTH_SHORT).show()
                    searchView.clearFocus()
                } else {
                    showLoading(true)
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }


    private fun setUserData(User: List<UserResponse.Item>) {
        val adapter = UserAdapter(User)
        binding.rvUser.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}