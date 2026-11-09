package ru.laert.pokemons.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.laert.pokemons.data.local.dao.FavoriteDao
import ru.laert.pokemons.data.local.entity.FavoritePokemonEntity

@Database(entities = [FavoritePokemonEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}