package ru.laert.pokemons.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ListRoute

@Serializable
data class DetailRoute(val pokemonId: Int)

@Serializable
data object ProfileRoute

@Serializable
data object SettingsRoute