package toolzo.payments.sdk.models.requests.pay

import com.google.gson.annotations.SerializedName

data class TransactionDetails(
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("OrderId")
    val orderId: String,
    @SerializedName("ReturnUrl")
    val returnUrl: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Currency")
    val currency: String
)