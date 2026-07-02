package com.example.pico_botella.webservice

import com.example.pico_botella.model.PokemonResponse
import com.example.pico_botella.utils.Constants.END_POINT
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT)
    suspend fun getPokemons(): PokemonResponse
}