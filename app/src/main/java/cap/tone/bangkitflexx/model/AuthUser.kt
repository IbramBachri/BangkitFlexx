package cap.tone.bangkitflexx.model

import cap.tone.bangkitflexx.database.FirebaseDatabase

class AuthUser(private val firebase:FirebaseDatabase) {
    fun signUp(email:String, pass:String) = firebase.signUp(email,pass)
    fun login(email:String, pass:String) = firebase.login(email,pass)
    fun logout() = firebase.logout()
    fun userNow() = firebase.userNow()
}