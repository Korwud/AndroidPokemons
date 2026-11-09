package ru.laert.pokemons.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.laert.pokemons.domain.model.PokemonFilter

interface SettingsRepository {
    val filterFlow: Flow<PokemonFilter>

    suspend fun saveFilter(filter: PokemonFilter)
}