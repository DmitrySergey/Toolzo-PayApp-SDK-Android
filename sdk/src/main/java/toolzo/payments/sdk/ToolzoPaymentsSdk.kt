package toolzo.payments.sdk

import toolzo.payments.sdk.configuration.SdkConfiguration
import toolzo.payments.sdk.models.requests.pay.CardDetails
import toolzo.payments.sdk.models.requests.pay.TransactionDetails
import toolzo.payments.sdk.models.requests.refund.RefundData
import toolzo.payments.sdk.models.results.pay.PaymentResult
import toolzo.payments.sdk.models.results.refund.RefundResult
import toolzo.payments.sdk.models.results.status.OrderDetails
import toolzo.payments.sdk.utils.Result

interface ToolzoPaymentsSdk {

    fun createCryptogram(cardDetails: CardDetails): String

    suspend fun pay(cardDetails: CardDetails, transactionDetails: TransactionDetails): Result<PaymentResult>

    suspend fun getOrderStatus(orderId: String): Result<OrderDetails>

    suspend fun refund(refundData: RefundData): Result<RefundResult>

    companion object {

        fun getInstance(configuration: SdkConfiguration): ToolzoPaymentsSdk {
            return PaymentSdkImpl(configuration)
        }
    }
}