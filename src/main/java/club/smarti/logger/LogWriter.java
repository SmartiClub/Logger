package club.smarti.logger;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Default log writer. Writes messages to System stream
 * *
 * Advantages:
 * – it allows to re-route the logs to Logcat for Android
 * – it allows to implement multiple output, e.g. console and file
 */
public class LogWriter {

    /**
     * Output DEBUG message to system console (default behavior)
     *
     * @param tag - optional tag
     * @param message - log message
     */
    @Contract(pure = true)
    public void debug(@Nullable String tag, @NotNull String message) {
        try {
            if (tag != null) {
                System.out.println(tag + ": " + message);
            }
            else {
                System.out.println(message);
            }
        }
        catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Output ERROR message to system console (default behavior)
     *
     * @param tag - optional tag
     * @param message - log message
     */
    @Contract(pure = true)
    public void error(@Nullable String tag, @NotNull String message) {
        try {
            if (tag != null) {
                System.err.println(tag + ": " + message);
            }
            else {
                System.err.println(message);
            }
        }
        catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Output VERBOSE message to system console (default behavior)
     *
     * @param tag - optional tag
     * @param message - log message
     */
    @Contract(pure = true)
    public void verbose(@Nullable String tag, @NotNull String message) {
        debug(tag, message);
    }

    /**
     * Output INFO message to system console (default behavior)
     *
     * @param tag - optional tag
     * @param message - log message
     */
    @Contract(pure = true)
    public void info(@Nullable String tag, @NotNull String message) {
        debug(tag, message);
    }

    /**
     * Output WARNING message to system console (default behavior)
     *
     * @param tag - optional tag
     * @param message - log message
     */
    @Contract(pure = true)
    public void warning(@Nullable String tag, @NotNull String message) {
        error(tag, message);
    }
}