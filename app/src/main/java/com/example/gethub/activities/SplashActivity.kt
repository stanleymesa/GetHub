package com.example.gethub.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.gethub.R
import com.example.gethub.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var animFadeIn: Animation
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFadeIn()
        goToMain()
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