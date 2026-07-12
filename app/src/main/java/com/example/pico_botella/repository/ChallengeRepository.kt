package com.example.pico_botella.repository

import android.content.Context
import com.example.pico_botella.data.AppDB
import com.example.pico_botella.data.ChallengeDao
import com.example.pico_botella.model.Challenge
import kotlinx.coroutines.flow.Flow

class ChallengeRepository(private val challengeDao: ChallengeDao) {

    constructor(context: Context) : this(AppDB.getDatabase(context).challengeDao())

    val allChallenges: Flow<List<Challenge>> = challengeDao.getAllChallenges()

    suspend fun insert(challenge: Challenge) = challengeDao.insertChallenge(challenge)
    suspend fun update(challenge: Challenge) = challengeDao.updateChallenge(challenge)
    suspend fun delete(challenge: Challenge) = challengeDao.deleteChallenge(challenge)
    suspend fun getRandomChallenge(): Challenge? = challengeDao.getRandomChallenge()
}