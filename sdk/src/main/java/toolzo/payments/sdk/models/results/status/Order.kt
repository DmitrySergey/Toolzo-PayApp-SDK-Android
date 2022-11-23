package toolzo.payments.sdk.models.results.status

import com.google.gson.annotations.SerializedName

class Order(
    @SerializedName("TransactionId")
    val transactionId: Int,
    @SerializedName("OrderId")
    val orderId: String,
    @SerializedName("MercId")
    val mercId: Int,
    @SerializedName("DateTime")
    val dateTime: String,
    @SerializedName("ExecDateTime")
    val execDateTime: String,
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("Currency")
    val currency: String,
    @SerializedName("State")
    val state: State,
    @SerializedName("StateDescription")
    val stateDescription: String,
    @SerializedName("CardInfo")
    val cardInfo: CardInfo,
    @SerializedName("ClientInfo")
    val clientInfo: ClientInfo,
    @SerializedName("BinInfo")
    val binInfo: BinInfo
)