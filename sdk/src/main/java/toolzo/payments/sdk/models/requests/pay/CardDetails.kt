package toolzo.payments.sdk.models.requests.pay

import com.google.gson.annotations.SerializedName

data class CardDetails(
    @SerializedName("CardHolder")
    val cardholder: String,
    @SerializedName("CardNumber")
    val cardNumber: String,
    @SerializedName("Cvv")
    val cvv: String,
    @SerializedName("ExpirationMonth")
    val expirationMonth: String,
    @SerializedName("ExpirationYear")
    val expirationYear: String
)
