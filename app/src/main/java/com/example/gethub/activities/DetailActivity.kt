package com.example.gethub.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gethub.R
import com.example.gethub.databinding.ActivityDetailBinding
import com.example.gethub.models.User
import com.example.gethub.statics.DetailObject
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var dataUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetail()
    }

    private fun setDetail() {
        dataUser = intent.getParcelableExtra(DetailObject.KEY_INTENT_DETAIL) ?: User()

        Glide.with(this)
            .load(resources.getIdentifier(dataUser.avatar, "drawable", packageName))
            .circleCrop()
            .into(binding.ivAvatar)

        binding.includeActionBar.tvActionUsername.text = dataUser.username
        binding.tvUsername.text = dataUser.username
        binding.tvName.text = dataUser.name
        binding.tvRepo.text = dataUser.repository
        binding.tvFollowers.text = dataUser.followers
        binding.tvFollowing.text = dataUser.following
        binding.tvLocation.text = dataUser.location
        binding.tvCompany.text = dataUser.company

        binding.includeActionBar.ivBack.setOnClickListener(this)
        binding.includeActionBar.ivClose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
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