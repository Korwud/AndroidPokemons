package ru.laert.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.laert.core.domain.model.UserProfile
import java.io.File

interface ProfileRepository {
    val profileFlow: Flow<UserProfile>

    suspend fun saveProfile(profile: UserProfile)
    suspend fun downloadResume(url: String): File
}