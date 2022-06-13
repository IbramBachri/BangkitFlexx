package cap.tone.bangkitflexx.ui.chat.private

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cap.tone.bangkitflexx.Model.MessageModel
import cap.tone.bangkitflexx.R
import cap.tone.bangkitflexx.databinding.ActivityPrivateChatBinding
import java.text.SimpleDateFormat
import java.util.*

class PrivateChatActivity : AppCompatActivity() {
    private lateinit var binding :ActivityPrivateChatBinding
    private lateinit var listViewType: MutableList<Int>
    private lateinit var listChat: MutableList<MessageModel>
    private lateinit var adapterChat: AdapterPrivateChat

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivateChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edtChat.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val idTypeChat = binding.radioBtn.checkedRadioButtonId
                val typeChat = if (idTypeChat == R.id.self_chat) {
                    AdapterPrivateChat.VIEW_TYPE_SELF
                } else {
                    AdapterPrivateChat.VIEW_TYPE_OTHER
                }
                val message = binding.edtChat.text.toString().trim()
                if (message.isEmpty()) {
                    Toast.makeText(this@PrivateChatActivity, "Message is empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val date = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
                    val time = SimpleDateFormat("HH:mm", Locale.US).format(Date())
                    val chat = MessageModel(message = message, date = date, time = time)
                    listViewType.add(typeChat)
                    listChat.add(chat)
                    adapterChat.notifyDataSetChanged()
                }
            };true
        }
        setupAdapterRecyclerView()
    }


    private fun setupAdapterRecyclerView() {
        listViewType = mutableListOf()
        listChat = mutableListOf()
        adapterChat = AdapterPrivateChat(listViewType = listViewType, listChat = listChat)
        binding.apply {
            rvPrivateChat.layoutManager = LinearLayoutManager(this@PrivateChatActivity)
            rvPrivateChat.adapter = adapterChat
        }
    }
}