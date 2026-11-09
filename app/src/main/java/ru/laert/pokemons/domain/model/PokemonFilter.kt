package ru.laert.pokemons.domain.model

data class PokemonFilter(
    val types: Set<String> = emptySet()
) {
    val isDefault: Boolean
        get() = types.isEmpty()
}