package ru.laert.pokemons.data.repository

import ru.laert.pokemons.data.mapper.extractId
import ru.laert.pokemons.data.mapper.toDomain
import ru.laert.pokemons.data.remote.NetworkModule
import ru.laert.pokemons.data.remote.PokemonApi
import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val api: PokemonApi = NetworkModule.pokemonApi
): PokemonRepository {
    override suspend fun getPokemons(): List<Pokemon> {
        val response = api.getPokemons(limit = 9999, offset = 0)

        return response.results.mapNotNull { item ->
            val id = item.extractId() ?: return@mapNotNull null
            Pokemon(
                id = id,
                name = item.name,
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                types = emptyList(),
                abilities = emptyList(),
                height = 0,
                weight = 0
            )
        }
    }

    override suspend fun getPokemonById(id: Int): Pokemon {
        return api.getPokemonById(id).toDomain()
    }
}