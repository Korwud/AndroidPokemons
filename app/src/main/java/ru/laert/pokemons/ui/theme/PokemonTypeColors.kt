package ru.laert.pokemons.ui.theme

import androidx.compose.ui.graphics.Color

val TypeFire = Color(0xFFF08030)
val TypeWater = Color(0xFF6890F0)
val TypeGrass = Color(0xFF78C850)
val TypeElectric = Color(0xFFF8D030)
val TypeIce = Color(0xFF98D8D8)
val TypeFighting = Color(0xFFC03028)
val TypePoison = Color(0xFFA040A0)
val TypeGround = Color(0xFFE0C068)
val TypeFlying = Color(0xFFA890F0)
val TypePsychic = Color(0xFFF85888)
val TypeBug = Color(0xFFA8B820)
val TypeRock = Color(0xFFB8A038)
val TypeGhost = Color(0xFF705898)
val TypeDragon = Color(0xFF7038F8)
val TypeDark = Color(0xFF705848)
val TypeSteel = Color(0xFFB8B8D0)
val TypeFairy = Color(0xFFEE99AC)
val TypeNormal = Color(0xFFA8A878)

fun colorForType(type: String): Color = when (type.lowercase()) {
    "fire" -> TypeFire
    "water" -> TypeWater
    "grass" -> TypeGrass
    "electric" -> TypeElectric
    "ice" -> TypeIce
    "fighting" -> TypeFighting
    "poison" -> TypePoison
    "ground" -> TypeGround
    "flying" -> TypeFlying
    "psychic" -> TypePsychic
    "bug" -> TypeBug
    "rock" -> TypeRock
    "ghost" -> TypeGhost
    "dragon" -> TypeDragon
    "dark" -> TypeDark
    "steel" -> TypeSteel
    "fairy" -> TypeFairy
    else -> TypeNormal
}