package app.rickandmorty.core.resourcestate

public sealed class ResourceState<out T>(
    public val complete: Boolean,
    public val shouldLoad: Boolean,
    private val value: T?,
) {
    public open operator fun invoke(): T? = value
}

public object Uninitialized :
    ResourceState<Nothing>(complete = false, shouldLoad = true, value = null),
    Incomplete

public data class Loading<out T>(private val value: T? = null) :
    ResourceState<T>(complete = false, shouldLoad = false, value = value),
    Incomplete

public data class Success<out T>(private val value: T) :
    ResourceState<T>(complete = true, shouldLoad = false, value = value) {
    override operator fun invoke(): T = value
}

public data class Fail<out T>(val error: Throwable, private val value: T? = null) :
    ResourceState<T>(complete = true, shouldLoad = true, value = value) {
    override fun equals(other: Any?): Boolean {
        if (other !is Fail<*>) {
            return false
        }

        val otherError = other.error
        return error::class == otherError::class &&
            error.message == otherError.message &&
            error.stackTrace.firstOrNull() == otherError.stackTrace.firstOrNull()
    }

    override fun hashCode(): Int = arrayOf(
        error::class,
        error.message,
        error.stackTrace.firstOrNull(),
    ).contentHashCode()
}

public interface Incomplete
