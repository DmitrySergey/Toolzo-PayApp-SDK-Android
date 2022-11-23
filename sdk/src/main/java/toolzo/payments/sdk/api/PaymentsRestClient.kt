package toolzo.payments.sdk.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import toolzo.payments.sdk.api.utils.safeApiResult
import toolzo.payments.sdk.configuration.SdkConfiguration
import toolzo.payments.sdk.models.results.pay.PaymentResult
import toolzo.payments.sdk.utils.Result
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import toolzo.payments.sdk.api.utils.safeApiResultWithPayload
import toolzo.payments.sdk.models.results.refund.RefundResult
import toolzo.payments.sdk.models.results.status.OrderDetails
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

internal class PaymentsRestClient constructor(
    private val sdkConfiguration: SdkConfiguration
) : PaymentsRestApi {

    private val paymentsRestApi: PaymentsApiFunctions

    init {
        var httpClient = OkHttpClient.Builder()
        httpClient
            .connectTimeout(sdkConfiguration.networkConfig.connectionTimeOut, TimeUnit.MILLISECONDS)
            .readTimeout(sdkConfiguration.networkConfig.readWriteTimeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(sdkConfiguration.networkConfig.readWriteTimeOut, TimeUnit.MILLISECONDS)

        if (sdkConfiguration.isDebug) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient = httpClient.addInterceptor(interceptor)
        }
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(sdkConfiguration.networkConfig.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient.build())

        paymentsRestApi = retrofit.build().create(PaymentsApiFunctions::class.java)
    }

    private fun getBody(requestBody: RequestBody?): String {
        if (requestBody == null)
            return ""
        val buffer = Buffer()
        requestBody.writeTo(buffer)

        val contentType = requestBody.contentType()
        val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

        return buffer.readString(charset)
    }

    override suspend fun pay(data: JsonObject): Result<PaymentResult> = safeApiResultWithPayload {
        paymentsRestApi.pay(data).await()
    }

    override suspend fun getOrderStatus(data: JsonObject): Result<OrderDetails> = safeApiResultWithPayload {
        paymentsRestApi.getOrderStatus(data).await()
    }

    override suspend fun refund(data: JsonObject): Result<RefundResult> = safeApiResultWithPayload {
        paymentsRestApi.refund(data).await()
    }
}
