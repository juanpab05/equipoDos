package com.example.pico_botella.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Entidad Room que representa un reto en la base de datos SQLite.
 * Cada reto tiene un id autogenerado y una descripción de texto.
 */
@Entity
data class Challenge (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String): Serializable