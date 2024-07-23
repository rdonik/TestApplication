package test.application.grafic.support.nullability

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}