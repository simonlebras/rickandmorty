package app.rickandmorty.core.logging

public interface Logger {
    /** Set a one-time tag for use on the next logging call.  */
    public fun tag(tag: String): Logger

    /** Log a verbose message with optional format args.  */
    public fun v(message: String, vararg args: Any?)

    /** Log a verbose exception and a message with optional format args.  */
    public fun v(t: Throwable, message: String, vararg args: Any?)

    /** Log a verbose exception.  */
    public fun v(t: Throwable)

    /** Log a debug message with optional format args.  */
    public fun d(message: String, vararg args: Any?)

    /** Log a debug exception and a message with optional format args.  */
    public fun d(t: Throwable, message: String, vararg args: Any?)

    /** Log a debug exception.  */
    public fun d(t: Throwable)

    /** Log an info message with optional format args.  */
    public fun i(message: String, vararg args: Any?)

    /** Log an info exception and a message with optional format args.  */
    public fun i(t: Throwable, message: String, vararg args: Any?)

    /** Log an info exception.  */
    public fun i(t: Throwable)

    /** Log a warning message with optional format args.  */
    public fun w(message: String, vararg args: Any?)

    /** Log a warning exception and a message with optional format args.  */
    public fun w(t: Throwable, message: String, vararg args: Any?)

    /** Log a warning exception.  */
    public fun w(t: Throwable)

    /** Log an error message with optional format args.  */
    public fun e(message: String, vararg args: Any?)

    /** Log an error exception and a message with optional format args.  */
    public fun e(t: Throwable, message: String, vararg args: Any?)

    /** Log an error exception.  */
    public fun e(t: Throwable)

    /** Log an assert message with optional format args.  */
    public fun wtf(message: String, vararg args: Any?)

    /** Log an assert exception and a message with optional format args.  */
    public fun wtf(t: Throwable, message: String, vararg args: Any?)

    /** Log an assert exception.  */
    public fun wtf(t: Throwable)
}
