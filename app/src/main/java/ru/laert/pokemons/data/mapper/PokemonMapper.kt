package ru.laert.pokemons.data.mapper

import ru.laert.pokemons.data.remote.dto.AbilitySlotDto
import ru.laert.pokemons.data.remote.dto.PokemonDetailDto
import ru.laert.pokemons.data.remote.dto.PokemonListItemDto
import ru.laert.pokemons.domain.model.Ability
import ru.laert.pokemons.domain.model.Pokemon

fun PokemonDetailDto.toDomain(): Pokemon = Pokemon(
    id = id,
    name = name,
    imageUrl = sprites.frontDefault
        ?: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
    height = height,
    weight = weight,
    types = types.map { it.type.name },
    abilities = abilities.map { it.toDomain() }
)

fun AbilitySlotDto.toDomain(): Ability = Ability(
    name = ability.name,
    isHidden = isHidden
)

fun PokemonListItemDto.extractId(): Int? {
    return url.trimEnd('/').substringAfterLast('/').toIntOrNull()
}