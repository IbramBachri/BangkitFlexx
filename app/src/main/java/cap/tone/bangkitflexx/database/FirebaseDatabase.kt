package cap.tone.bangkitflexx.database

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Completable

class FirebaseDatabase {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun signUp(email:String, pass:String) = Completable.create {
            task ->
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener {
                if(!task.isDisposed) {
                    if(it.isSuccessful) {
                        task.onComplete()
                    } else {
                        task.onError(it.exception!!)
                    }
                }
            }
    }

    fun login(email:String, pass:String) = Completable.create {
        task ->
        firebaseAuth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener {
                if(!task.isDisposed) {
                    if(it.isSuccessful) {
                        task.onComplete()
                    } else {
                        task.onError(it.exception!!)
                    }
                }
            }
    }

    fun logout() = firebaseAuth.signOut()
    fun userNow() = firebaseAuth.currentUser
}