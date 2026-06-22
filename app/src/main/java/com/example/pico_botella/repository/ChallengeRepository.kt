package com.example.pico_botella.repository

import com.example.pico_botella.data.ChallengeDao
import com.example.pico_botella.model.Challenge
import kotlinx.coroutines.flow.Flow

/**
 * Repository de Retos.
 * Es la única fuente de verdad para los datos: el ViewModel
 * nunca habla directamente con el DAO, siempre pasa por aquí.
 */
class ChallengeRepository(private val challengeDao: ChallengeDao) {
    val allChallenges: Flow<List<Challenge>> = challengeDao.getAllChallenges()

    suspend fun insert(challenge: Challenge) = challengeDao.insertChallenge(challenge)
    //(HU 8.0)
    suspend fun update(challenge: Challenge) = challengeDao.updateChallenge(challenge)

    // (HU 9.0)
    suspend fun delete(challenge: Challenge) = challengeDao.deleteChallenge(challenge)
}