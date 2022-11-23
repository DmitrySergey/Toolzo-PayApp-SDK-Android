package toolzo.payments.sdk.api.utils

import com.google.gson.JsonObject
import toolzo.payments.sdk.models.requests.pay.TransactionDetails

internal interface RestConverter {

    fun createOrderJson(orderId: String): JsonObject

    fun createPayJson(transactionDetails: TransactionDetails, token: String): JsonObject

    fun <T : Any> toJsonObject(data: T): JsonObject
}