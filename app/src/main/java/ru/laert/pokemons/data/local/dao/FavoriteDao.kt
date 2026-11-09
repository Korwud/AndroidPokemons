package ru.laert.pokemons.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.laert.pokemons.data.local.entity.FavoritePokemonEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_pokemons")
    fun getFavorites(): Flow<List<FavoritePokemonEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemons WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Query("SELECT * FROM favorite_pokemons WHERE id = :id")
    suspend fun getFavoriteById(id: Int): FavoritePokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(pokemon: FavoritePokemonEntity)

    @Query("DELETE FROM favorite_pokemons WHERE id = :id")
    suspend fun removeFavorite(id: Int)
}