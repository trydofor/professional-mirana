package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DummyBlockTest {

    @Test
    void testIgnore() {
        AtomicReference<Throwable> captured = new AtomicReference<>();
        DummyBlock.TweakIgnore.tweakGlobal(captured::set);

        Throwable testException = new RuntimeException("Test exception");
        DummyBlock.ignore(testException);

        assertEquals(testException, captured.get());

        DummyBlock.TweakIgnore.resetGlobal();
    }

    @Test
    void testIgnoreWithoutHandler() {
        // Test ignore with no handler set
        Throwable testException = new RuntimeException("Test exception");
        assertDoesNotThrow(() -> DummyBlock.ignore(testException));
    }

    @Test
    void testEmpty() {
        assertDoesNotThrow(DummyBlock::empty);
    }

    @Test
    void testNever() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, DummyBlock::never);
        assertEquals("should NOT invoke NEVER", exception.getMessage());

        exception = assertThrows(IllegalStateException.class, () -> DummyBlock.never("Custom message"));
        assertEquals("should NOT invoke NEVER:Custom message", exception.getMessage());
    }

    @Test
    void testTodo() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, DummyBlock::todo);
        assertEquals("should NOT invoke TODO", exception.getMessage());

        exception = assertThrows(IllegalStateException.class, () -> DummyBlock.todo("Custom message"));
        assertEquals("should NOT invoke TODO:Custom message", exception.getMessage());
    }

    @Test
    void testFixme() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, DummyBlock::fixme);
        assertEquals("should NOT invoke FIXME", exception.getMessage());

        exception = assertThrows(IllegalStateException.class, () -> DummyBlock.fixme("Custom message"));
        assertEquals("should NOT invoke FIXME:Custom message", exception.getMessage());
    }
}
