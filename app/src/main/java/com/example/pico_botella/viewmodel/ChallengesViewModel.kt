package com.example.pico_botella.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pico_botella.model.Challenge
import com.example.pico_botella.repository.ChallengeRepository
import kotlinx.coroutines.launch

class ChallengesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChallengeRepository = ChallengeRepository(application)
    val allChallenges: LiveData<List<Challenge>> = repository.allChallenges.asLiveData()

    fun insert(challenge: Challenge) = viewModelScope.launch { repository.insert(challenge) }
    fun update(challenge: Challenge) = viewModelScope.launch { repository.update(challenge) }
    fun delete(challenge: Challenge) = viewModelScope.launch { repository.delete(challenge) }
}