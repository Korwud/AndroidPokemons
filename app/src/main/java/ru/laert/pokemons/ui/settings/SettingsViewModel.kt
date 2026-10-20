package ru.laert.pokemons.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.laert.pokemons.data.repository.SettingsRepositoryImpl
import ru.laert.pokemons.domain.cache.BadgeCache
import ru.laert.pokemons.domain.model.PokemonFilter
import ru.laert.pokemons.domain.repository.SettingsRepository

class SettingsViewModel(
    context: Context,
    private val repository: SettingsRepository,
    private val badgeCache: BadgeCache
) : ViewModel() {

    private val _filter = MutableStateFlow(PokemonFilter())
    val filter: StateFlow<PokemonFilter> = _filter.asStateFlow()

    init {
        viewModelScope.launch {
            repository.filterFlow.collect { savedFilter ->
                _filter.value = savedFilter
            }
        }
    }

    fun toggleType(type: String) {
        val currentTypes = _filter.value.types.toMutableSet()

        if (currentTypes.contains(type)) {
            currentTypes.remove(type)
        }
        else {
            currentTypes.add(type)
        }

        _filter.value = _filter.value.copy(types = currentTypes)
    }

    fun save(onSaved: () -> Unit) {
        viewModelScope.launch {
            repository.saveFilter(_filter.value)
            badgeCache.updateBadgeState(!_filter.value.isDefault)
            onSaved()
        }
    }
}