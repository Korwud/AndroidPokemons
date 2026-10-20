package ru.laert.pokemons.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.laert.pokemons.domain.model.Pokemon

interface FavoriteRepository {

    fun getFavorites(): Flow<List<Pokemon>>

    fun isFavorite(id: Int): Flow<Boolean>

    suspend fun addFavorite(pokemon: Pokemon)

    suspend fun removeFavorite(id: Int)
}