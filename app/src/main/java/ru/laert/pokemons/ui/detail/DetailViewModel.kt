package ru.laert.pokemons.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.laert.pokemons.data.model.Pokemon
import ru.laert.pokemons.data.repository.PokemonRepository

class DetailViewModel(pokemonId : Int) : ViewModel() {
    private val repository = PokemonRepository()

    private val _pokemon = MutableStateFlow<Pokemon?>(null)
    val pokemon : StateFlow<Pokemon?> = _pokemon.asStateFlow()

    init {
        _pokemon.value = repository.getPokemonById(pokemonId)
    }
}