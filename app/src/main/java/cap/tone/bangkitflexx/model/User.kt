package cap.tone.bangkitflexx.model

data class User(val name: String,
                val email: String,
                val about: String,
                val skill: String,
                val profilePicturePath: String?,
                val registrationTokens: MutableList<String>) {
    constructor(): this("", "", "", "", null, mutableListOf())
}
