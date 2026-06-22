package com.example.pico_botella.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val img: String
)

data class PokemonResponse(
    @SerializedName("pokemon")
    val pokemonList: MutableList<Pokemon>
)
