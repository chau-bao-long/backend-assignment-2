interface JwtService {
    fun generateLoginToken(name: String): String

    fun validateLoginToken(token: String?): Boolean
    
    fun getNameFromLoginToken(token: String): String?
}