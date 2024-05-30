package pro.fessional.mirana.flow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.best.DummyBlock;

import java.util.function.Consumer;

/**
 * @author trydofor
 * @since 2024-05-30
 */
class FlowBreakTest {

    @Test
    void testBreak1() {
        int i = 0;
        for (; i < 10; i++) {
            try {
                consume(i, it -> FlowBreak.breakFlow());
            }
            catch (FlowBreakException fbe) {
                break;
            }
        }
        Assertions.assertEquals(i, 0);
    }

    @Test
    void testBreak2() {
        int i = 0;
        for (; i < 10; i++) {
            try {
                consume(i, it -> FlowBreak.breakFlow(FlowEnum.Break));
            }
            catch (FlowBreakException fbe) {
                Assertions.assertEquals(FlowEnum.Break, fbe.getLabel());
                break;
            }
        }
        Assertions.assertEquals(i, 0);
    }

    @Test
    void testReturn1() {
        int i = 0;
        for (; i < 10; i++) {
            try {
                consume(i, it -> FlowBreak.returnVoid());
            }
            catch (FlowReturnException fbe) {
                Assertions.assertEquals(i, 0);
                Assertions.assertEquals(i, fbe.getOrElse(i));
                return;
            }
        }
        Assertions.fail();
    }

    @Test
    void testReturn2() {
        int i = 0;
        for (; i < 10; i++) {
            try {
                consume(i, it -> FlowBreak.returnValue("value"));
            }
            catch (FlowReturnException fbe) {
                Assertions.assertEquals(i, 0);
                Assertions.assertEquals("value", fbe.getValue());
                return;
            }
        }
        Assertions.fail();
    }

    @Test
    void testReturn3() {
        Assertions.assertNull(testReturn(null, FlowEnum.Return, 5));
        Assertions.assertEquals("1", testReturn("1", FlowEnum.Return, 5));
        Assertions.assertEquals("10", testReturn("2", FlowEnum.Continue, 10));
        try {
            testReturn("2", FlowEnum.Throw, 10);
            Assertions.fail();
        }
        catch (Exception e) {
            Assertions.assertTrue(e instanceof FlowReturnException);
            Assertions.assertEquals("2", ((FlowReturnException)e).getValue());
        }
    }

    String testReturn(String value, Enum<?> label, int max) {
        int i = 0;
        for (; i < max; i++) {
            try {
                consume(i, it -> FlowBreak.returnValue(value, label));
            }
            catch (FlowReturnException fbe) {
                Assertions.assertEquals(value, fbe.getValue());
                Assertions.assertEquals(label, fbe.getLabel());
                if (fbe.getLabel() == FlowEnum.Return) {
                    return fbe.getValue();
                }
                else if (fbe.getLabel() == FlowEnum.Continue) {
                    continue;
                }
                else if (fbe.getLabel() == FlowEnum.Throw) {
                    throw fbe;
                }
                else if (fbe.getLabel() == FlowEnum.Default) {
                    DummyBlock.empty();
                }
            }
            System.out.println(i);
        }
        return String.valueOf(i);
    }

    void consume(int i, Consumer<Integer> ic) {
        ic.accept(i);
    }
}