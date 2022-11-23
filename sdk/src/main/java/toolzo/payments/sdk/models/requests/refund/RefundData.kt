package toolzo.payments.sdk.models.requests.refund

import com.google.gson.annotations.SerializedName

data class RefundData(
    @SerializedName("OrderId")
    val orderId: String,
    @SerializedName("ParentOrderId")
    val parentOrderId: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("Currency")
    val currency: String
)

