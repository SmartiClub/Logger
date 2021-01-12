import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import club.smarti.logger.L;
import utils.OutputVerifier;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLogger {

    private final OutputVerifier mOutput = new OutputVerifier();

    @Test
    @Order(1)
    public void testDefault() {
        L.setup(null, false, null, null, mOutput);

        mOutput.setNotExpected();
        L.v("X");
        mOutput.assertNotExecuted();

        mOutput.setExpected(L.DEBUG, "TestLogger [24]", "\"Debug 1\"");
        L.d("Debug 1");
        mOutput.assertExecuted();

        mOutput.setExpected(L.INFO, "TestLogger [28]", "\"Info 1\"");
        L.i("Info 1");
        mOutput.assertExecuted();

        mOutput.setExpected(L.WARNING, "TestLogger [32]", "\"Warning 1\"");
        L.w("Warning 1");
        mOutput.assertExecuted();

        mOutput.setExpected(L.ERROR, "TestLogger [36]", "\"Error 1\"");
        L.e("Error 1");
        mOutput.assertExecuted();
    }

    @Test
    @Order(2)
    public void testNull() {
        L.setup("Tag", false, null, null, mOutput);
        L.setMinLogLevel(L.DEBUG);

        mOutput.setExpected(L.DEBUG, "Tag TestLogger [47]", "null");
        L.d((Object[]) null);
        mOutput.assertExecuted();
    }

    @Test
    @Order(3)
    public void testVerboseLevel() {
        L.setup("V", false, null, null, mOutput);
        L.setMinLogLevel(L.VERBOSE);

        mOutput.setExpected(L.VERBOSE, "V TestLogger [58]", "1, 2, 3");
        L.v(1, 2, 3);
        mOutput.assertExecuted();

        mOutput.setExpected(L.DEBUG, "V TestLogger [62]", "<null>");
        L.d((Object) null);
        mOutput.assertExecuted();

        mOutput.setExpected(L.INFO, "V TestLogger [66]", "1, 2.0, 3.0");
        L.i(1, 2f, 3.0);
        mOutput.assertExecuted();

        mOutput.setExpected(L.WARNING, "V TestLogger [70]", "[1, 2, 3]");
        L.w((Object) new int[]{1, 2, 3});
        mOutput.assertExecuted();

        mOutput.setExpected(L.ERROR, "V TestLogger [74]", "\"S1\", \"S2\"");
        L.e("S1", "S2");
        mOutput.assertExecuted();
    }

    @Test
    @Order(4)
    public void testInfoLevel() {
        L.setup("I", false, null, null, mOutput);
        L.setMinLogLevel(L.INFO);

        mOutput.setNotExpected();
        L.v("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.d("Try");
        mOutput.assertNotExecuted();

        mOutput.setExpected(L.INFO, "I TestLogger [93]", "<null>, <null>");
        L.i(null, null);
        mOutput.assertExecuted();

        mOutput.setExpected(L.WARNING, "I TestLogger [97]", "0, \"text\"");
        L.w(0, "text");
        mOutput.assertExecuted();

        mOutput.setExpected(L.ERROR, "I TestLogger [101]", "\"\"");
        L.e("");
        mOutput.assertExecuted();
    }

    @Test
    @Order(5)
    public void testErrorLevel() {
        L.setup("E", false, null, null, mOutput);
        L.setMinLogLevel(L.ERROR);

        mOutput.setNotExpected();
        L.v("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.d("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.i("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.w("Try");
        mOutput.assertNotExecuted();

        mOutput.setExpected(L.ERROR, "E TestLogger [128]", "\"OK\"");
        L.e("OK");
        mOutput.assertExecuted();
    }

    @Test
    @Order(6)
    public void testNothing() {
        L.setup("X", false, null, null, mOutput);
        L.setMinLogLevel(L.NOTHING);

        mOutput.setNotExpected();
        L.v("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.d("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.i("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.w("Try");
        mOutput.assertNotExecuted();

        mOutput.setNotExpected();
        L.e("Try");
        mOutput.assertNotExecuted();
    }

    @Test
    @Order(7)
    public void testThreadName() {
        L.setup("X", true, null, null, mOutput);
        L.setMinLogLevel(L.VERBOSE);
        Thread.currentThread().setName("TestExecThread");

        mOutput.setExpected(L.DEBUG, "X TestLogger [167] TestExecThread", "100500");
        L.d(100500);
        mOutput.assertExecuted();
    }

    @Test
    @Order(8)
    public void testAnonymousClass() {
        L.setup("X", false, null, null, mOutput);
        L.setMinLogLevel(L.VERBOSE);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                mOutput.setExpected(L.DEBUG, "X TestLogger$1 [181]", "100500");
                L.d(100500);
                mOutput.assertExecuted();
            }
        };
        task.run();
    }
}