package toolzo.payments.sdk.models.results.status

enum class State {
    New,
    GetAcs,
    InProcess,
    Canceled,
    Completed,
    PartialRefunded,
    FullyRefunded
}