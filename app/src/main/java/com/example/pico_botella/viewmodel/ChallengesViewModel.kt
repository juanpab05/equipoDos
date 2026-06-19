package com.example.pico_botella.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pico_botella.data.AppDB
import com.example.pico_botella.model.Challenge
import com.example.pico_botella.repository.ChallengeRepository
import kotlinx.coroutines.launch

/**
 * ViewModel de la pantalla "Agregar y listar retos".
 * Usa AndroidViewModel para acceder al contexto de la app (necesario para Room).
 * Las operaciones de BD se ejecutan con corrutinas en viewModelScope.
 */
class ChallengesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChallengeRepository
    val allChallenges: LiveData<List<Challenge>>

    init {
        val dao = AppDB.getDatabase(application).challengeDao()
        repository = ChallengeRepository(dao)
        allChallenges = repository.allChallenges.asLiveData()
    }

    fun insert(challenge: Challenge) = viewModelScope.launch {
        repository.insert(challenge)
    }

    // (HU 8.0)
    fun update(challenge: Challenge) = viewModelScope.launch {
        repository.update(challenge)
    }

    //(HU 9.0)
    fun delete(challenge: Challenge) = viewModelScope.launch {
        repository.delete(challenge)
    }

}