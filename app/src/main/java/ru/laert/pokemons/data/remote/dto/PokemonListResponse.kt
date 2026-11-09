package ru.laert.pokemons.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonListItemDto>
)

@Serializable
data class PokemonListItemDto(
    val name: String,
    val url: String
)