package ru.laert.pokemons.domain.usecase

import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.repository.PokemonRepository

class GetPokemonDetailsUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Pokemon {
        return repository.getPokemonById(id)
    }
}