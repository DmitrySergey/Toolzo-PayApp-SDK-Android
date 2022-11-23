package toolzo.payments.sdk.configuration

import java.util.concurrent.TimeUnit

class NetworkConfig(
    val serverUrl: String = "https://s3.di2p.ru/api/v1/",
    val connectionTimeOut: Long = TimeUnit.SECONDS.toMillis(90),
    val readWriteTimeOut: Long = TimeUnit.SECONDS.toMillis(90)
)