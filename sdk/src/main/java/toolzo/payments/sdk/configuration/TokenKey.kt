package toolzo.payments.sdk.configuration

import com.nimbusds.jose.util.Base64URL
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec

class TokenKey(
    private val n: String,
    private val e: String
) {
    fun toPublicKey(): PublicKey {
        val modulus = BigInteger(1, Base64URL(n).decode())
        val exponent = BigInteger(1, Base64URL(e).decode())
        return KeyFactory.getInstance("RSA").generatePublic(RSAPublicKeySpec(modulus, exponent))
    }
}