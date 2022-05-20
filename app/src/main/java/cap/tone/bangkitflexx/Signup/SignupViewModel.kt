package cap.tone.bangkitflexx.Signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cap.tone.bangkitflexx.Model.UserModel
import cap.tone.bangkitflexx.Model.UserPreference
import kotlinx.coroutines.launch

class SignupViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}