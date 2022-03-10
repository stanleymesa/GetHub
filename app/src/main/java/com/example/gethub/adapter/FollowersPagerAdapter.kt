package com.example.gethub.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gethub.fragments.FollowersFragment
import com.example.gethub.statics.DetailObject

class FollowersPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username = ""

    override fun getItemCount(): Int {
        return DetailObject.TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersFragment()

        fragment.arguments = Bundle().apply {
            putString(DetailObject.KEY_USERNAME_FOLLOWERS, username)
            putInt(DetailObject.KEY_SELECTED_TABS, position)
        }


        return fragment
    }
}