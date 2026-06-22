package com.example.pico_botella.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel de la ventana Home Principal.
 *
 * Guarda el estado del audio de fondo (encendido/apagado) para que sobreviva
 * a cambios de configuración y para que otras pantallas (p.ej. Reglas del juego,
 * HU 5.0) puedan consultar si la música estaba sonando antes de navegar y así
 * decidir si deben restaurarla al volver.
 */
class HomeViewModel : ViewModel(){
    // true = la música de fondo está sonando, false = está pausada/apagada
    private val _isMusicPlaying = MutableLiveData(false)
    val isMusicPlaying: LiveData<Boolean> get() = _isMusicPlaying

    fun setMusicPlaying(isPlaying: Boolean) {
        _isMusicPlaying.value = isPlaying
    }

}