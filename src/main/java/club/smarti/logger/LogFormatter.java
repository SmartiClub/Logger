package club.smarti.logger;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import club.smarti.readable.Readable;

/**
 * Default log formatter. Translates data to human readable string
 * *
 * Advantages:
 * – supports base Java types and structures
 * – extendable to convert more complex data
 * – allows to implement filter or watchdog for sensitive data
 */
public class LogFormatter {

    /**
     * Translate array of objects to log string
     *
     * @param args - array of optional data
     * @return log message
     */
    @NotNull
    @Contract(pure = true)
    public String translate(@Nullable Object... args) {
        // Bypass (foolproof)
        if (args == null) {
            return "null";
        }
        // Ambiguous varargs
        if (args.getClass() != Object[].class) {
            return Readable.toString((Object) args);
        }
        return Readable.toString(args);
    }
}