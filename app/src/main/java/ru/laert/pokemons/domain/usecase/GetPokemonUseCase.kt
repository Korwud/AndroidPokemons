package ru.laert.pokemons.domain.usecase

import ru.laert.pokemons.domain.model.Pokemon
import ru.laert.pokemons.domain.repository.PokemonRepository

class GetPokemonUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): List<Pokemon> {
        return repository.getPokemons()
    }
}