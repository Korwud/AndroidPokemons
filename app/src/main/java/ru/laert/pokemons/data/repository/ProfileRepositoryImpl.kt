package ru.laert.pokemons.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.laert.pokemons.data.remote.NetworkModule
import ru.laert.pokemons.domain.model.UserProfile
import ru.laert.pokemons.domain.repository.ProfileRepository
import java.io.File
import okhttp3.Request

private val Context.profileDataStore by preferencesDataStore(name = "user_profile")

class ProfileRepositoryImpl(
    private val context: Context
) : ProfileRepository {

    private object Keys {
        val FULL_NAME = stringPreferencesKey("full_name")
        val AVATAR_URI = stringPreferencesKey("avatar_uri")
        val RESUME_URL = stringPreferencesKey("resume_url")
        val RANK = stringPreferencesKey("rank")
        val TIME_R_COMMAND_ATTACK = stringPreferencesKey("time_r_command_attack")
    }

    override val profileFlow: Flow<UserProfile> = context.profileDataStore.data.map { prefs ->
        UserProfile(
            fullName = prefs[Keys.FULL_NAME] ?: "",
            avatarUri = prefs[Keys.AVATAR_URI] ?: "",
            resumeUrl = prefs[Keys.RESUME_URL] ?: "",
            rank = prefs[Keys.RANK] ?: "",
            timeRCommandAttack = prefs[Keys.TIME_R_COMMAND_ATTACK] ?: ""
        )
    }

    override suspend fun saveProfile(profile: UserProfile) {
        context.profileDataStore.edit { prefs ->
            prefs[Keys.FULL_NAME] = profile.fullName
            prefs[Keys.AVATAR_URI] = profile.avatarUri
            prefs[Keys.RESUME_URL] = profile.resumeUrl
            prefs[Keys.RANK] = profile.rank
            prefs[Keys.TIME_R_COMMAND_ATTACK] = profile.timeRCommandAttack
        }
    }

    override suspend fun downloadResume(url: String): File = withContext(Dispatchers.IO) {
        val client = NetworkModule.httpClient
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Ошибка скачивания: ${response.code}")

            val body = response.body ?: throw Exception("Пустой ответ")

            val file = File(context.cacheDir, "resume.pdf")
            file.outputStream().use { output ->
                body.byteStream().copyTo(output)
            }

            file
        }
    }
}