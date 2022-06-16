package cap.tone.bangkitflexx.ui.signIn

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.ui.drawer.NavDrawActivity
import cap.tone.bangkitflexx.util.FirestoreUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

@Suppress("DEPRECATION")
class SignInActivity : AppCompatActivity() {
    private val rcSignIn = 1

    private val signInProviders =
        listOf(
            AuthUI.IdpConfig.EmailBuilder()
            .setAllowNewAccounts(true)
            .setRequireName(true)
            .build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_signin.setOnClickListener {
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders)
                .setLogo(R.mipmap.ic_launcher_round)
                .build()
            startActivityForResult(intent, rcSignIn)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == rcSignIn) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val progressDialog = indeterminateProgressDialog("Setting up your account")
                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<NavDrawActivity>().newTask().clearTask())

                    //val registrationToken = FirebaseInstanceId.getInstance().token
                    //MyFirebaseInstanceIDService.addTokenToFirestore(registrationToken)

                    progressDialog.dismiss()
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) return

                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK ->
                        longSnackbar(signin_layout, "No network")
                    ErrorCodes.UNKNOWN_ERROR ->
                        longSnackbar(signin_layout, "Unknown error")
                }
            }
        }
    }
}