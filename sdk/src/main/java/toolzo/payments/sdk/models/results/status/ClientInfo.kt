package toolzo.payments.sdk.models.results.status

import com.google.gson.annotations.SerializedName

class ClientInfo(
    @SerializedName("PhoneNumber")
    val phoneNumber: Int,
    @SerializedName("Email")
    val email: String
)