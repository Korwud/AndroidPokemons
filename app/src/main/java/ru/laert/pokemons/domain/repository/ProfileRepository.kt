package ru.laert.pokemons.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.laert.pokemons.domain.model.UserProfile
import java.io.File

interface ProfileRepository {
    val profileFlow: Flow<UserProfile>

    suspend fun saveProfile(profile: UserProfile)
    suspend fun downloadResume(url: String): File
}