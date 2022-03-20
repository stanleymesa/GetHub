package com.example.gethub.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gethub.R
import com.example.gethub.data.local.datastore.SettingPreferences
import com.example.gethub.data.local.statics.DetailObject
import com.example.gethub.data.remote.responses.ItemsItem
import com.example.gethub.data.repository.RetrofitRepository
import com.example.gethub.databinding.ActivityMainBinding
import com.example.gethub.ui.adapter.UserAdapter
import com.example.gethub.ui.viewmodel.MainViewModel
import com.example.gethub.ui.viewmodel.PrefViewModelFactory
import com.example.gethub.ui.viewmodel.PreferencesViewModel
import com.example.gethub.ui.viewmodel.RetrofitViewModelFactory
import com.google.android.material.snackbar.Snackbar

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickCallback {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        RetrofitViewModelFactory.getInstance(RetrofitRepository())
    }
    private lateinit var mMenu: Menu
    private val prefViewModel by viewModels<PreferencesViewModel> {
        PrefViewModelFactory.getInstance(SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        observeData()
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
            observeData()
            binding.includeContentMain.srlMain.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        mMenu = menu
        menuInflater.inflate(R.menu.main_menu, menu)

        prefViewModel.getThemeTitlePref().observe(this, {
            menu.findItem(R.id.darkmode_menu).title = it
        })

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)


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

        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View?) {
                menu.findItem(R.id.favorite_menu).isVisible = false
                menu.findItem(R.id.darkmode_menu).isVisible = false
                searchView.onActionViewExpanded()
                searchView.requestFocus()
            }

            override fun onViewDetachedFromWindow(p0: View?) {
                menu.findItem(R.id.favorite_menu).isVisible = true
                menu.findItem(R.id.darkmode_menu).isVisible = true
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }

            R.id.darkmode_menu -> {
                if (mMenu.findItem(R.id.darkmode_menu).title.equals(resources.getString(R.string.dark_mode))) {
                    prefViewModel.saveThemePref(true)
                } else {
                    prefViewModel.saveThemePref(false)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun observeData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkInternet()
        }

        prefViewModel.getThemePref().observe(this, { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefViewModel.saveThemeTitlePref(resources.getString(R.string.light_mode))
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefViewModel.saveThemeTitlePref(resources.getString(R.string.dark_mode))
            }
        })

        viewModel.textOnSubmit.observe(this, {
            when (it) {
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