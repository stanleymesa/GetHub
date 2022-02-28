package com.example.gethub.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gethub.R
import com.example.gethub.adapter.UserAdapter
import com.example.gethub.databinding.ActivityMainBinding
import com.example.gethub.models.User
import com.example.gethub.statics.DetailObject
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickCallback, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        binding.includeActionBar.ivClose.setOnClickListener(this)

    }

    private val listUsers: ArrayList<User>
        get() {
            val avatar = resources.getStringArray(R.array.avatar)
            val username = resources.getStringArray(R.array.username)
            val name = resources.getStringArray(R.array.name)
            val location = resources.getStringArray(R.array.location)
            val repository = resources.getStringArray(R.array.repository)
            val company = resources.getStringArray(R.array.company)
            val followers = resources.getStringArray(R.array.followers)
            val following = resources.getStringArray(R.array.following)
            val list = arrayListOf<User>()
            for (i in avatar.indices) {
                val user = User(
                    avatar = avatar[i],
                    username = username[i],
                    name = name[i],
                    location = location[i],
                    repository = repository[i],
                    company = company[i],
                    followers = followers[i],
                    following = following[i],
                )
                list.add(user)
            }
            return list
        }

    private fun setAdapter() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        binding.rvUser.adapter = UserAdapter(listUsers, this)
    }

    override fun onItemClicked(dataUser: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailObject.KEY_INTENT_DETAIL, dataUser)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_close -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Close Application")
                    .setCancelable(true)
                    .setMessage("Apakah anda yakin ingin keluar?")
                    .setIcon(R.drawable.ic_baseline_directions_run_24)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK") { _, _ -> finishAffinity() }
                    .show()
            }
        }
    }
}