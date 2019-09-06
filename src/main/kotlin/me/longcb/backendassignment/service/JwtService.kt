interface JwtService {
    fun generateLoginToken(userId: Long?): String

    fun validateLoginToken(token: String?): Boolean
    
    fun getNameFromLoginToken(token: String): String?
}