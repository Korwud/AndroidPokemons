package ru.laert.pokemons.data.model

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val types: List<String>,
    val imageUrl: String
)
