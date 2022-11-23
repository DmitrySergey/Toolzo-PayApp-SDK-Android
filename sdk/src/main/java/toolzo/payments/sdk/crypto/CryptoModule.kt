package toolzo.payments.sdk.crypto

import android.util.Base64
import org.bouncycastle.jce.provider.BouncyCastleProvider
import toolzo.payments.sdk.api.utils.JsonConverterImpl
import toolzo.payments.sdk.configuration.TokenKey
import toolzo.payments.sdk.models.requests.pay.CardDetails
import java.security.AlgorithmParameters
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.MGF1ParameterSpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource


internal object CryptoModule {

    private val jsonConverter = JsonConverterImpl()

    fun createCryptogram(cardDetails: CardDetails, tokenKey: TokenKey): String {
        return toBase64String(getEncodedToken(cardDetails, tokenKey) ?: byteArrayOf())
    }

    fun createSignature(payload: String, secret: String): String {
        val secretBytes = Base64.encode(secret.toByteArray(), Base64.NO_WRAP)
        val hmac = HMAC.hash("HmacSHA1", payload, secretBytes)
        val hmac2 = HMAC.hmacSha1(payload, secretBytes)
        return Base64.encodeToString(hmac2, Base64.NO_WRAP).removeSuffix("\n")
    }

    private fun getEncodedToken(cardDetails: CardDetails, tokenKey: TokenKey): ByteArray? {
        val json = jsonConverter.toJsonObject(cardDetails).toString()
        val algp = AlgorithmParameters.getInstance("OAEP", BouncyCastleProvider())
        val paramSpec: AlgorithmParameterSpec = OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT)
        algp.init(paramSpec)
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", BouncyCastleProvider())
        cipher.init(Cipher.ENCRYPT_MODE, tokenKey.toPublicKey(), algp)
        return cipher.doFinal(json.toByteArray())
    }

    fun toBase64String(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}