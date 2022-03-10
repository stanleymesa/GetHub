package com.example.gethub.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gethub.R
import com.example.gethub.adapter.UserAdapter
import com.example.gethub.databinding.ActivityMainBinding
import com.example.gethub.responses.ItemsItem
import com.example.gethub.statics.DetailObject
import com.example.gethub.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickCallback {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        observeSearchUsers()
        setSwipeRefresh()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternet() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork == null) {
            Snackbar.make(binding.root, DetailObject.INTERNET_ERROR_MESSAGE, Snackbar.LENGTH_SHORT)
                .show()
        }

    }

    private fun setSwipeRefresh() {
        binding.includeContentMain.srlMain.setOnRefreshListener {
            observeSearchUsers()
            binding.includeContentMain.srlMain.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkInternet()
                }
                viewModel.setTextOnSubmit(name)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
        return true
    }

    private fun observeSearchUsers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkInternet()
        }

        viewModel.textOnSubmit.observe(this, {
            when(it) {
                null -> viewModel.getSearchUser(DetailObject.DEFAULT_SEARCH_USERS)
                else -> viewModel.getSearchUser(it)
            }
        })
        viewModel.searchUsers.observe(this, {
            setAdapter(it.items)
        })

        viewModel.isLoading.observe(this, {
            when {
                it -> binding.includeContentMain.pbMain.visibility = View.VISIBLE
                else -> binding.includeContentMain.pbMain.visibility = View.GONE
            }
        })
    }

    private fun setAdapter(listItemsItem: List<ItemsItem>) {

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.includeContentMain.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.includeContentMain.rvUser.layoutManager = LinearLayoutManager(this)
        }
        binding.includeContentMain.rvUser.setHasFixedSize(true)
        binding.includeContentMain.rvUser.adapter = UserAdapter(listItemsItem, this)
    }

    override fun onItemClicked(username: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailObject.KEY_INTENT_DETAIL, username)
        startActivity(intent)
    }


}