package com.example.gethub.ui.activities


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gethub.R
import com.example.gethub.data.local.database.Favorite
import com.example.gethub.data.local.statics.DetailObject
import com.example.gethub.data.remote.responses.DetailResponse
import com.example.gethub.data.repository.RetrofitRepository
import com.example.gethub.databinding.ActivityDetailBinding
import com.example.gethub.ui.viewmodel.DetailViewModel
import com.example.gethub.ui.viewmodel.FavoriteViewModel
import com.example.gethub.ui.viewmodel.RetrofitViewModelFactory
import com.example.gethub.ui.viewmodel.RoomViewModelFactory
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private var isFavorite = false
    private lateinit var favoriteUser: Favorite
    private var detailResponse: DetailResponse = DetailResponse()
    private val detailViewModel by viewModels<DetailViewModel> {
        RetrofitViewModelFactory.getInstance(RetrofitRepository())
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        RoomViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        observeDetailUser()
        setSwipeRefresh()

    }

    private val intentUsername: String
        get() {
            val username = intent.getStringExtra(DetailObject.KEY_INTENT_DETAIL)
            username?.let {
                return username
            }
            return ""
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_home_menu -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternet() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork == null) {
            binding.includeContentDetail.layoutDetail.visibility = View.GONE
            Snackbar.make(binding.root, DetailObject.INTERNET_ERROR_MESSAGE, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun setSwipeRefresh() {
        binding.includeContentDetail.srlDetail.setOnRefreshListener {
            observeDetailUser()
            binding.includeContentDetail.srlDetail.isRefreshing = false
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.title = intentUsername
        binding.detailToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun observeDetailUser() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkInternet()
        }

        detailViewModel.getDetailUser(intentUsername)
        detailViewModel.detailUser.observe(this, {
            detailResponse = it
            setDetail(detailResponse)

            favoriteViewModel.getAllFavorites().observe(this, { listFavorite ->
                setFab(listFavorite)
            })
        })


        detailViewModel.isLoading.observe(this, {
            when (it) {
                true -> {
                    binding.includeContentDetail.layoutDetail.visibility = View.GONE
                    binding.includeContentDetail.pbDetail.visibility = View.VISIBLE
                }

                else -> {
                    binding.includeContentDetail.layoutDetail.visibility = View.VISIBLE
                    binding.includeContentDetail.pbDetail.visibility = View.GONE
                }
            }
        })
        favoriteViewModel.isLoading.observe(this, {
            when (it) {
                true -> {
                    binding.fabDetail.isEnabled = false
                    binding.includeContentDetail.pbDetail.visibility = View.VISIBLE
                }

                else -> {
                    binding.fabDetail.isEnabled = true
                    binding.includeContentDetail.pbDetail.visibility = View.GONE
                }
            }
        })
    }

    private fun setFab(listFavoriteUser: List<Favorite>) {
        listFavoriteUser.forEach {
            if (it.idUser == detailResponse.id) {
                binding.fabDetail.setImageResource(R.drawable.redheart)
                isFavorite = true
                favoriteUser = it
                return
            }
        }
    }

    private fun setDetail(dataUser: DetailResponse) {

        Glide.with(this)
            .load(dataUser.avatarUrl)
            .placeholder(R.drawable.ic_baseline_person_pin_24)
            .circleCrop()
            .into(binding.includeContentDetail.ivAvatar)

        binding.includeContentDetail.tvUsername.text = dataUser.login
        binding.includeContentDetail.tvName.text = dataUser.name
        binding.includeContentDetail.tvRepo.text = dataUser.publicRepos.toString()
        binding.includeContentDetail.tvFollowers.text = dataUser.followers.toString()
        binding.includeContentDetail.tvFollowing.text = dataUser.following.toString()
        binding.includeContentDetail.tvLocation.text = DetailObject.DEFAULT_NULL
        binding.includeContentDetail.tvCompany.text = DetailObject.DEFAULT_NULL
        dataUser.location?.let {
            binding.includeContentDetail.tvLocation.text = dataUser.location
        }
        dataUser.company?.let {
            binding.includeContentDetail.tvCompany.text = dataUser.company
        }
        binding.includeContentDetail.frameFollowers.setOnClickListener(this)
        binding.includeContentDetail.frameFollowing.setOnClickListener(this)
        binding.fabDetail.setOnClickListener(this)
    }

    private fun insertFavoriteUser() {
        val favorite = Favorite(
            idUser = detailResponse.id!!,
            avatarUrl = detailResponse.avatarUrl!!,
            login = detailResponse.login!!
        )

        favoriteViewModel.insert(favorite)
    }

    private fun deleteFavoriteUser() {
        favoriteViewModel.delete(favoriteUser)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.frame_followers -> {
                val intent = Intent(this, FollowersActivity::class.java)
                intent.apply {
                    putExtra(DetailObject.KEY_INTENT_FOLLOWERS, detailResponse)
                    putExtra(DetailObject.KEY_SELECTED_TABS, DetailObject.TAB_FOLLOWERS_INDEX)
                    startActivity(intent)
                }
            }

            R.id.frame_following -> {
                val intent = Intent(this, FollowersActivity::class.java)
                intent.apply {
                    putExtra(DetailObject.KEY_INTENT_FOLLOWERS, detailResponse)
                    putExtra(DetailObject.KEY_SELECTED_TABS, DetailObject.TAB_FOLLOWING_INDEX)
                    startActivity(intent)
                }
            }

            R.id.fab_detail -> {
                isFavorite = if (isFavorite) {
                    deleteFavoriteUser()
                    binding.fabDetail.setImageResource(R.drawable.emptyheart)
                    false
                } else {
                    insertFavoriteUser()
                    binding.fabDetail.setImageResource(R.drawable.redheart)
                    true
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}