package toolzo.payments.sdk.configuration

class SdkConfiguration(
    val apiKey: String,
    val apiSecret: String,
    val tokenKey: TokenKey,
    val isDebug: Boolean = false,
    val networkConfig: NetworkConfig = NetworkConfig()
) {
}