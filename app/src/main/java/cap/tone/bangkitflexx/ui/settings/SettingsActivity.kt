package cap.tone.bangkitflexx.ui.settings

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.glide.GlideApp
import cap.tone.bangkitflexx.model.PreferencesModelFactory
import cap.tone.bangkitflexx.model.UserPreference
import cap.tone.bangkitflexx.ui.profile.EditProfileFragment
import cap.tone.bangkitflexx.util.FirestoreUtil
import cap.tone.bangkitflexx.util.StorageUtil
import kotlinx.android.synthetic.main.activity_settings.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {
    private val requestCodePermission = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        FirestoreUtil.getCurrentUser { user ->
            tv_profile_name.text = user.name
            tv_profile_email.text = user.email
            if (user.profilePicturePath != null)
                GlideApp.with(this)
                    .load(StorageUtil.pathToReference(user.profilePicturePath))
                    .placeholder(R.drawable.person)
                    .into(tv_profile_img)

        }

        val pref = UserPreference.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[SettingsViewModel::class.java]
        viewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch_theme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch_theme.isChecked = false
            }
        }

        checkRuntimePermissions()

        btn_edt_profile.setOnClickListener {
            replaceFragment(EditProfileFragment())
        }

        switch_theme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
        //logout.setOnClickListener{
        //    AuthUI.getInstance()
        //        .signOut(this@SettingsActivity.context!!)
        //        .addOnCompleteListener {
        //            startActivity(intentFor<SignInActivity>().newTask().clearTask())
        //        }
        //}

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_frame_layout, fragment)
            .commit()
    }

    private fun checkRuntimePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this@SettingsActivity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ),
                requestCodePermission
            )
        }
    }
}
