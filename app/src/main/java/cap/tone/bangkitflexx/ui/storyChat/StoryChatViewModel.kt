package cap.tone.bangkitflexx.ui.storyChat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cap.tone.bangkitflexx.Model.UserModel
import cap.tone.bangkitflexx.Model.UserPreference
import kotlinx.coroutines.launch

class StoryChatViewModel (private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}