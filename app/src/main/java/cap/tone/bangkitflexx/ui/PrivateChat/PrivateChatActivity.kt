package cap.tone.bangkitflexx.ui.PrivateChat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cap.tone.bangkitflexx.Model.MessageModel
import cap.tone.bangkitflexx.databinding.ActivityPrivateChatBinding

class PrivateChatActivity : AppCompatActivity() {
    private lateinit var binding :ActivityPrivateChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivateChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listViewType = mutableListOf<Int>()
        listViewType.add(0)
        listViewType.add(1)
        listViewType.add(0)
        listViewType.add(2)
        listViewType.add(1)
        listViewType.add(2)
        listViewType.add(2)
        listViewType.add(1)
        val listChat = mutableListOf<MessageModel>()
        listChat.add(MessageModel(message = "", time = "", date = "Dec 30"))
        listChat.add(MessageModel(message = "Hello", time = "15:30", date = ""))
        listChat.add(MessageModel(message = "", time = "", date = "Dec 31"))
        listChat.add(MessageModel(message = "Hello, how are you today?", time = "23:15", date = ""))
        listChat.add(MessageModel(message = "I am fine, and you?", time = "23:16", date = ""))
        listChat.add(MessageModel(message = "I'm fine too", time = "23:16", date = ""))
        listChat.add(MessageModel(message = "Sorry i am late to answer your message because i have problem with my network yesterday", time = "23:16", date = ""))
        listChat.add(MessageModel(message = "Its ok, i just want to borrow your motorcycle, can i?", time = "23:17", date = ""))
        val adapterChat = AdapterPrivateChat(listViewType = listViewType, listChat = listChat)
        binding.apply {
            rvPrivateChat.layoutManager = LinearLayoutManager(this@PrivateChatActivity)
            rvPrivateChat.adapter = adapterChat
        }
    }
}