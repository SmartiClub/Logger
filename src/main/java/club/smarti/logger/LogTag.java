package club.smarti.logger;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Message tag composer
 */
public class LogTag {

    protected boolean mThreadInfo = false;
    protected String mCustomTag = null;

    /**
     * Assign or reset custom tag for further filtering
     *
     * @param tag - optional text label
     */
    public void setCustomTag(@Nullable String tag) {
        mCustomTag = tag;
    }

    /**
     * Turn on/off adding caller thread info to log output
     *
     * @param show - on/off flag
     */
    public void showThread(boolean show) {
        mThreadInfo = show;
    }

    /**
     * Build message tag
     *
     * @param stack - current stack trace (captured in advance for performance optimization)
     * @return tag string
     */
    @NotNull
    @Contract(pure = true)
    public String compose(StackTraceElement[] stack) {
        StringBuilder builder = new StringBuilder();
        if (mCustomTag != null) {
            builder.append(mCustomTag).append(' ');
        }
        addCaller(builder, stack);
        if (mThreadInfo) {
            String threadName = Thread.currentThread().getName();
            builder.append(' ').append(threadName);
        }
        return builder.toString();
    }

    /**
     * Get caller name and line number.
     * Example: "ActivityWords [123]"
     *
     * @param builder - string builder for performance optimization
     * @param stack - current stack trace
     */
    protected void addCaller(@NotNull StringBuilder builder, @NotNull StackTraceElement[] stack) {
        StackTraceElement trace = getCallerInfo(stack);
        if (trace != null) {
            String raw = trace.getClassName();
            String name = raw.substring(raw.lastIndexOf(".") + 1);
            int pos = name.indexOf('$');
            if (pos > 0) {
                pos += 1;
                // Allow anonymous class index, but split inner class name
                if (name.length() - pos > 2) {
                    name = name.substring(pos);
                }
            }
            int line = trace.getLineNumber();
            builder.append(name).append(" [").append(line).append(']');
        }
        else {
            builder.append("unknown");
        }
    }

    /**
     * Parse the stack trace in order to find the caller record
     *
     * @param stack - current stack trace
     */
    @Nullable
    protected StackTraceElement getCallerInfo(@NotNull StackTraceElement[] stack) {
        if (stack.length > 2) {
            // Skip system and the logger calls
            return stack[2];
        }
        return null;
    }
}