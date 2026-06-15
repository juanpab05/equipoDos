package com.example.pico_botella.fragments

import android.R.attr.ordering
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import com.example.pico_botella.R
import com.example.pico_botella.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var backgroundMusic: MediaPlayer? = null   // ahora es propiedad de clase
    private var wasMusicPlaying = false                // recuerda si sonaba antes de girar
    private var currentRotation = 0f          // guarda la posición de la botella

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
        backgroundMusic = MediaPlayer.create(context, R.raw.home)

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
            if (backgroundMusic?.isPlaying == true){
                backgroundMusic?.pause()
            } else {
                backgroundMusic?.start()
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

    fun restartCountdown(){
        val countdownText = binding.tvCountdown
        countdownText.text = "3"
        countdownText.visibility = View.INVISIBLE
    }

    fun countdown(){
        val spinBtn = binding.ivButtonSpin
        val countdownText = binding.tvCountdown

        countdownText.visibility = View.VISIBLE // Volver visible el contador

        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000 + 1
                countdownText.text = secondsLeft.toString() // El contador va cambiando el numero
            }

            override fun onFinish() {
                countdownText.text = "0" // El contador acaba en 0
                spinBtn.visibility = View.VISIBLE // Reaparece el boton de girar la botella
                blinkingButtonAnimation() // Se le vuelve a aplicar la animación
                // Aqui deberia ir la funcion de la HU 12
                // Además la HU 12 debe encargarse de reanudar la musica de fondo si estaba sonando
            }
        }

        timer.start()
    }

    fun animationBottle(){
        val bottle = binding.ivBottle
        val spinningSound = MediaPlayer.create(context, R.raw.spinning)

        val angles = (0 until 360 step 30).toList() // [0, 30, 60, 90, ..., 330]
        val stopPosition = angles.random().toFloat()
        val fullRotations = 360f * 3 // 3 vueltas completas antes de parar

        val finalAngle = currentRotation + fullRotations + stopPosition

        ObjectAnimator.ofFloat(bottle, "rotation", currentRotation, finalAngle).apply {
            spinningSound.start()

            duration = 4000
            interpolator = DecelerateInterpolator() // arranca rápido, frena suave
            currentRotation = finalAngle % 360 // actualizar la posición de la botella

            doOnEnd {
                spinningSound.pause() // Pausar el sonido de la botella al terminar la animación
                spinningSound.release() // Liberamos memoria
                countdown() // Activamos el contador
            }

            start()
        }
    }

    fun spinBottle(){
        val spinBtn = binding.ivButtonSpin

        spinBtn.setOnClickListener {
            restartCountdown() // Reinicia y esconde el contador

            spinBtn.clearAnimation() // Primero limpia la animación del boton
            spinBtn.visibility = View.INVISIBLE // Desaparece el botón

            wasMusicPlaying = backgroundMusic?.isPlaying == true // Setear la variable a true si la musica de fondo está sonando
            if (wasMusicPlaying) backgroundMusic?.pause() // Detener la musica de fondo si estaba sonando

            animationBottle() // Inicia la animación de la botella
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