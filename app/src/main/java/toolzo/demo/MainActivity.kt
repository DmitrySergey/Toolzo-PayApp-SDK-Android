package toolzo.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import toolzo.payments.sdk.ToolzoPaymentsSdk
import toolzo.payments.sdk.configuration.SdkConfiguration
import toolzo.payments.sdk.configuration.TokenKey
import toolzo.payments.sdk.models.requests.pay.CardDetails
import toolzo.payments.sdk.models.requests.pay.TransactionDetails
import java.util.UUID

class MainActivity : AppCompatActivity() {

    lateinit var sdk: ToolzoPaymentsSdk

    private val handler = CoroutineExceptionHandler { context, exception ->
        Toast.makeText(application, "Caught $exception", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sdk = ToolzoPaymentsSdk.getInstance(
            SdkConfiguration(
                "9094c882fe7340779c1ce2fb6cd4ae9a",
                "D8MeWAqPxt78dDzfeK5FYmLw0BQ/uIM4Lvad3pmuvvJOuDxtgsFbcoRceIs4ICDojVmPyL7Gkjaa0qmzFTpKaQ==",
                TokenKey(
                    "3teFkrq3dy3cJI60bXifg83ArO_66iXBTitV0_i8SPkeZtyNZZraLqTZiiP1caBmPYsSm8OKKfLIFqmK941zySOc9YM6ViqiJ-0B_fxJQBX34-dYm7JFLjOv2TGiPW-gA9SE6qOmXdGuTNSiJxugNRtM7yCWuMTEQnAhfWoz5jpnUYWLfAPDeny0KOozFqJy1NpyWiPru0VeR12QBdHhCHQDFcV9QvbZZtR3yaSIAtPVn1mWgIMv9d0MIj9MWOPAT9htceSJznogw8bzIIYpK5wCyIDUrseSkFiPqXkhs9FCfrQ3IOEHBNKvRPztGoxBXqOcBLE0CAnFR3sqy_ToaCgF5wSNzU9D9eJgCQKaDpW9Dmi5MZrbUxAO-64oXlt8ojg0d3S1MVFyfGi-A9THylfspJGagnsYXo-G55HYQXCdvlTgFa7l7yu7tr5R2aNCh1DfuVmNSNsTL1tQfLlWHAyPNbc7d82neoRIRdKIaVkozRedLVG-63tSdbBuFo3kNjIgfob9GMv0KewXgQLaC2e4tCQytqFyO5g0bSb_wzfQUVRdbBbGTeILSG5YrI9ubd4oqil9MA6__Xyb6XCAjLFUb9gf92xZqGSxbXeyg6Hd-saId-pCA0MTIdYJp-wMoBpU05egEAOp8V5JefNWHv1OwHNLYiXy4Bj6buPhcIE",
                    "AQAB"
                ),
                true
            )
        )

//        GlobalScope.launch(handler) {
//            val orderId = UUID.randomUUID().toString()
//            testPay(orderId)
//            testGetOrderStatus(orderId)
//            testRefund(transactionId)
//        }

        val crypto = sdk.createCryptogram(
            cardDetails = CardDetails(
                cardNumber = "4111111111111111",
                cardholder = "JAMES BOND",
                expirationYear = "28",
                expirationMonth = "12",
                cvv = "123"
            )
        )

    }

    suspend fun testPay(orderId: String) {
        sdk.pay(
            cardDetails = CardDetails(
                cardNumber = "4111111111111111",
                cardholder = "JAMES BOND",
                expirationYear = "28",
                expirationMonth = "12",
                cvv = "123"
            ),
            transactionDetails = TransactionDetails(
                orderId = orderId,
                amount = 100,
                returnUrl = "https://yandex.ru",
                description = "CREDIT",
                currency = "USD",
            )
        ).proceedResult(
            success = {
                println("OkHttp Result $it")
            },
            error = {
                it.printStackTrace()
            }
        )
    }

    suspend fun testGetOrderStatus(orderId: String) {
        sdk.getOrderStatus(orderId).proceedResult(
            success = {
                println("Result $it")
            },
            error = {
                it.printStackTrace()
            }
        )
    }
//
//    suspend fun testRefund(transactionId: String) {
//        sdk.refund(
//            refundData = RefundData(
//                transactionId = transactionId,
//                amount = "100",
//                currency = "RUB"
//            )
//        ).proceedResult(
//            success = {
//                println("Result $it")
//            },
//            error = {
//                it.printStackTrace()
//            }
//        )
//    }
}