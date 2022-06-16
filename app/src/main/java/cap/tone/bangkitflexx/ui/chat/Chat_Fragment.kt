package cap.tone.bangkitflexx.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import cap.tone.bangkitflexx.R

class ChatFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCamera: Button = view.findViewById(R.id.send_camera)
        val btnGallery: Button = view.findViewById(R.id.send_gallery)
        val btnDocument: Button = view.findViewById(R.id.send_document)
        val btnContact: Button = view.findViewById(R.id.send_contact)

        btnCamera.setOnClickListener(this)
        btnGallery.setOnClickListener(this)
        btnDocument.setOnClickListener(this)
        btnContact.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.send_camera -> {

            }
            R.id.send_gallery -> {

            }
            R.id.send_document -> {

            }
            R.id.send_contact -> {

            }
        }
    }
}