package toolzo.payments.sdk.models.results.pay

import com.google.gson.annotations.SerializedName

data class PaymentResult(
    @SerializedName("TransactionId")
    val transactionId: Int,
    @SerializedName("PaymentUrl")
    val paymentUrl: String
)