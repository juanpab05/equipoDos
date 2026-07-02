package com.example.pico_botella.repository

import com.example.pico_botella.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.pico_botella.webservice.ApiService
import com.example.pico_botella.webservice.ApiUtils

class PokemonRepository {
    private var apiService: ApiService = ApiUtils.getApiService()

    suspend fun getRandomPokemon(): Pokemon? = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getPokemons()
            print(response)
            val listaPokemon = response.pokemonList

            if (listaPokemon.isNotEmpty()) {

                val randomIndex = (0 until listaPokemon.size).random()
                listaPokemon[randomIndex]
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}