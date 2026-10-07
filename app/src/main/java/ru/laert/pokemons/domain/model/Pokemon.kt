package ru.laert.pokemons.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val types: List<String>,
    val imageUrl: String
)
