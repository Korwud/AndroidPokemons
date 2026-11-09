package ru.laert.pokemons.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.laert.pokemons.data.remote.dto.PokemonDetailDto
import ru.laert.pokemons.data.remote.dto.PokemonListResponse

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ) : PokemonDetailDto
}