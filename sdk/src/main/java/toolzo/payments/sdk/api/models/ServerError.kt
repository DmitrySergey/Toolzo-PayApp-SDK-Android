package toolzo.payments.sdk.api.models

import com.google.gson.annotations.SerializedName

class ServerError(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Message")
    val message: String
)