package ru.laert.pokemons

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.laert.pokemons.di.appModule

class PokemonsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokemonsApplication)
            modules(appModule)
        }
    }
}