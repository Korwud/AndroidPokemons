package ru.laert.pokemons.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TypeDetailDto(
    val id: Int,
    val name: String,
    val pokemon: List<TypePokemonSlotDto>
)

@Serializable
data class TypePokemonSlotDto(
    val pokemon: TypePokemonDto,
    val slot: Int
)

@Serializable
data class TypePokemonDto(
    val name: String,
    val url: String
)