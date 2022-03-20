package com.example.gethub.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.gethub.R
import com.example.gethub.ui.adapter.FollowersPagerAdapter
import com.example.gethub.databinding.ActivityFollowersBinding
import com.example.gethub.data.remote.responses.DetailResponse
import com.example.gethub.data.local.statics.DetailObject
import com.google.android.material.tabs.TabLayoutMediator

class FollowersActivity : AppCompatActivity() {

    private var _binding: ActivityFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFollowersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        setTabWithViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.followers_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.followers_home_menu -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.followersToolbar)
        supportActionBar?.title = detailUserIntent.login
        binding.followersToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setTabWithViewPager() {
        val followersPagerAdapter = FollowersPagerAdapter(this)
        val viewPager = binding.includeContentFollowers.viewpager
        val tabLayout = binding.includeContentFollowers.tablayout

        followersPagerAdapter.username = detailUserIntent.login!!
        viewPager.adapter = followersPagerAdapter
        viewPager.currentItem = selectedTabs

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                DetailObject.TAB_FOLLOWERS_INDEX -> {
                    tab.text =
                        resources.getString(R.string.tab_followers, detailUserIntent.followers)
                }
                DetailObject.TAB_FOLLOWING_INDEX -> {
                    tab.text =
                        resources.getString(R.string.tab_following, detailUserIntent.following)
                }
            }
        }.attach()
    }

    private val detailUserIntent: DetailResponse
        get() {
            val detailUser =
                intent.getParcelableExtra<DetailResponse>(DetailObject.KEY_INTENT_FOLLOWERS)
            detailUser?.let {
                return detailUser
            }
            return DetailResponse()
        }

    private val selectedTabs: Int
        get() {
            return intent.getIntExtra(DetailObject.KEY_SELECTED_TABS, 0)
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}