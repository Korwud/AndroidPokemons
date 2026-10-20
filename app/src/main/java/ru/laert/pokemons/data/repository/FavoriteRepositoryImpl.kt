package ru.laert.pokemons.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.laert.pokemons.data.local.dao.FavoriteDao
import ru.laert.pokemons.data.local.entity.FavoritePokemonEntity
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val dao: FavoriteDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<Pokemon>> {
        return dao.getFavorites().map { entities ->
            entities.map { entity ->
                Pokemon(
                    id = entity.id,
                    name = entity.name,
                    imageUrl = entity.imageUrl,
                    types = emptyList(),
                    abilities = emptyList(),
                    height = 0,
                    weight = 0
                )
            }
        }
    }

    override fun isFavorite(id: Int): Flow<Boolean> = dao.isFavorite(id)

    override suspend fun addFavorite(pokemon: Pokemon) {
        dao.addFavorite(
            FavoritePokemonEntity(
                id = pokemon.id,
                name = pokemon.name,
                imageUrl = pokemon.imageUrl
            )
        )
    }

    override suspend fun removeFavorite(id: Int) {
        dao.removeFavorite(id)
    }
}