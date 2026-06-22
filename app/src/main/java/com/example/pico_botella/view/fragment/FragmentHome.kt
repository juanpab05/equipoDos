package com.example.pico_botella.view.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import androidx.core.animation.doOnEnd
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pico_botella.R
import com.example.pico_botella.databinding.FragmentHomeBinding
import com.example.pico_botella.view.dialog.DialogSpinResult
import com.example.pico_botella.viewmodel.HomeViewModel

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var backgroundMusic: MediaPlayer? = null
    private var wasMusicPlaying = false
    private var currentRotation = 0f

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
        spinBottle()
        observeRandomChallenge()
    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.isMusicPlaying.value == true && backgroundMusic?.isPlaying == false) {
            backgroundMusic?.start()
        }
    }

    private fun observeRandomChallenge() {
        homeViewModel.randomChallenge.observe(viewLifecycleOwner) { challenge ->
            if (challenge != null) {
                homeViewModel.clearRandomChallenge()
                val dialog = DialogSpinResult(challenge.description)
                dialog.show(parentFragmentManager, "DialogSpinResult")
            }
        }
    }

    fun blinkingButtonAnimation(){
        val fadeOut = AlphaAnimation(1.0f, 0.7f).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
            interpolator = DecelerateInterpolator()
        }

        val animationSet = AnimationSet(true).apply {
            addAnimation(fadeOut)
        }

        binding.ivButtonSpin.startAnimation(animationSet)
    }

    fun touchToolbarButton(){
        val expand = ScaleAnimation(1.0F, 1.3F, 1.0F, 1.3F).apply{
            duration = 250
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }
        val move = TranslateAnimation(0F, -20F, 0F, -35F).apply{
            duration = 250
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }

        val animationSet = AnimationSet(true).apply {
            addAnimation(expand)
            addAnimation(move)
        }

        backgroundMusic = MediaPlayer.create(context, R.raw.home)

        val rateBtn = binding.toolbar.ivStars
        val playBtn = binding.toolbar.ivSound
        val rulesBtn = binding.toolbar.ivController
        val addBtn = binding.toolbar.ivPlus
        val shareBtn = binding.toolbar.ivShare

        rateBtn.setOnClickListener {
            rateBtn.startAnimation(animationSet)

            rateApp()
        }

        playBtn.setOnClickListener {
            playBtn.startAnimation(animationSet)
            if (backgroundMusic?.isPlaying == true){
                backgroundMusic?.pause()
            } else {
                backgroundMusic?.start()
            }
        }

        rulesBtn.setOnClickListener {
            rulesBtn.startAnimation(animationSet)
            val wasPlaying = backgroundMusic?.isPlaying == true

            if (wasPlaying) backgroundMusic?.pause()
            homeViewModel.setMusicPlaying(wasPlaying)

            findNavController().navigate(R.id.action_fragmentHome_to_fragmentRules)
        }

        addBtn.setOnClickListener {
            addBtn.startAnimation(animationSet)
            val wasPlaying = backgroundMusic?.isPlaying == true
            if (wasPlaying) backgroundMusic?.pause()
            homeViewModel.setMusicPlaying(wasPlaying)
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentChallenges)
        }

        shareBtn.setOnClickListener {
            shareBtn.startAnimation(animationSet)
            share()
        }
    }

    fun restartCountdown(){
        val countdownText = binding.tvCountdown
        countdownText.text = "3"
        countdownText.visibility = View.INVISIBLE
    }

    fun countdown(){
        val spinBtn = binding.ivButtonSpin
        val countdownText = binding.tvCountdown

        countdownText.visibility = View.VISIBLE

        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000 + 1
                countdownText.text = secondsLeft.toString()
            }

            override fun onFinish() {
                countdownText.text = "0"
                spinBtn.visibility = View.VISIBLE
                blinkingButtonAnimation()

                homeViewModel.loadRandomChallenge()
            }
        }

        timer.start()
    }

    fun animationBottle(){
        val bottle = binding.ivBottle
        val spinningSound = MediaPlayer.create(context, R.raw.spinning)

        val angles = (0 until 360 step 30).toList()
        val stopPosition = angles.random().toFloat()
        val fullRotations = 360f * 3

        val finalAngle = currentRotation + fullRotations + stopPosition

        ObjectAnimator.ofFloat(bottle, "rotation", currentRotation, finalAngle).apply {
            spinningSound.start()

            duration = 4000
            interpolator = DecelerateInterpolator()
            currentRotation = finalAngle % 360

            doOnEnd {
                spinningSound.pause()
                spinningSound.release()
                countdown()
            }

            start()
        }
    }

    fun spinBottle(){
        val spinBtn = binding.ivButtonSpin

        spinBtn.setOnClickListener {
            restartCountdown()

            spinBtn.clearAnimation()
            spinBtn.visibility = View.INVISIBLE

            wasMusicPlaying = backgroundMusic?.isPlaying == true
            if (wasMusicPlaying) backgroundMusic?.pause()

            animationBottle()
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

    fun rateApp() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"
                .toUri()
        )
        startActivity(intent)
    }
}
