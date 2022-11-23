package toolzo.payments.sdk.api.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import toolzo.payments.sdk.crypto.CryptoModule.toBase64String
import toolzo.payments.sdk.models.requests.pay.TransactionDetails
import java.text.SimpleDateFormat
import java.util.*

internal class JsonConverterImpl : RestConverter {

    private val gson = Gson()
    private val jsonParser = JsonParser()

    override fun createOrderJson(orderId: String): JsonObject {
        return JsonObject().apply {
            addProperty("OrderId", orderId)
        }
    }

    private fun createPayPayload(transactionDetails: TransactionDetails, cardToken: String): JsonObject {
        return toJsonObject(transactionDetails).apply {
            addProperty("RebillFlag", false)
            addProperty("СardInfo", cardToken)
        }
    }

    private fun createPayRequest(payload: JsonObject): JsonObject {
        return JsonObject().apply {
            addProperty("Type", "Pay")
            addProperty("ApiVersion", "1")
            addProperty("Priority", "Normal")
            add("Payload", payload)
        }
    }

    override fun createPayJson(transactionDetails: TransactionDetails, cardToken: String): JsonObject {
        val payload = JsonObject()
        payload.addProperty("Type", "RequestWithCryptogram")
        payload.addProperty("CorrelationId", UUID.randomUUID().toString())
        payload.addProperty("TTL", "00:00:45")
        payload.addProperty("TimeStamp", SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSZ").format(Date()).removeSuffix("00")+":00") //2022-11-21T17:59:42+03:00
        payload.add("Payload", createPayRequest(createPayPayload(transactionDetails, cardToken)))
        return payload
    }

    override fun <T : Any> toJsonObject(data: T): JsonObject {
        return jsonParser.parse(gson.toJson(data)).asJsonObject
    }

    fun createPayment(payload: String, signature: String, apiKey: String): JsonObject {
        return JsonObject().apply {
            addProperty("ApiKey", apiKey)
            addProperty("Payload", toBase64String(payload.toByteArray()))
            addProperty("Signature", signature)
        }
    }
}