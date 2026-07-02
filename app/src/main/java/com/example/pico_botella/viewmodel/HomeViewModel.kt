package com.example.pico_botella.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pico_botella.data.AppDB
import com.example.pico_botella.model.Challenge
import com.example.pico_botella.repository.ChallengeRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChallengeRepository

    private val _isMusicPlaying = MutableLiveData(false)
    val isMusicPlaying: LiveData<Boolean> get() = _isMusicPlaying

    private val _randomChallenge = MutableLiveData<Challenge?>()
    val randomChallenge: LiveData<Challenge?> get() = _randomChallenge

    init {
        val dao = AppDB.getDatabase(application).challengeDao()
        repository = ChallengeRepository(dao)
    }

    fun setMusicPlaying(isPlaying: Boolean) {
        _isMusicPlaying.value = isPlaying
    }

    fun loadRandomChallenge() {
        viewModelScope.launch {
            _randomChallenge.value = repository.getRandomChallenge()
        }
    }

    fun clearRandomChallenge() {
        _randomChallenge.value = null
    }
}
