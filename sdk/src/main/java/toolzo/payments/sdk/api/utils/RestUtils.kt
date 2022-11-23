package toolzo.payments.sdk.api.utils

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import toolzo.payments.sdk.api.models.ServerError
import toolzo.payments.sdk.exceptions.RequestException
import toolzo.payments.sdk.exceptions.UnexpectedResponseException
import toolzo.payments.sdk.utils.Result
import retrofit2.Response
import toolzo.payments.sdk.api.models.ServerResponse
import java.net.UnknownHostException

suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Result<T> {
    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            return Result.Success(response.body())
        } else {
            response.errorBody()?.let { body ->
                val error = toServerError(body.string())
                error?.let {
                    return Result.Error(RequestException(it))
                }
            }
        }
        //{"Payload":"eyJQYXlsb2FkIjp7IkNvZGUiOiJJbnZhbGlkTWVzc2FnZVNpZ25hdHVyZSIsIk1lc3NhZ2UiOiIifSwiRXhlY3V0aW9uRHVyYXRpb24iOiIwMDowMDowMC4wMDU5OTE3IiwiVHlwZSI6IkVycm9yIiwiVGltZVN0YW1wIjoiMjAyMi0xMS0yMVQxMzoyNToxOS45OTI0Njk0WiJ9","Encrypted":false}


        return Result.Error(RequestException(response.code().toString(), response.message()))
    } catch (e: IllegalStateException) {
        return Result.Error(UnexpectedResponseException())
    } catch (e: UnknownHostException) {
        return Result.Error(e)
    } catch (e: Exception) {
        return Result.Error(e)
    }
}

val gson = Gson()

suspend inline fun <reified T : Any> safeApiResultWithPayload(call: suspend () -> Response<ServerResponse>): Result<T> {
    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            val payload = response.body()?.payload.orEmpty()
            val json = String(Base64.decode(payload, Base64.NO_WRAP))
            val jsonObject = JsonParser().parse(json).asJsonObject
            val result = jsonObject.get("Payload").asJsonObject
            return if (result.has("Code")){
                val error = gson.fromJson(result.toString(), ServerError::class.java)
                Result.Error(RequestException(error))
            } else {
                val success = gson.fromJson(result.toString(), T::class.java)
                Result.Success(success)
            }
        } else {
            response.errorBody()?.let { body ->
                val error = toServerError(body.string())
                error?.let {
                    return Result.Error(RequestException(it))
                }
            }
        }
        //{"Payload":"eyJQYXlsb2FkIjp7IkNvZGUiOiJJbnZhbGlkTWVzc2FnZVNpZ25hdHVyZSIsIk1lc3NhZ2UiOiIifSwiRXhlY3V0aW9uRHVyYXRpb24iOiIwMDowMDowMC4wMDU5OTE3IiwiVHlwZSI6IkVycm9yIiwiVGltZVN0YW1wIjoiMjAyMi0xMS0yMVQxMzoyNToxOS45OTI0Njk0WiJ9","Encrypted":false}


        return Result.Error(RequestException(response.code().toString(), response.message()))
    } catch (e: IllegalStateException) {
        return Result.Error(UnexpectedResponseException())
    } catch (e: UnknownHostException) {
        return Result.Error(e)
    } catch (e: Exception) {
        return Result.Error(e)
    }
}

fun toServerError(error: String): ServerError? {
    return try {
        GsonBuilder()
            .setLenient()
            .create()
            .fromJson(error, ServerError::class.java)
    } catch (e: JsonSyntaxException) {
        null
    }
}
