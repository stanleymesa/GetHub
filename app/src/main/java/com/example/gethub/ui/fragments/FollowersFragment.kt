package com.example.gethub.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gethub.R
import com.example.gethub.data.local.statics.DetailObject
import com.example.gethub.data.remote.responses.FollowersFollowingResponseItem
import com.example.gethub.data.repository.RetrofitRepository
import com.example.gethub.databinding.FragmentFollowersBinding
import com.example.gethub.ui.activities.DetailActivity
import com.example.gethub.ui.adapter.FollowersAdapter
import com.example.gethub.ui.viewmodel.FollowersViewModel
import com.example.gethub.ui.viewmodel.FollowingViewModel
import com.example.gethub.ui.viewmodel.RetrofitViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FollowersFragment : Fragment(), FollowersAdapter.OnItemClickCallback {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val retrofitRepository = RetrofitRepository()

    private val followersViewModel by viewModels<FollowersViewModel> {
        RetrofitViewModelFactory.getInstance(retrofitRepository)
    }
    private val followingViewModel by viewModels<FollowingViewModel> {
        RetrofitViewModelFactory.getInstance(retrofitRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setSwipeRefresh()
    }

    private val username: String
        get() {
            arguments?.let {
                return it.getString(DetailObject.KEY_USERNAME_FOLLOWERS, "")
            }
            return ""
        }

    private val selectedTabs: Int
        get() {
            arguments?.let {
                return it.getInt(DetailObject.KEY_SELECTED_TABS)
            }
            return DetailObject.TAB_FOLLOWERS_INDEX
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternet() {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork == null) {
            Snackbar.make(
                requireActivity().findViewById(R.id.root_followers),
                DetailObject.INTERNET_ERROR_MESSAGE,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setSwipeRefresh() {
        binding.srlFollowers.setOnRefreshListener {
            observeData()
            binding.srlFollowers.isRefreshing = false
        }
    }

    private fun observeData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkInternet()
        }
        when (selectedTabs) {
            DetailObject.TAB_FOLLOWERS_INDEX -> {
                followersViewModel.getFollowers(username)
                followersViewModel.listFollowers.observe(requireActivity(), {
                    setAdapter(it)
                })
                followersViewModel.isLoading.observe(requireActivity(), {
                    when (it) {
                        true -> binding.pbFollowers.visibility = View.VISIBLE
                        else -> binding.pbFollowers.visibility = View.GONE
                    }
                })
            }

            else -> {
                followingViewModel.getFollowing(username)
                followingViewModel.listFollowing.observe(requireActivity(), {
                    setAdapter(it)
                })
                followingViewModel.isLoading.observe(requireActivity(), {
                    when (it) {
                        true -> binding.pbFollowers.visibility = View.VISIBLE
                        else -> binding.pbFollowers.visibility = View.GONE
                    }
                })
            }
        }

    }

    private fun setAdapter(listFollowersFollowing: List<FollowersFollowingResponseItem>) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFollowersFollowing.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.rvFollowersFollowing.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvFollowersFollowing.setHasFixedSize(true)
        binding.rvFollowersFollowing.adapter = FollowersAdapter(listFollowersFollowing, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(username: String) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailObject.KEY_INTENT_DETAIL, username)
        startActivity(intent)
    }

}