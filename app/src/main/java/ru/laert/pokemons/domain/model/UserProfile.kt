package ru.laert.pokemons.domain.model

data class UserProfile (
    val fullName: String = "",
    val avatarUri: String = "",
    val resumeUrl: String = "",
    val rank: String = "",
    val timeRCommandAttack: String = ""
)