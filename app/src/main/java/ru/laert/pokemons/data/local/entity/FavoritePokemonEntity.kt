package ru.laert.pokemons.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemons")
data class FavoritePokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val typesString: String,
    val abilitiesString: String,
    val height: Int,
    val weight: Int
)