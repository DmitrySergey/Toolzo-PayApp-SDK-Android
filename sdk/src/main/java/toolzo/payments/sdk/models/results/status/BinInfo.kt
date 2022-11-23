package toolzo.payments.sdk.models.results.status

import com.google.gson.annotations.SerializedName

class BinInfo(
    @SerializedName("Brand")
    val brand: String,
    @SerializedName("IssuingBank")
    val issuingBank: String,
    @SerializedName("CountryName")
    val countryName: String,
    @SerializedName("CardType")
    val cardType: String,
    @SerializedName("CardLevel")
    val cardLevel: String
)