package cap.tone.bangkitflexx.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.ui.profile.EditProfileFragment
import cap.tone.bangkitflexx.util.FirestoreUtil
import cap.tone.bangkitflexx.util.StorageUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        btn_edt_profile.setOnClickListener {
            replaceFragment(EditProfileFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        FirestoreUtil.getCurrentUser { user ->
            tv_profile_email.text = user.email
            Glide.with(this)
                .load(user.profilePicturePath?.let { StorageUtil.pathToReference(it) })
                .placeholder(R.drawable.person)
                .into(tv_profile_img)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_frame_layout, fragment)
            .commit()
    }
}
