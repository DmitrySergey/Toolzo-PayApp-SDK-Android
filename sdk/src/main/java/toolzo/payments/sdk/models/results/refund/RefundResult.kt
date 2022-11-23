package toolzo.payments.sdk.models.results.refund

import com.google.gson.annotations.SerializedName

data class RefundResult(
    @SerializedName("TransactionId")
    val transactionId: Int,
    @SerializedName("OrderId")
    val orderId: String
)