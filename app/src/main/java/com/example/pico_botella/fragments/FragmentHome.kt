package com.example.pico_botella.fragments

import android.R.attr.ordering
import android.animation.ObjectAnimator
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
import androidx.core.animation.doOnEnd
import com.example.pico_botella.R
import com.example.pico_botella.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var currentRotation = 0f          // guarda la posición de la botella
    private var isSpinning = false            // bloqueo del botón si la botella está girando

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonBlinking()
        spinBottle()
        playMusic()
        shareApp()
    }

    fun buttonBlinking(){
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

    fun animationBottle(){
        if (isSpinning) return // Evitar animar la botella si ya está girando

        val bottle = binding.ivBottle
        val spinningSound = MediaPlayer.create(context, R.raw.spinning)

        val steps = (0 until 360 step 30).toList() // [0, 30, 60, 90, ..., 330]
        val stopPosition = steps.random().toFloat()

        // Giros completos + parada en el grado aleatorio
        val fullRotations = 360f * 3 // 3 vueltas completas antes de parar
        val finalAngle = currentRotation + fullRotations + stopPosition

        ObjectAnimator.ofFloat(bottle, "rotation", currentRotation, finalAngle).apply {
            spinningSound.start()

            duration = 4000
            interpolator = DecelerateInterpolator() // arranca rápido, frena suave
            currentRotation = finalAngle % 360 // actualizar la posición de la botella

            doOnEnd {
                isSpinning = false
                spinningSound.pause()
            }

            start()
        }
    }

    fun spinBottle(){
        val spinBtn = binding.ivButtonSpin
        spinBtn.setOnClickListener {
            animationBottle()
            isSpinning = true
        }
    }

    fun playMusic(){
        val playBtn = binding.toolbar.ivSound
        val mediaPlayer = MediaPlayer.create(context, R.raw.home)
        playBtn.setOnClickListener {
            if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
            } else {
                mediaPlayer.start() // no need to call prepare(); create() does that for you
            }
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

    fun shareApp(){
        val shareBtn = binding.toolbar.ivShare
        shareBtn.setOnClickListener {
            share()
        }
    }

}