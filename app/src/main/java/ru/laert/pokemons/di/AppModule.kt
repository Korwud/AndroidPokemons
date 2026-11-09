package ru.laert.pokemons.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.laert.pokemons.data.local.AppDatabase
import ru.laert.pokemons.data.remote.NetworkModule
import ru.laert.pokemons.data.remote.PokemonApi
import ru.laert.pokemons.data.repository.FavoriteRepositoryImpl
import ru.laert.pokemons.data.repository.PokemonRepositoryImpl
import ru.laert.pokemons.data.repository.ProfileRepositoryImpl
import ru.laert.pokemons.data.repository.SettingsRepositoryImpl
import ru.laert.pokemons.domain.cache.BadgeCache
import ru.laert.pokemons.domain.repository.FavoriteRepository
import ru.laert.pokemons.domain.repository.PokemonRepository
import ru.laert.pokemons.domain.repository.ProfileRepository
import ru.laert.pokemons.domain.repository.SettingsRepository
import ru.laert.pokemons.domain.usecase.GetPokemonDetailsUseCase
import ru.laert.pokemons.domain.usecase.GetPokemonUseCase
import ru.laert.pokemons.ui.detail.DetailViewModel
import ru.laert.pokemons.ui.favorites.FavoritesViewModel
import ru.laert.pokemons.ui.list.ListViewModel
import ru.laert.pokemons.ui.profile.EditProfileViewModel
import ru.laert.pokemons.ui.profile.ProfileViewModel
import ru.laert.pokemons.ui.settings.SettingsViewModel

val appModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }
    single { BadgeCache(settingsRepository = get()) }

    single<PokemonApi> { NetworkModule.pokemonApi }

    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
    single { GetPokemonUseCase(get()) }
    single { GetPokemonDetailsUseCase(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "pokemon_database"
        ).build()
    }
    single { get<AppDatabase>().favoriteDao() }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }

    viewModel {
        ListViewModel(
            settingsRepository = get(),
            getPokemonUseCase = get(),
            pokemonRepository = get(),
            badgeCache = get()
        )
    }

    viewModel {
        SettingsViewModel(get(), get())
    }

    viewModel { FavoritesViewModel(get()) }

    viewModel { (pokemonId: Int) ->
        DetailViewModel(
            pokemonId = pokemonId,
            getPokemonDetailsUseCase = get(),
            favoriteRepository = get()
        )
    }

    single<ProfileRepository> { ProfileRepositoryImpl(androidContext()) }

    viewModel { ProfileViewModel(get(), androidContext()) }

    viewModel { EditProfileViewModel(get()) }
}