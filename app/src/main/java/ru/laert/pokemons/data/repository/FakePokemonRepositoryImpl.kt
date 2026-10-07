package ru.laert.pokemons.data.repository

import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.model.Ability
import ru.laert.pokemons.domain.repository.PokemonRepository

class FakePokemonRepositoryImpl: PokemonRepository {
    override suspend fun getPokemons(): List<Pokemon> = mockPokemons

    override suspend fun getPokemonById(id: Int): Pokemon =
        mockPokemons.find { it.id == id }
            ?: throw NoSuchElementException("Pokemon with id=$id not found")

    private val mockPokemons = listOf(
        Pokemon(
            id = 1,
            name = "Bulbasaur",
            height = 7,
            weight = 69,
            abilities = listOf(
                Ability("overgrow", false),
                Ability("chlorophyll", true)
            ),
            types = listOf("grass", "poison"),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
        ),
        Pokemon(
            id = 4,
            name = "Charmander",
            height = 6,
            weight = 85,
            abilities = listOf(
                Ability("blaze", false),
                Ability("solar-power", true)
            ),
            types = listOf("fire"),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"
        ),
        Pokemon(
            id = 7,
            name = "Squirtle",
            height = 5,
            weight = 90,
            abilities = listOf(
                Ability("torrent", false),
                Ability("rain-dish", true)
            ),
            types = listOf("water"),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"
        ),
        Pokemon(
            id = 10,
            name = "Caterpie",
            height = 3,
            weight = 29,
            abilities = listOf(
                Ability("shield-dust", false),
                Ability("run-away", true)
            ),
            types = listOf("bug"),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png"
        ),
        Pokemon(
            id = 14,
            name = "Kakuna",
            height = 6,
            weight = 100,
            abilities = listOf(
                Ability("shed-skin", false)
            ),
            types = listOf("bug", "poison"),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/14.png"
        )
    )
}