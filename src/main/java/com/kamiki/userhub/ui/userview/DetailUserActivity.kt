package com.kamiki.userhub.ui.userview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kamiki.userhub.R
import com.kamiki.userhub.adapter.SectionPagerAdapter
import com.kamiki.userhub.data.response.DetailUserResponse
import com.kamiki.userhub.databinding.ActivityDetailUserBinding
import com.kamiki.userhub.ui.viewmodel.MainViewModel

@Suppress("NAME_SHADOWING")
class DetailUserActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers, R.string.following
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]


        val actionbar = supportActionBar
        this.title = "User Detail"
        actionbar.run {
            this!!.title = "User Detail"
            setDisplayHomeAsUpEnabled(true)
        }

        val login = intent.getStringExtra(EXTRA_USERNAME).toString()

        if (savedInstanceState == null) {
            mainViewModel.findUserDetail(login)
        }

        mainViewModel.DetailUser.observe(this) { username ->
            setDetailUserData(username)
        }

        mainViewModel.IsLoading.observe(this) {
            showLoading(it)
        }


        val sectionPagerAdapter = SectionPagerAdapter(this, login)
        val viewPager: ViewPager2 = binding.viewpager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailUserData(username: DetailUserResponse) {
        binding.name.text = username.name
        binding.username.text = username.login
        Glide.with(this).load(username.avatarUrl).circleCrop().into(binding.image)
        binding.followers.text = username.followers.toString() + " followers"
        binding.following.text = username.following.toString() + " following"

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}