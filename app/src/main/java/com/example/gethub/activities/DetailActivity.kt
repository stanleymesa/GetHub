package com.example.gethub.activities


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gethub.R
import com.example.gethub.databinding.ActivityDetailBinding
import com.example.gethub.responses.DetailResponse
import com.example.gethub.statics.DetailObject
import com.example.gethub.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailResponse: DetailResponse
    private val viewModel by viewModels<DetailViewModel>()

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
        viewModel.getDetailUser(intentUsername)
        viewModel.detailUser.observe(this, {
            detailResponse = it
            setDetail(detailResponse)
        })
        viewModel.isLoading.observe(this, {
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

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}