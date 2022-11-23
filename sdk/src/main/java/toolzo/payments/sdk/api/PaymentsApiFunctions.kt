package toolzo.payments.sdk.api

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import toolzo.payments.sdk.models.results.pay.PaymentResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import toolzo.payments.sdk.api.models.ServerResponse
import toolzo.payments.sdk.models.results.refund.RefundResult
import toolzo.payments.sdk.models.results.status.OrderDetails

internal interface PaymentsApiFunctions {

    @POST("payment")
    fun pay(@Body data: JsonObject): Deferred<Response<ServerResponse>>

    @POST("state")
    fun getOrderStatus(@Body data: JsonObject): Deferred<Response<ServerResponse>>

    @POST("refund")
    fun refund(@Body data: JsonObject): Deferred<Response<ServerResponse>>
}