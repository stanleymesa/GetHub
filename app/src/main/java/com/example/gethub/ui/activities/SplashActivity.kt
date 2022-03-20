package com.example.gethub.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.gethub.R
import com.example.gethub.data.local.datastore.SettingPreferences
import com.example.gethub.databinding.ActivitySplashBinding
import com.example.gethub.ui.viewmodel.PrefViewModelFactory
import com.example.gethub.ui.viewmodel.PreferencesViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var animFadeIn: Animation
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val prefViewModel by viewModels<PreferencesViewModel> {
        PrefViewModelFactory.getInstance(SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
        setFadeIn()
        goToMain()
    }

    private fun observeData() {
        prefViewModel.getThemePref().observe(this, { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefViewModel.saveThemeTitlePref(resources.getString(R.string.light_mode))
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefViewModel.saveThemeTitlePref(resources.getString(R.string.dark_mode))
            }
        })
    }

    private fun setFadeIn() {
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in)
        binding.ivLogo.animation = animFadeIn
        binding.tvGethub.animation = animFadeIn
    }

    private fun goToMain() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        handler.postDelayed(runnable, 4000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}