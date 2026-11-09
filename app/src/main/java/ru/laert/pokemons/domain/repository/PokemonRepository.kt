package ru.laert.pokemons.domain.repository

import ru.laert.pokemons.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemons(): List<Pokemon>

    suspend fun getPokemonById(id: Int): Pokemon
}