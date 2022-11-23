package toolzo.payments.sdk.models.results.status

import com.google.gson.annotations.SerializedName

class CardInfo(
    @SerializedName("CardNumber")
    val cardNumber: String,
    @SerializedName("CardHolder")
    val cardHolder: String,
    @SerializedName("BindingId")
    val bindingId: Int
) {
}