package toolzo.payments.sdk.models.results.status

import com.google.gson.annotations.SerializedName

class OrderDetails(
    @SerializedName("ReferenceCode")
    val referenceCode: String,
    @SerializedName("Order")
    val order: Order
)