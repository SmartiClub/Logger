package utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import club.smarti.logger.L;
import club.smarti.logger.LogWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutputVerifier extends LogWriter {

    private int mExpectedLevel = L.NOTHING;
    private String mExpectedTag;
    private String mExpectedMessage;
    private boolean mExecuted;

    public OutputVerifier() {
    }

    public void setExpected(int level, @NotNull String tag, @NotNull String message) {
        mExpectedLevel = level;
        mExpectedTag = tag;
        mExpectedMessage = message;
        mExecuted = false;
    }

    public void setNotExpected() {
        mExpectedLevel = L.NOTHING;
        mExpectedTag = null;
        mExpectedMessage = null;
        mExecuted = false;
    }

    @Override
    public void verbose(@Nullable String tag, @NotNull String message) {
        assertFalse(mExecuted);
        assertEquals(mExpectedLevel, L.VERBOSE);
        assertEquals(mExpectedTag, tag);
        assertEquals(mExpectedMessage, message);
        mExecuted = true;
    }

    @Override
    public void debug(@Nullable String tag, @NotNull String message) {
        assertFalse(mExecuted);
        assertEquals(mExpectedLevel, L.DEBUG);
        assertEquals(mExpectedTag, tag);
        assertEquals(mExpectedMessage, message);
        mExecuted = true;
    }

    @Override
    public void info(@Nullable String tag, @NotNull String message) {
        assertFalse(mExecuted);
        assertEquals(mExpectedLevel, L.INFO);
        assertEquals(mExpectedTag, tag);
        assertEquals(mExpectedMessage, message);
        mExecuted = true;
    }

    @Override
    public void warning(@Nullable String tag, @NotNull String message) {
        assertFalse(mExecuted);
        assertEquals(mExpectedLevel, L.WARNING);
        assertEquals(mExpectedTag, tag);
        assertEquals(mExpectedMessage, message);
        mExecuted = true;
    }

    @Override
    public void error(@Nullable String tag, @NotNull String message) {
        assertFalse(mExecuted);
        assertEquals(mExpectedLevel, L.ERROR);
        assertEquals(mExpectedTag, tag);
        assertEquals(mExpectedMessage, message);
        mExecuted = true;
    }

    public void assertExecuted() {
        assertTrue(mExecuted);
        mExecuted = false;
    }

    public void assertNotExecuted() {
        assertFalse(mExecuted);
    }
}
