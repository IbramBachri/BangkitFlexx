package cap.tone.bangkitflexx.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.glide.GlideApp
import cap.tone.bangkitflexx.util.FirestoreUtil
import cap.tone.bangkitflexx.util.StorageUtil
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION", "DEPRECATION")
class EditProfileFragment : Fragment() {

    private val rcSelectImage = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        view.apply {
            add_new_image.setOnClickListener {
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/jpg", "image/gif"))
                }
                startActivityForResult(Intent.createChooser(intent, "Select Image"), rcSelectImage)
            }

            btn_save_profile.setOnClickListener {
                if (::selectedImageBytes.isInitialized)
                    StorageUtil.uploadProfilePhoto(selectedImageBytes) { imagePath ->
                        FirestoreUtil.updateCurrentUser(
                            edt_name.text.toString(),
                            edt_email.text.toString(),
                            edt_about.text.toString(),
                            edt_skill.text.toString(),
                            imagePath
                        )
                    }
                else
                    FirestoreUtil.updateCurrentUser(
                        edt_name.text.toString(),
                        edt_email.text.toString(),
                        edt_about.text.toString(),
                        edt_skill.text.toString(),
                        null
                    )
                toast("Saving")
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == rcSelectImage && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null) {

            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media
                .getBitmap(activity?.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                .load(selectedImageBytes)
                .into(tv_edt_profile_img)

            pictureJustChanged = true
        }
    }


    override fun onStart() {
        super.onStart()
        FirestoreUtil.getCurrentUser { user ->
            if (this@EditProfileFragment.isVisible) {
                edt_name.setText(user.name)
                edt_email.setText(user.email)
                edt_about.setText(user.about)
                edt_skill.setText(user.skill)

                if (!pictureJustChanged && user.profilePicturePath != null)
                    GlideApp.with(this)
                        .load(StorageUtil.pathToReference(user.profilePicturePath))
                        .placeholder(R.drawable.person)
                        .into(tv_edt_profile_img)
            }
        }
    }

}