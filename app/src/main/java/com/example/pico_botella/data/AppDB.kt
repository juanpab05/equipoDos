package com.example.pico_botella.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pico_botella.model.Challenge
import com.example.pico_botella.utils.Constants.NAME_BD

/**
 * Base de datos Room de la aplicación.
 */
@Database(entities = [Challenge::class], version = 1)
abstract class AppDB : RoomDatabase()  {
    abstract fun challengeDao(): ChallengeDao
    companion object{
        fun getDatabase(context: Context): AppDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                NAME_BD
            ).build()
        }
    }
}