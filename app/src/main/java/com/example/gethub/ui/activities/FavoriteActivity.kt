package com.example.gethub.ui.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gethub.R
import com.example.gethub.data.local.database.Favorite
import com.example.gethub.data.local.statics.DetailObject
import com.example.gethub.databinding.ActivityFavoriteBinding
import com.example.gethub.ui.adapter.FavoriteAdapter
import com.example.gethub.ui.viewmodel.FavoriteViewModel
import com.example.gethub.ui.viewmodel.RoomViewModelFactory
import com.example.gethub.util.EspressoUtil

class FavoriteActivity : AppCompatActivity(), FavoriteAdapter.OnItemClicked {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!
    private var mListUserFavorite: ArrayList<Favorite> = ArrayList()
    private var mListUserToBeDelete: ArrayList<Favorite> = ArrayList()
    private lateinit var adapter: FavoriteAdapter
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        RoomViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        setAdapter(false)
        setSwipeRefresh()

        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_delete_menu -> {
                mListUserToBeDelete.forEach {
                    favoriteViewModel.delete(it)
                }
            }

            R.id.select_all_fav_menu -> {
                setAdapter(true)
                adapter.submitList(mListUserFavorite)
            }

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(binding.favoriteToolbar)
        supportActionBar?.title = resources.getString(R.string.favorite_title)
        binding.favoriteToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setSwipeRefresh() {
        binding.includeContentFavorite.srlFavorite.setOnRefreshListener {
            observeData()
            binding.includeContentFavorite.srlFavorite.isRefreshing = false
        }
    }

    private fun observeData() {
        favoriteViewModel.getAllFavorites().observe(this, {
            EspressoUtil.increment()
            mListUserFavorite = it as ArrayList<Favorite>
            adapter.submitList(mListUserFavorite)
            EspressoUtil.decrement()
        })

        favoriteViewModel.isLoading.observe(this, {
            when (it) {
                true -> {
                    binding.includeContentFavorite.pbFavorite.visibility = View.VISIBLE
                }

                else -> {
                    binding.includeContentFavorite.pbFavorite.visibility = View.GONE
                }
            }
        })
    }

    private fun setAdapter(isCheckboxAllSelected: Boolean) {
        adapter = FavoriteAdapter(this, isCheckboxAllSelected)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.includeContentFavorite.rvFavorite.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.includeContentFavorite.rvFavorite.layoutManager = LinearLayoutManager(this)
        }
        binding.includeContentFavorite.rvFavorite.setHasFixedSize(true)
        binding.includeContentFavorite.rvFavorite.adapter = adapter
    }


    override fun onUsersToBeDelete(listUserToBeDelete: ArrayList<Favorite>) {
        this.mListUserToBeDelete = listUserToBeDelete
    }

    override fun onItemClicked(username: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailObject.KEY_INTENT_DETAIL, username)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}