package club.smarti.logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Java application logger
 * *
 * Advantages:
 * – easy to debug (custom tag to filter, class name, line number, readable data)
 * – highly customizable
 * – safe to use with JUnit frameworks (Android)
 * – clean code with short names
 * – can be turned on/off
 */
public class L {

    // Log level definitions:
    public static final int NOTHING = 0;
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int DEBUG = 4;
    public static final int VERBOSE = 5;

    private static int mMinLogLevel = DEBUG;

    private static LogTag mTagBuilder;
    private static LogFormatter mFormatter;
    private static LogWriter mWriter;

    // Default config:
    static {
        setup(null, false, null, null, null);
    }

    /**
     * Initial setup (optional)
     *
     * @param tag - custom tag for further filtering
     * @param tagBuilder - custom tag composer (optional)
     * @param formatter - custom object to string translator (optional)
     * @param writer - custom log output manager (optional)
     * @param showThread - print caller thread name
     */
    public static void setup(@Nullable String tag, boolean showThread, @Nullable LogTag tagBuilder, @Nullable LogFormatter formatter, @Nullable LogWriter writer) {
        mTagBuilder = (tagBuilder != null ? tagBuilder : new LogTag());
        mTagBuilder.setCustomTag(tag);
        mTagBuilder.showThread(showThread);

        mFormatter = (formatter != null ? formatter : new LogFormatter());
        mWriter = (writer != null ? writer : new LogWriter());
    }

    /**
     * Set minimal accepted log level, every logger call below that level is ignored
     *
     * @param level - min log level to be applied
     */
    public static void setMinLogLevel(int level) {
        if (level < NOTHING || level > VERBOSE) {
            throw new IllegalArgumentException("Incorrect log level: " + level);
        }
        mMinLogLevel = level;
    }

    /**
     * Output VERBOSE message
     *
     * @param args - data to be printed
     */
    public static void v(@Nullable Object... args) {
        if (mMinLogLevel >= VERBOSE) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            print(VERBOSE, stack, args);
        }
    }

    /**
     * Output DEBUG message
     *
     * @param args - data to be printed
     */
    public static void d(@Nullable Object... args) {
        if (mMinLogLevel >= DEBUG) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            print(DEBUG, stack, args);
        }
    }

    /**
     * Output INFO message
     *
     * @param args - data to be printed
     */
    public static void i(@Nullable Object... args) {
        if (mMinLogLevel >= INFO) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            print(INFO, stack, args);
        }
    }

    /**
     * Output WARNING message
     *
     * @param args - data to be printed
     */
    public static void w(@Nullable Object... args) {
        if (mMinLogLevel >= WARNING) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            print(WARNING, stack, args);
        }
    }

    /**
     * Output ERROR message
     *
     * @param args - data to be printed
     */
    public static void e(@Nullable Object... args) {
        if (mMinLogLevel >= ERROR) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            print(ERROR, stack, args);
        }
    }

    /**
     * Print the log
     *
     * @param level - log level to route the output
     * @param stack - current stack trace
     * @param args - optional data
     */
    @SuppressWarnings("EnhancedSwitchMigration")
    private static void print(int level, @NotNull StackTraceElement[] stack, @Nullable Object... args) {
        String tag = mTagBuilder.compose(stack);
        String message = mFormatter.translate(args);

        switch (level) {
            case VERBOSE:
                mWriter.verbose(tag, message);
                break;
            case DEBUG:
                mWriter.debug(tag, message);
                break;
            case INFO:
                mWriter.info(tag, message);
                break;
            case WARNING:
                mWriter.warning(tag, message);
                break;
            case ERROR:
                mWriter.error(tag, message);
                break;
        }
    }
}