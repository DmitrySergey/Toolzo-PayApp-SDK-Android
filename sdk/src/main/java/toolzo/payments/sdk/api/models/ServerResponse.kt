package toolzo.payments.sdk.api.models

import com.google.gson.annotations.SerializedName

class ServerResponse(
    @SerializedName("ApiKey")
    val apiKey: String?,
    @SerializedName("Payload")
    val payload: String,
    @SerializedName("Signature")
    val signature: String?,
    @SerializedName("Encrypted")
    val encrypted: Boolean?
) {
}