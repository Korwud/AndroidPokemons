package ru.laert.pokemons.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.laert.pokemons.data.local.dao.FavoriteDao
import ru.laert.pokemons.data.local.entity.FavoritePokemonEntity
import ru.laert.pokemons.domain.model.Ability
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val dao: FavoriteDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<Pokemon>> {
        return dao.getFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isFavorite(id: Int): Flow<Boolean> = dao.isFavorite(id)

    override suspend fun addFavorite(pokemon: Pokemon) {
        dao.addFavorite(pokemon.toEntity())
    }

    override suspend fun removeFavorite(id: Int) {
        dao.removeFavorite(id)
    }
    override suspend fun getFavoriteById(id: Int): Pokemon? {
        return dao.getFavoriteById(id)?.toDomain()
    }


    private fun Pokemon.toEntity(): FavoritePokemonEntity {
        val typesStr = types.joinToString(",")

        val abilitiesStr = abilities.joinToString(",") { "${it.name}:${it.isHidden}" }

        return FavoritePokemonEntity(
            id = id,
            name = name,
            imageUrl = imageUrl,
            typesString = typesStr,
            abilitiesString = abilitiesStr,
            height = height,
            weight = weight
        )
    }

    private fun FavoritePokemonEntity.toDomain(): Pokemon {
        val typesList = if (typesString.isBlank()) emptyList() else typesString.split(",")

        val abilitiesList = if (abilitiesString.isBlank()) emptyList() else {
            abilitiesString.split(",").map { str ->
                val parts = str.split(":")
                Ability(
                    name = parts[0],
                    isHidden = parts.getOrNull(1)?.toBoolean() ?: false
                )
            }
        }

        return Pokemon(
            id = id,
            name = name,
            imageUrl = imageUrl,
            types = typesList,
            abilities = abilitiesList,
            height = height,
            weight = weight
        )
    }
}