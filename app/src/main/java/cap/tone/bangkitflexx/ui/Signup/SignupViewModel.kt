package cap.tone.bangkitflexx.ui.Signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cap.tone.bangkitflexx.Model.UserModel
import cap.tone.bangkitflexx.Model.UserPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class SignupViewModel(private val pref: UserPreference) : ViewModel() {

//    private var firebaseAuth:FirebaseAuth
//    private var firebaseDatabase:FirebaseDatabase
//    private var namedb:DatabaseReference
//
//    init {
//        firebaseAuth = FirebaseAuth.getInstance()
//        firebaseDatabase = FirebaseDatabase.getInstance()
//        namedb = firebaseDatabase.getReference("akun")
//    }
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

//    fun signUp(name:String, email:String, password:String) {
//        firebaseAuth.createUserWithEmailAndPassword(email,password)
//            .addOnCompleteListener {
//                if(it.isSuccessful) {
//
//                }
//            }
//    }
}