package toolzo.payments.sdk

import toolzo.payments.sdk.api.PaymentsRestClient
import toolzo.payments.sdk.api.utils.JsonConverterImpl
import toolzo.payments.sdk.configuration.SdkConfiguration
import toolzo.payments.sdk.crypto.CryptoModule
import toolzo.payments.sdk.models.requests.pay.CardDetails
import toolzo.payments.sdk.models.requests.pay.TransactionDetails
import toolzo.payments.sdk.models.requests.refund.RefundData
import toolzo.payments.sdk.models.results.pay.PaymentResult
import toolzo.payments.sdk.models.results.refund.RefundResult
import toolzo.payments.sdk.models.results.status.OrderDetails
import toolzo.payments.sdk.utils.Result

internal class PaymentSdkImpl(
    private val configuration: SdkConfiguration
) : ToolzoPaymentsSdk {

    private val restClient = PaymentsRestClient(configuration)
    private val jsonConverter = JsonConverterImpl()

    override suspend fun pay(cardDetails: CardDetails, transactionDetails: TransactionDetails): Result<PaymentResult> {
        val token = createCryptogram(cardDetails)
        val payload = jsonConverter.createPayJson(transactionDetails, token).toString()
        val signature = CryptoModule.createSignature(payload, configuration.apiSecret)
        return restClient.pay(
            jsonConverter.createPayment(payload, signature, configuration.apiKey)
        )
    }

    override suspend fun getOrderStatus(orderId: String): Result<OrderDetails> {
        return restClient.getOrderStatus(jsonConverter.createOrderJson(orderId))
    }

    override suspend fun refund(refundData: RefundData): Result<RefundResult> {
        return restClient.refund(jsonConverter.toJsonObject(refundData))
    }

    override fun createCryptogram(cardDetails: CardDetails): String {
        return CryptoModule.createCryptogram(cardDetails, configuration.tokenKey)
    }
}