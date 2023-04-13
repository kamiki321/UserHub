package com.kamiki.userhub.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kamiki.userhub.ui.userview.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity, private var login: String) :
    FragmentStateAdapter(activity) {


    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_USERNAME, login)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}