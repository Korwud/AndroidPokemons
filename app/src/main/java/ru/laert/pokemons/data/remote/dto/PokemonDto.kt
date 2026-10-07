package ru.laert.pokemons.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlotDto>,
    val abilities: List<AbilitySlotDto>,
    val sprites: SpritesDto
)

@Serializable
data class TypeSlotDto(
    val slot: Int,
    val type: TypeDto
)

@Serializable
data class TypeDto(
    val name: String,
    val url: String
)

@Serializable
data class AbilitySlotDto(
    val ability: AbilityDto,
    @SerialName("is_hidden") val isHidden: Boolean,
    val slot: Int
)

@Serializable
data class AbilityDto(
    val name: String,
    val url: String
)

@Serializable
data class SpritesDto(
    @SerialName("front_default") val frontDefault: String?
)