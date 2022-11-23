package toolzo.payments.sdk.api

import com.google.gson.JsonObject
import toolzo.payments.sdk.models.results.pay.PaymentResult
import toolzo.payments.sdk.models.results.refund.RefundResult
import toolzo.payments.sdk.models.results.status.OrderDetails
import toolzo.payments.sdk.utils.Result

internal interface PaymentsRestApi {

    suspend fun pay(data: JsonObject): Result<PaymentResult>

    suspend fun getOrderStatus(data: JsonObject): Result<OrderDetails>

    suspend fun refund(data: JsonObject): Result<RefundResult>
}