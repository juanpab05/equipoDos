package com.example.pico_botella.fragments

import android.R.attr.ordering
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import com.example.pico_botella.R
import com.example.pico_botella.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        blinkingButtonAnimation()
        touchToolbarButton()
    }

    fun blinkingButtonAnimation(){
        // 1. Fade Out Animation
        val fadeOut = AlphaAnimation(1.0f, 0.7f).apply {
            duration = 1000 // 1 seconds
            repeatCount = Animation.INFINITE // Loop forever
            repeatMode = Animation.REVERSE   // Reverse animation after loop
            interpolator = DecelerateInterpolator()
        }

        // 2. Create AnimationSet
        val animationSet = AnimationSet(true).apply {
            // Add animation to the set
            addAnimation(fadeOut)
        }

        // Start the animation on the image view
        binding.ivButtonSpin.startAnimation(animationSet)
    }

    //Controla que ocurre al presionar un boton de la toolbar
    fun touchToolbarButton(){
        val expand = ScaleAnimation(1.0F, 1.3F, 1.0F, 1.3F).apply{
            duration = 250 //  250 milliseconds
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }
        val move = TranslateAnimation(0F, -20F, 0F, -35F).apply{
            duration = 250
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }

        val animationSet = AnimationSet(true).apply {
            // Add animations to the set
            addAnimation(expand)
            addAnimation(move)
        }

        //Media player para reproducir la canción de home
        val mediaPlayer = MediaPlayer.create(context, R.raw.home)

        val rateBtn = binding.toolbar.ivStars
        val playBtn = binding.toolbar.ivSound
        val rulesBtn = binding.toolbar.ivController
        val addBtn = binding.toolbar.ivPlus
        val shareBtn = binding.toolbar.ivShare

        rateBtn.setOnClickListener {
            rateBtn.startAnimation(animationSet)
        }

        playBtn.setOnClickListener {
            playBtn.startAnimation(animationSet)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start() // no need to call prepare(); create() does that for you
            }
        }

        rulesBtn.setOnClickListener {
            rulesBtn.startAnimation(animationSet)
        }

        addBtn.setOnClickListener {
            addBtn.startAnimation(animationSet)
        }

        shareBtn.setOnClickListener {
            shareBtn.startAnimation(animationSet)
            share()
        }
    }


    fun share(){
        val message = getString(R.string.share_app)

        val intent = Intent().apply{
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }



}