package com.example.pico_botella.data

import androidx.room.*
import com.example.pico_botella.model.Challenge
import kotlinx.coroutines.flow.Flow
/**
 * DAO (Data Access Object) para la entidad Reto.
 * Define todas las operaciones posibles contra la tabla "retos".
 */
@Dao
interface ChallengeDao {

    // Retorna todos los retos ordenados del más nuevo al más viejo
    @Query("SELECT * FROM Challenge ORDER BY id DESC")
    fun getAllChallenges(): Flow<List<Challenge>>

    @Insert
    suspend fun insertChallenge(challenge: Challenge)

    //(HU 8.0 - encargado de editar)
    @Update
    suspend fun updateChallenge(challenge: Challenge)

    //(HU 9.0 - encargado de eliminar)
    @Delete
    suspend fun deleteChallenge(challenge: Challenge)
}