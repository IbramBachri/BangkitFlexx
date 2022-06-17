package cap.tone.bangkitflexx.Model

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}