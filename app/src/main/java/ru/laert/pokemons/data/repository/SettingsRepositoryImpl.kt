package ru.laert.pokemons.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.laert.pokemons.domain.model.PokemonFilter
import ru.laert.pokemons.domain.repository.SettingsRepository

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepositoryImpl(
    private val context: Context
) : SettingsRepository {

    private object KEYS {
        val TYPES = stringSetPreferencesKey("types")
    }

    override val filterFlow: Flow<PokemonFilter> = context.dataStore.data.map { preferences ->
        PokemonFilter(
            types = preferences[KEYS.TYPES] ?: emptySet()
        )
    }

    override suspend fun saveFilter(filter: PokemonFilter) {
        context.dataStore.edit { preferences ->
            preferences[KEYS.TYPES] = filter.types
        }
    }
}