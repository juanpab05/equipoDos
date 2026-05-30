package com.example.pico_botella.fragments

import android.R.attr.ordering
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
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
        buttonBlinking()
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

}