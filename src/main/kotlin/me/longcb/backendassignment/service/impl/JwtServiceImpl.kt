package me.longcb.backendassignment.service.impl

import JwtService
import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.DirectEncrypter
import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.JWEDecryptionKeySelector
import com.nimbusds.jose.proc.SimpleSecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.util.*

@Service
@Scope("prototype")
class JwtServiceImpl : JwtService {

    @Value("\${spring.jwt.expiration-time}")
    private var expirationTime: Int? = null

    @Value("\${spring.jwt.secret-key}")
    private var secretKey: String? = null

    override fun generateLoginToken(name: String): String {
        val claims = JWTClaimsSet.Builder()
                .claim(NAME, name)
                .expirationTime(generateExpirationDate())
                .build()
        val payload = Payload(claims.toJSONObject())
        val header = JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
        val signer = DirectEncrypter(generateSharedSecret())
        val jweObject = JWEObject(header, payload)
        jweObject.encrypt(signer)
        return jweObject.serialize()
    }

    override fun validateLoginToken(token: String?): Boolean {
        if (token.isNullOrBlank()) return false
        val name = getNameFromLoginToken(token)
        return name.isNullOrBlank() && !isTokenExpired(token)
    }

    override fun getNameFromLoginToken(token: String): String? {
        val jwtProcessor = configJWTProcessor()
        val claims = jwtProcessor.process(token, null)
        return claims.getClaim(NAME) as String?
    }

    private fun configJWTProcessor(): ConfigurableJWTProcessor<SimpleSecurityContext> {
        val jwtProcessor = DefaultJWTProcessor<SimpleSecurityContext>()
        val jweKeySource = ImmutableSecret<SimpleSecurityContext>(generateSharedSecret())
        val jweKeySelector = JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource)
        jwtProcessor.jweKeySelector = jweKeySelector
        return jwtProcessor
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    private fun getExpirationDateFromToken(token: String): Date {
        val jwtProcessor = configJWTProcessor()
        val claims = jwtProcessor.process(token, null)
        return claims.expirationTime
    }

    private fun generateSharedSecret() = secretKey!!.toByteArray()

    private fun generateExpirationDate() = Date(System.currentTimeMillis() + expirationTime!!)

    companion object {
        const val NAME = "userName"
    }
}