package ru.laert.pokemons.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.laert.pokemons.data.local.AppDatabase
import ru.laert.pokemons.data.repository.FavoriteRepositoryImpl
import ru.laert.pokemons.data.repository.SettingsRepositoryImpl
import ru.laert.pokemons.domain.cache.BadgeCache
import ru.laert.pokemons.domain.repository.FavoriteRepository
import ru.laert.pokemons.domain.repository.SettingsRepository
import ru.laert.pokemons.ui.detail.DetailViewModel
import ru.laert.pokemons.ui.favorites.FavoritesViewModel
import ru.laert.pokemons.ui.list.ListViewModel
import ru.laert.pokemons.ui.settings.SettingsViewModel

val appModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }

    // Кэш бейджа (получит SettingsRepository через get())
    single { BadgeCache(settingsRepository = get()) }

    viewModel {
        ListViewModel(androidContext(), get(), get())
    }

    viewModel {
        SettingsViewModel(androidContext(), get(), get())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "pokemon_database"
        ).build()
    }

    single { get<AppDatabase>().favoriteDao() }

    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }

    viewModel { FavoritesViewModel(get()) }

    viewModel { (pokemonId: Int) ->
        DetailViewModel(pokemonId = pokemonId, favoriteRepository = get())
    }
}