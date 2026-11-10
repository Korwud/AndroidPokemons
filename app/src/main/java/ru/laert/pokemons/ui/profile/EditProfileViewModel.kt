package ru.laert.pokemons.ui.profile

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.laert.pokemons.data.notifications.AlarmScheduler
import ru.laert.pokemons.domain.model.UserProfile
import ru.laert.pokemons.domain.repository.ProfileRepository

class EditProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val context: Context
) : ViewModel() {

    private val alarmScheduler = AlarmScheduler(context)
    private val _profileState = MutableStateFlow(UserProfile())
    val profileState: StateFlow<UserProfile> = _profileState.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    sealed interface UiEvent {
        data object RequestExactAlarmPermission : UiEvent
    }

    init {
        viewModelScope.launch {
            profileRepository.profileFlow.collect { savedProfile ->
                _profileState.value = savedProfile
            }
        }
    }

    fun updateName(name: String) {
        _profileState.value = _profileState.value.copy(fullName = name)
    }

    fun updateRank(rank: String) {
        _profileState.value = _profileState.value.copy(rank = rank)
    }

    fun updateResumeUrl(url: String) {
        _profileState.value = _profileState.value.copy(resumeUrl = url)
    }

    fun updateAvatarUri(uri: String) {
        _profileState.value = _profileState.value.copy(avatarUri = uri)
    }

    fun updateTime(time: String) {
        _profileState.value = _profileState.value.copy(timeRCommandAttack = time)
    }

    fun saveProfile(onSaved: () -> Unit) {
        viewModelScope.launch {
            val currentProfile = _profileState.value

            profileRepository.saveProfile(currentProfile)

            if (currentProfile.timeRCommandAttack.isNotBlank()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    if (!alarmManager.canScheduleExactAlarms()) {
                        _uiEvents.send(UiEvent.RequestExactAlarmPermission)

                    } else {
                        scheduleAlarm(currentProfile)
                    }
                } else {
                    scheduleAlarm(currentProfile)
                }
            }

            onSaved()
        }
    }

    private fun scheduleAlarm(profile: UserProfile) {
        alarmScheduler.scheduleReminder(
            timeString = profile.timeRCommandAttack,
            userName = profile.fullName.ifBlank { "John Doe" }
        )
    }
}