package cap.tone.bangkitflexx.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cap.tone.bangkitflexx.model.UserPreference

class SplashViewModel(private val pref: UserPreference): ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}