package toolzo.payments.sdk.exceptions

import toolzo.payments.sdk.api.models.ServerError

class RequestException(val code: String, message: String) : Exception(message) {

    constructor(serverError: ServerError) : this(serverError.code, serverError.message)
}