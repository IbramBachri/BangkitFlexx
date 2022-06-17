package cap.tone.bangkitflexx.recyclerView.item

import cap.tone.bangkitflexx.Model.Message
import cap.tone.bangkitflexx.Model.MessageType
import java.util.*

data class ImageMessage(val imagePath: String,
                        override val time: Date,
                        override val senderId: String,
                        override val type: String = MessageType.IMAGE)
    : Message {
    constructor() : this("", Date(0), "", "")
}