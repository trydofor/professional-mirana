package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.i18n.AssertErrorEnum;
import pro.fessional.mirana.pain.BadStateException;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertEmpty1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertEqual2;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertFalse1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertGreater2;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertGreaterEqual2;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertLess2;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertLessEqual2;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNotEmpty1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNotEqual2;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNotNull1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNull1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertTrue1;
import static pro.fessional.mirana.text.FormatUtil.logback;
import static pro.fessional.mirana.text.FormatUtil.named;

/**
 * @author trydofor
 * @since 2025-01-14
 */
@SuppressWarnings("DataFlowIssue")
class AssertStateTest {

    final String name = "name";
    final String msg1 = "must be true";
    final String msg2 = "{} must be true";

    private void e1(Object error, Executable exec) {
        if (error != null) {
            IllegalStateException e = assertThrows(IllegalStateException.class, exec);
            assertEquals(named(name, msg1), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    private void e2(Object error, Executable exec) {
        if (error != null) {
            IllegalStateException e = assertThrows(IllegalStateException.class, exec);
            assertEquals(named(name, logback(msg2, name)), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    private void e3(AssertErrorEnum msg, Executable exec) {
        if (msg != null) {
            BadStateException e = assertThrows(BadStateException.class, exec);
            assertEquals(msg.getHint(), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    @Test
    public void testIsTrue() {
        e1(AssertTrue1, () -> AssertState.isTrue(false, name, msg1));
        e2(AssertTrue1, () -> AssertState.isTrue(false, name, msg2, name));
        e3(AssertTrue1, () -> AssertState.isTrue(false, name));
        e3(AssertTrue1, () -> AssertState.isTrue(false, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertState.isTrue(false, name, AssertTrue1, name));
        e1(AssertTrue1, () -> AssertState.isTrue(null, name, msg1));
        e2(AssertTrue1, () -> AssertState.isTrue(null, name, msg2, name));
        e3(AssertTrue1, () -> AssertState.isTrue(null, name));
        e3(AssertTrue1, () -> AssertState.isTrue(null, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertState.isTrue(null, name, AssertTrue1, name));
        e1(AssertTrue1, () -> AssertState.isTrue(new boolean[]{}, name, msg1));
        e2(AssertTrue1, () -> AssertState.isTrue(new boolean[]{}, name, msg2, name));
        e3(AssertTrue1, () -> AssertState.isTrue(new boolean[]{}, name));
        e3(AssertTrue1, () -> AssertState.isTrue(new boolean[]{}, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertState.isTrue(new boolean[]{}, name, AssertTrue1, name));
        e1(AssertTrue1, () -> AssertState.isTrue(new boolean[]{ true, false }, name, msg1));
        e2(AssertTrue1, () -> AssertState.isTrue(new boolean[]{ true, false }, name, msg2, name));
        e3(AssertTrue1, () -> AssertState.isTrue(new boolean[]{ true, false }, name));
        e3(AssertTrue1, () -> AssertState.isTrue(new boolean[]{ true, false }, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertState.isTrue(new boolean[]{ true, false }, name, AssertTrue1, name));

        e1(null, () -> AssertState.isTrue(true, name, msg1));
        e2(null, () -> AssertState.isTrue(true, name, msg2, name));
        e3(null, () -> AssertState.isTrue(true, name));
        e3(null, () -> AssertState.isTrue(true, name, AssertTrue1));
        e3(null, () -> AssertState.isTrue(true, name, AssertTrue1, name));
        e1(null, () -> AssertState.isTrue(new boolean[]{ true, true }, name, msg1));
        e2(null, () -> AssertState.isTrue(new boolean[]{ true, true }, name, msg2, name));
        e3(null, () -> AssertState.isTrue(new boolean[]{ true, true }, name));
        e3(null, () -> AssertState.isTrue(new boolean[]{ true, true }, name, AssertTrue1));
        e3(null, () -> AssertState.isTrue(new boolean[]{ true, true }, name, AssertTrue1, name));
    }

    @Test
    public void testIsFalse() {
        e1(AssertFalse1, () -> AssertState.isFalse(true, name, msg1));
        e2(AssertFalse1, () -> AssertState.isFalse(true, name, msg2, name));
        e3(AssertFalse1, () -> AssertState.isFalse(true, name));
        e3(AssertFalse1, () -> AssertState.isFalse(true, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertState.isFalse(true, name, AssertFalse1, name));
        e1(AssertFalse1, () -> AssertState.isFalse(null, name, msg1));
        e2(AssertFalse1, () -> AssertState.isFalse(null, name, msg2, name));
        e3(AssertFalse1, () -> AssertState.isFalse(null, name));
        e3(AssertFalse1, () -> AssertState.isFalse(null, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertState.isFalse(null, name, AssertFalse1, name));
        e1(AssertFalse1, () -> AssertState.isFalse(new boolean[]{}, name, msg1));
        e2(AssertFalse1, () -> AssertState.isFalse(new boolean[]{}, name, msg2, name));
        e3(AssertFalse1, () -> AssertState.isFalse(new boolean[]{}, name));
        e3(AssertFalse1, () -> AssertState.isFalse(new boolean[]{}, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertState.isFalse(new boolean[]{}, name, AssertFalse1, name));
        e1(AssertFalse1, () -> AssertState.isFalse(new boolean[]{ true, false }, name, msg1));
        e2(AssertFalse1, () -> AssertState.isFalse(new boolean[]{ true, false }, name, msg2, name));
        e3(AssertFalse1, () -> AssertState.isFalse(new boolean[]{ true, false }, name));
        e3(AssertFalse1, () -> AssertState.isFalse(new boolean[]{ true, false }, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertState.isFalse(new boolean[]{ true, false }, name, AssertFalse1, name));

        e1(null, () -> AssertState.isFalse(false, name, msg1));
        e2(null, () -> AssertState.isFalse(false, name, msg2, name));
        e3(null, () -> AssertState.isFalse(false, name));
        e3(null, () -> AssertState.isFalse(false, name, AssertFalse1));
        e3(null, () -> AssertState.isFalse(false, name, AssertFalse1, name));
        e1(null, () -> AssertState.isFalse(new boolean[]{ false, false }, name, msg1));
        e2(null, () -> AssertState.isFalse(new boolean[]{ false, false }, name, msg2, name));
        e3(null, () -> AssertState.isFalse(new boolean[]{ false, false }, name));
        e3(null, () -> AssertState.isFalse(new boolean[]{ false, false }, name, AssertFalse1));
        e3(null, () -> AssertState.isFalse(new boolean[]{ false, false }, name, AssertFalse1, name));
    }

    @Test
    public void testIsNull() {
        e1(AssertNull1, () -> AssertState.isNull("", name, msg1));
        e2(AssertNull1, () -> AssertState.isNull("", name, msg2, name));
        e3(AssertNull1, () -> AssertState.isNull("", name));
        e3(AssertNull1, () -> AssertState.isNull("", name, AssertNull1));
        e3(AssertNull1, () -> AssertState.isNull("", name, AssertNull1, name));
        e1(null, () -> AssertState.isNull(null, name, msg1));
        e2(null, () -> AssertState.isNull(null, name, msg2, name));
        e3(null, () -> AssertState.isNull(null, name));
        e3(null, () -> AssertState.isNull(null, name, AssertNull1));
        e3(null, () -> AssertState.isNull(null, name, AssertNull1, name));
    }

    @Test
    public void testNotNull() {
        e1(AssertNotNull1, () -> AssertState.notNull(null, name, msg1));
        e2(AssertNotNull1, () -> AssertState.notNull(null, name, msg2, name));
        e3(AssertNotNull1, () -> AssertState.notNull(null, name));
        e3(AssertNotNull1, () -> AssertState.notNull(null, name, AssertNotNull1));
        e3(AssertNotNull1, () -> AssertState.notNull(null, name, AssertNotNull1, name));
        e1(null, () -> AssertState.notNull("", name, msg1));
        e2(null, () -> AssertState.notNull("", name, msg2, name));
        e3(null, () -> AssertState.notNull("", name));
        e3(null, () -> AssertState.notNull("", name, AssertNotNull1));
        e3(null, () -> AssertState.notNull("", name, AssertNotNull1, name));
    }

    @Test
    public void testIsEmpty() {
        e1(AssertEmpty1, () -> AssertState.isEmpty("1", name, msg1));
        e2(AssertEmpty1, () -> AssertState.isEmpty("1", name, msg2, name));
        e3(AssertEmpty1, () -> AssertState.isEmpty("1", name));
        e3(AssertEmpty1, () -> AssertState.isEmpty("1", name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertState.isEmpty("1", name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty((String) null, name, msg1));
        e2(null, () -> AssertState.isEmpty((String) null, name, msg2, name));
        e3(null, () -> AssertState.isEmpty((String) null, name));
        e3(null, () -> AssertState.isEmpty((String) null, name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty((String) null, name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty("", name, msg1));
        e2(null, () -> AssertState.isEmpty("", name, msg2, name));
        e3(null, () -> AssertState.isEmpty("", name));
        e3(null, () -> AssertState.isEmpty("", name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty("", name, AssertEmpty1, name));

        e1(AssertEmpty1, () -> AssertState.isEmpty(singletonList("1"), name, msg1));
        e2(AssertEmpty1, () -> AssertState.isEmpty(singletonList("1"), name, msg2, name));
        e3(AssertEmpty1, () -> AssertState.isEmpty(singletonList("1"), name));
        e3(AssertEmpty1, () -> AssertState.isEmpty(singletonList("1"), name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertState.isEmpty(singletonList("1"), name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty((List<?>) null, name, msg1));
        e2(null, () -> AssertState.isEmpty((List<?>) null, name, msg2, name));
        e3(null, () -> AssertState.isEmpty((List<?>) null, name));
        e3(null, () -> AssertState.isEmpty((List<?>) null, name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty((List<?>) null, name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty(emptyList(), name, msg1));
        e2(null, () -> AssertState.isEmpty(emptyList(), name, msg2, name));
        e3(null, () -> AssertState.isEmpty(emptyList(), name));
        e3(null, () -> AssertState.isEmpty(emptyList(), name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty(emptyList(), name, AssertEmpty1, name));

        e1(AssertEmpty1, () -> AssertState.isEmpty(singletonMap("1", ""), name, msg1));
        e2(AssertEmpty1, () -> AssertState.isEmpty(singletonMap("1", ""), name, msg2, name));
        e3(AssertEmpty1, () -> AssertState.isEmpty(singletonMap("1", ""), name));
        e3(AssertEmpty1, () -> AssertState.isEmpty(singletonMap("1", ""), name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertState.isEmpty(singletonMap("1", ""), name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty((Map<?, ?>) null, name, msg1));
        e2(null, () -> AssertState.isEmpty((Map<?, ?>) null, name, msg2, name));
        e3(null, () -> AssertState.isEmpty((Map<?, ?>) null, name));
        e3(null, () -> AssertState.isEmpty((Map<?, ?>) null, name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty((Map<?, ?>) null, name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty(emptyMap(), name, msg1));
        e2(null, () -> AssertState.isEmpty(emptyMap(), name, msg2, name));
        e3(null, () -> AssertState.isEmpty(emptyMap(), name));
        e3(null, () -> AssertState.isEmpty(emptyMap(), name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty(emptyMap(), name, AssertEmpty1, name));

        e1(AssertEmpty1, () -> AssertState.isEmpty(new Object[]{ "1", "" }, name, msg1));
        e2(AssertEmpty1, () -> AssertState.isEmpty(new Object[]{ "1", "" }, name, msg2, name));
        e3(AssertEmpty1, () -> AssertState.isEmpty(new Object[]{ "1", "" }, name));
        e3(AssertEmpty1, () -> AssertState.isEmpty(new Object[]{ "1", "" }, name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertState.isEmpty(new Object[]{ "1", "" }, name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty((Object[]) null, name, msg1));
        e2(null, () -> AssertState.isEmpty((Object[]) null, name, msg2, name));
        e3(null, () -> AssertState.isEmpty((Object[]) null, name));
        e3(null, () -> AssertState.isEmpty((Object[]) null, name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty((Object[]) null, name, AssertEmpty1, name));
        e1(null, () -> AssertState.isEmpty(Null.Objects, name, msg1));
        e2(null, () -> AssertState.isEmpty(Null.Objects, name, msg2, name));
        e3(null, () -> AssertState.isEmpty(Null.Objects, name));
        e3(null, () -> AssertState.isEmpty(Null.Objects, name, AssertEmpty1));
        e3(null, () -> AssertState.isEmpty(Null.Objects, name, AssertEmpty1, name));
    }

    @Test
    public void testNotEmpty() {
        e1(AssertNotEmpty1, () -> AssertState.notEmpty((String) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty((String) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((String) null, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((String) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((String) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertState.notEmpty("", name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty("", name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty("", name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty("", name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty("", name, AssertNotEmpty1, name));
        e1(null, () -> AssertState.notEmpty("1", name, msg1));
        e2(null, () -> AssertState.notEmpty("1", name, msg2, name));
        e3(null, () -> AssertState.notEmpty("1", name));
        e3(null, () -> AssertState.notEmpty("1", name, AssertNotEmpty1));
        e3(null, () -> AssertState.notEmpty("1", name, AssertNotEmpty1, name));

        e1(AssertNotEmpty1, () -> AssertState.notEmpty((List<?>) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty((List<?>) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((List<?>) null, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((List<?>) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((List<?>) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertState.notEmpty(emptyList(), name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty(emptyList(), name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(emptyList(), name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(emptyList(), name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(emptyList(), name, AssertNotEmpty1, name));
        e1(null, () -> AssertState.notEmpty(singletonList("1"), name, msg1));
        e2(null, () -> AssertState.notEmpty(singletonList("1"), name, msg2, name));
        e3(null, () -> AssertState.notEmpty(singletonList("1"), name));
        e3(null, () -> AssertState.notEmpty(singletonList("1"), name, AssertNotEmpty1));
        e3(null, () -> AssertState.notEmpty(singletonList("1"), name, AssertNotEmpty1, name));

        e1(AssertNotEmpty1, () -> AssertState.notEmpty((Map<?, ?>) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty((Map<?, ?>) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((Map<?, ?>) null, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((Map<?, ?>) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((Map<?, ?>) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertState.notEmpty(emptyMap(), name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty(emptyMap(), name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(emptyMap(), name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(emptyMap(), name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(emptyMap(), name, AssertNotEmpty1, name));
        e1(null, () -> AssertState.notEmpty(singletonMap("1", ""), name, msg1));
        e2(null, () -> AssertState.notEmpty(singletonMap("1", ""), name, msg2, name));
        e3(null, () -> AssertState.notEmpty(singletonMap("1", ""), name));
        e3(null, () -> AssertState.notEmpty(singletonMap("1", ""), name, AssertNotEmpty1));
        e3(null, () -> AssertState.notEmpty(singletonMap("1", ""), name, AssertNotEmpty1, name));

        e1(AssertNotEmpty1, () -> AssertState.notEmpty((Object[]) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty((Object[]) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((Object[]) null, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((Object[]) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty((Object[]) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertState.notEmpty(Null.Objects, name, msg1));
        e2(AssertNotEmpty1, () -> AssertState.notEmpty(Null.Objects, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(Null.Objects, name));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(Null.Objects, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertState.notEmpty(Null.Objects, name, AssertNotEmpty1, name));
        e1(null, () -> AssertState.notEmpty(new Object[]{ "1" }, name, msg1));
        e2(null, () -> AssertState.notEmpty(new Object[]{ "1" }, name, msg2, name));
        e3(null, () -> AssertState.notEmpty(new Object[]{ "1" }, name));
        e3(null, () -> AssertState.notEmpty(new Object[]{ "1" }, name, AssertNotEmpty1));
        e3(null, () -> AssertState.notEmpty(new Object[]{ "1" }, name, AssertNotEmpty1, name));
    }

    @Test
    public void testIsEqual() {
        e1(AssertEqual2, () -> AssertState.isEqual(null, "1", name, msg1));
        e2(AssertEqual2, () -> AssertState.isEqual(null, "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertState.isEqual(null, "1", name));
        e3(AssertEqual2, () -> AssertState.isEqual(null, "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.isEqual(null, "1", name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertState.isEqual("", "1", name, msg1));
        e2(AssertEqual2, () -> AssertState.isEqual("", "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertState.isEqual("", "1", name));
        e3(AssertEqual2, () -> AssertState.isEqual("", "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.isEqual("", "1", name, AssertEqual2, name));
        e1(null, () -> AssertState.isEqual("1", "1", name, msg1));
        e2(null, () -> AssertState.isEqual("1", "1", name, msg2, name));
        e3(null, () -> AssertState.isEqual("1", "1", name));
        e3(null, () -> AssertState.isEqual("1", "1", name, AssertEqual2));
        e3(null, () -> AssertState.isEqual("1", "1", name, AssertEqual2, name));
    }

    @Test
    public void testNotEqual() {
        e1(AssertNotEqual2, () -> AssertState.notEqual("1", "1", name, msg1));
        e2(AssertNotEqual2, () -> AssertState.notEqual("1", "1", name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.notEqual("1", "1", name));
        e3(AssertNotEqual2, () -> AssertState.notEqual("1", "1", name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.notEqual("1", "1", name, AssertNotEqual2, name));
        e1(null, () -> AssertState.notEqual(null, "1", name, msg1));
        e2(null, () -> AssertState.notEqual(null, "1", name, msg2, name));
        e3(null, () -> AssertState.notEqual(null, "1", name));
        e3(null, () -> AssertState.notEqual(null, "1", name, AssertNotEqual2));
        e3(null, () -> AssertState.notEqual(null, "1", name, AssertNotEqual2, name));
        e1(null, () -> AssertState.notEqual("", "1", name, msg1));
        e2(null, () -> AssertState.notEqual("", "1", name, msg2, name));
        e3(null, () -> AssertState.notEqual("", "1", name));
        e3(null, () -> AssertState.notEqual("", "1", name, AssertNotEqual2));
        e3(null, () -> AssertState.notEqual("", "1", name, AssertNotEqual2, name));
    }

    @Test
    public void testEqObj() {
        e1(AssertEqual2, () -> AssertState.eqObj(null, "1", name, msg1));
        e2(AssertEqual2, () -> AssertState.eqObj(null, "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqObj(null, "1", name));
        e3(AssertEqual2, () -> AssertState.eqObj(null, "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqObj(null, "1", name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertState.eqObj("", "1", name, msg1));
        e2(AssertEqual2, () -> AssertState.eqObj("", "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqObj("", "1", name));
        e3(AssertEqual2, () -> AssertState.eqObj("", "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqObj("", "1", name, AssertEqual2, name));
        e1(null, () -> AssertState.eqObj("1", "1", name, msg1));
        e2(null, () -> AssertState.eqObj("1", "1", name, msg2, name));
        e3(null, () -> AssertState.eqObj("1", "1", name));
        e3(null, () -> AssertState.eqObj("1", "1", name, AssertEqual2));
        e3(null, () -> AssertState.eqObj("1", "1", name, AssertEqual2, name));
    }

    @Test
    public void testNeObj() {
        e1(AssertNotEqual2, () -> AssertState.neObj("1", "1", name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neObj("1", "1", name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neObj("1", "1", name));
        e3(AssertNotEqual2, () -> AssertState.neObj("1", "1", name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neObj("1", "1", name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertState.neObj(null, "1", name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neObj(null, "1", name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neObj(null, "1", name));
        e3(AssertNotEqual2, () -> AssertState.neObj(null, "1", name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neObj(null, "1", name, AssertNotEqual2, name));
        e1(null, () -> AssertState.neObj("", "1", name, msg1));
        e2(null, () -> AssertState.neObj("", "1", name, msg2, name));
        e3(null, () -> AssertState.neObj("", "1", name));
        e3(null, () -> AssertState.neObj("", "1", name, AssertNotEqual2));
        e3(null, () -> AssertState.neObj("", "1", name, AssertNotEqual2, name));
    }

    @Test
    public void testGeObj() {
        e1(AssertGreaterEqual2, () -> AssertState.geObj(null, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geObj(null, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geObj(null, 1, name));
        e3(AssertGreaterEqual2, () -> AssertState.geObj(null, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geObj(null, 1, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertState.geObj(0, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geObj(0, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geObj(0, 1, name));
        e3(AssertGreaterEqual2, () -> AssertState.geObj(0, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geObj(0, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geObj(1, 1, name, msg1));
        e2(null, () -> AssertState.geObj(1, 1, name, msg2, name));
        e3(null, () -> AssertState.geObj(1, 1, name));
        e3(null, () -> AssertState.geObj(1, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geObj(1, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geObj(2, 1, name, msg1));
        e2(null, () -> AssertState.geObj(2, 1, name, msg2, name));
        e3(null, () -> AssertState.geObj(2, 1, name));
        e3(null, () -> AssertState.geObj(2, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geObj(2, 1, name, AssertGreaterEqual2, name));
    }

    @Test
    public void testGtObj() {
        e1(AssertGreater2, () -> AssertState.gtObj(null, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtObj(null, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtObj(null, 1, name));
        e3(AssertGreater2, () -> AssertState.gtObj(null, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtObj(null, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtObj(0, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtObj(0, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtObj(0, 1, name));
        e3(AssertGreater2, () -> AssertState.gtObj(0, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtObj(0, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtObj(1, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtObj(1, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtObj(1, 1, name));
        e3(AssertGreater2, () -> AssertState.gtObj(1, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtObj(1, 1, name, AssertGreater2, name));
        e1(null, () -> AssertState.gtObj(2, 1, name, msg1));
        e2(null, () -> AssertState.gtObj(2, 1, name, msg2, name));
        e3(null, () -> AssertState.gtObj(2, 1, name));
        e3(null, () -> AssertState.gtObj(2, 1, name, AssertGreater2));
        e3(null, () -> AssertState.gtObj(2, 1, name, AssertGreater2, name));
    }

    @Test
    public void testLeObj() {
        e1(AssertLessEqual2, () -> AssertState.leObj(null, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leObj(null, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leObj(null, 1, name));
        e3(AssertLessEqual2, () -> AssertState.leObj(null, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leObj(null, 1, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertState.leObj(2, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leObj(2, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leObj(2, 1, name));
        e3(AssertLessEqual2, () -> AssertState.leObj(2, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leObj(2, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leObj(1, 1, name, msg1));
        e2(null, () -> AssertState.leObj(1, 1, name, msg2, name));
        e3(null, () -> AssertState.leObj(1, 1, name));
        e3(null, () -> AssertState.leObj(1, 1, name, AssertLessEqual2));
        e3(null, () -> AssertState.leObj(1, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leObj(0, 1, name, msg1));
        e2(null, () -> AssertState.leObj(0, 1, name, msg2, name));
        e3(null, () -> AssertState.leObj(0, 1, name));
        e3(null, () -> AssertState.leObj(0, 1, name, AssertLessEqual2));
        e3(null, () -> AssertState.leObj(0, 1, name, AssertLessEqual2, name));
    }

    @Test
    public void testLtObj() {
        e1(AssertLess2, () -> AssertState.ltObj(null, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltObj(null, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltObj(null, 1, name));
        e3(AssertLess2, () -> AssertState.ltObj(null, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltObj(null, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltObj(2, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltObj(2, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltObj(2, 1, name));
        e3(AssertLess2, () -> AssertState.ltObj(2, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltObj(2, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltObj(1, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltObj(1, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltObj(1, 1, name));
        e3(AssertLess2, () -> AssertState.ltObj(1, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltObj(1, 1, name, AssertLess2, name));
        e1(null, () -> AssertState.ltObj(0, 1, name, msg1));
        e2(null, () -> AssertState.ltObj(0, 1, name, msg2, name));
        e3(null, () -> AssertState.ltObj(0, 1, name));
        e3(null, () -> AssertState.ltObj(0, 1, name, AssertLess2));
        e3(null, () -> AssertState.ltObj(0, 1, name, AssertLess2, name));
    }

    @Test
    public void testEqVal() {
        e1(AssertEqual2, () -> AssertState.eqVal(0, 1, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal(0, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal(0, 1, name));
        e3(AssertEqual2, () -> AssertState.eqVal(0, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal(0, 1, name, AssertEqual2, name));
        e1(null, () -> AssertState.eqVal(1, 1, name, msg1));
        e2(null, () -> AssertState.eqVal(1, 1, name, msg2, name));
        e3(null, () -> AssertState.eqVal(1, 1, name));
        e3(null, () -> AssertState.eqVal(1, 1, name, AssertEqual2));
        e3(null, () -> AssertState.eqVal(1, 1, name, AssertEqual2, name));

        e1(AssertEqual2, () -> AssertState.eqVal(0L, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal(0L, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal(0L, 1L, name));
        e3(AssertEqual2, () -> AssertState.eqVal(0L, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal(0L, 1L, name, AssertEqual2, name));
        e1(null, () -> AssertState.eqVal(1L, 1L, name, msg1));
        e2(null, () -> AssertState.eqVal(1L, 1L, name, msg2, name));
        e3(null, () -> AssertState.eqVal(1L, 1L, name));
        e3(null, () -> AssertState.eqVal(1L, 1L, name, AssertEqual2));
        e3(null, () -> AssertState.eqVal(1L, 1L, name, AssertEqual2, name));

        e1(AssertEqual2, () -> AssertState.eqVal((int[]) null, 1, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal((int[]) null, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal((int[]) null, 1, name));
        e3(AssertEqual2, () -> AssertState.eqVal((int[]) null, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal((int[]) null, 1, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertState.eqVal(Null.Ints, 1, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal(Null.Ints, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal(Null.Ints, 1, name));
        e3(AssertEqual2, () -> AssertState.eqVal(Null.Ints, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal(Null.Ints, 1, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertEqual2, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, name, AssertEqual2, name));
        e1(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, name, msg1));
        e2(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, name, msg2, name));
        e3(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, name));
        e3(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, name, AssertEqual2));
        e3(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, name, AssertEqual2, name));

        e1(AssertEqual2, () -> AssertState.eqVal(null, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal(null, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal(null, 1L, name));
        e3(AssertEqual2, () -> AssertState.eqVal(null, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal(null, 1L, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertState.eqVal(Null.Longs, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal(Null.Longs, 1L, name));
        e3(AssertEqual2, () -> AssertState.eqVal(Null.Longs, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal(Null.Longs, 1L, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertEqual2, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, name, AssertEqual2, name));
        e1(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, name, msg1));
        e2(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, name, msg2, name));
        e3(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, name));
        e3(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, name, AssertEqual2));
        e3(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, name, AssertEqual2, name));
    }

    @Test
    public void testNeVal() {
        e1(AssertNotEqual2, () -> AssertState.neVal(1, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal(1, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(1, 1, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(1, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal(1, 1, name, AssertNotEqual2, name));
        e1(null, () -> AssertState.neVal(0, 1, name, msg1));
        e2(null, () -> AssertState.neVal(0, 1, name, msg2, name));
        e3(null, () -> AssertState.neVal(0, 1, name));
        e3(null, () -> AssertState.neVal(0, 1, name, AssertNotEqual2));
        e3(null, () -> AssertState.neVal(0, 1, name, AssertNotEqual2, name));

        e1(AssertNotEqual2, () -> AssertState.neVal(1L, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal(1L, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(1L, 1L, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(1L, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal(1L, 1L, name, AssertNotEqual2, name));
        e1(null, () -> AssertState.neVal(0L, 1L, name, msg1));
        e2(null, () -> AssertState.neVal(0L, 1L, name, msg2, name));
        e3(null, () -> AssertState.neVal(0L, 1L, name));
        e3(null, () -> AssertState.neVal(0L, 1L, name, AssertNotEqual2));
        e3(null, () -> AssertState.neVal(0L, 1L, name, AssertNotEqual2, name));

        e1(AssertNotEqual2, () -> AssertState.neVal((int[]) null, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal((int[]) null, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal((int[]) null, 1, name));
        e3(AssertNotEqual2, () -> AssertState.neVal((int[]) null, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal((int[]) null, 1, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertState.neVal(Null.Ints, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal(Null.Ints, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(Null.Ints, 1, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(Null.Ints, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal(Null.Ints, 1, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, name, AssertNotEqual2, name));
        e1(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, name, msg1));
        e2(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, name, msg2, name));
        e3(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, name));
        e3(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, name, AssertNotEqual2));
        e3(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, name, AssertNotEqual2, name));

        e1(AssertNotEqual2, () -> AssertState.neVal(null, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal(null, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(null, 1L, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(null, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal(null, 1L, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertState.neVal(Null.Longs, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(Null.Longs, 1L, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(Null.Longs, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal(Null.Longs, 1L, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertNotEqual2, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, name, AssertNotEqual2, name));
        e1(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, name, msg1));
        e2(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, name, msg2, name));
        e3(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, name));
        e3(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, name, AssertNotEqual2));
        e3(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, name, AssertNotEqual2, name));
    }

    @Test
    public void testGeVal() {
        e1(AssertGreaterEqual2, () -> AssertState.geVal(0, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal(0, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(0, 1, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(0, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(0, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geVal(1, 1, name, msg1));
        e2(null, () -> AssertState.geVal(1, 1, name, msg2, name));
        e3(null, () -> AssertState.geVal(1, 1, name));
        e3(null, () -> AssertState.geVal(1, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geVal(1, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geVal(2, 1, name, msg1));
        e2(null, () -> AssertState.geVal(2, 1, name, msg2, name));
        e3(null, () -> AssertState.geVal(2, 1, name));
        e3(null, () -> AssertState.geVal(2, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geVal(2, 1, name, AssertGreaterEqual2, name));

        e1(AssertGreaterEqual2, () -> AssertState.geVal(0L, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal(0L, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(0L, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(0L, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(0L, 1L, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geVal(1L, 1L, name, msg1));
        e2(null, () -> AssertState.geVal(1L, 1L, name, msg2, name));
        e3(null, () -> AssertState.geVal(1L, 1L, name));
        e3(null, () -> AssertState.geVal(1L, 1L, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geVal(1L, 1L, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geVal(2L, 1L, name, msg1));
        e2(null, () -> AssertState.geVal(2L, 1L, name, msg2, name));
        e3(null, () -> AssertState.geVal(2L, 1L, name));
        e3(null, () -> AssertState.geVal(2L, 1L, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geVal(2L, 1L, name, AssertGreaterEqual2, name));

        e1(AssertGreaterEqual2, () -> AssertState.geVal((int[]) null, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal((int[]) null, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal((int[]) null, 1, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal((int[]) null, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal((int[]) null, 1, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertState.geVal(Null.Ints, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal(Null.Ints, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(Null.Ints, 1, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(Null.Ints, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(Null.Ints, 1, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, name, msg1));
        e2(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, name, msg2, name));
        e3(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, name));
        e3(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, name, AssertGreaterEqual2, name));

        e1(AssertGreaterEqual2, () -> AssertState.geVal(null, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal(null, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(null, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(null, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(null, 1L, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertState.geVal(Null.Longs, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(Null.Longs, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(Null.Longs, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(Null.Longs, 1L, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, name, msg1));
        e2(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, name, msg2, name));
        e3(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, name));
        e3(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, name, AssertGreaterEqual2));
        e3(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, name, AssertGreaterEqual2, name));
    }

    @Test
    public void testGtVal() {
        e1(AssertGreater2, () -> AssertState.gtVal(0, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(0, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(0, 1, name));
        e3(AssertGreater2, () -> AssertState.gtVal(0, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(0, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtVal(1, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(1, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(1, 1, name));
        e3(AssertGreater2, () -> AssertState.gtVal(1, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(1, 1, name, AssertGreater2, name));
        e1(null, () -> AssertState.gtVal(2, 1, name, msg1));
        e2(null, () -> AssertState.gtVal(2, 1, name, msg2, name));
        e3(null, () -> AssertState.gtVal(2, 1, name));
        e3(null, () -> AssertState.gtVal(2, 1, name, AssertGreater2));
        e3(null, () -> AssertState.gtVal(2, 1, name, AssertGreater2, name));

        e1(AssertGreater2, () -> AssertState.gtVal(0L, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(0L, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(0L, 1L, name));
        e3(AssertGreater2, () -> AssertState.gtVal(0L, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(0L, 1L, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtVal(1L, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(1L, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(1L, 1L, name));
        e3(AssertGreater2, () -> AssertState.gtVal(1L, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(1L, 1L, name, AssertGreater2, name));
        e1(null, () -> AssertState.gtVal(2L, 1L, name, msg1));
        e2(null, () -> AssertState.gtVal(2L, 1L, name, msg2, name));
        e3(null, () -> AssertState.gtVal(2L, 1L, name));
        e3(null, () -> AssertState.gtVal(2L, 1L, name, AssertGreater2));
        e3(null, () -> AssertState.gtVal(2L, 1L, name, AssertGreater2, name));

        e1(AssertGreater2, () -> AssertState.gtVal((int[]) null, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal((int[]) null, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal((int[]) null, 1, name));
        e3(AssertGreater2, () -> AssertState.gtVal((int[]) null, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal((int[]) null, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtVal(Null.Ints, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(Null.Ints, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(Null.Ints, 1, name));
        e3(AssertGreater2, () -> AssertState.gtVal(Null.Ints, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(Null.Ints, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertGreater2, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, name, AssertGreater2, name));
        e1(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, name, msg1));
        e2(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, name, msg2, name));
        e3(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, name));
        e3(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, name, AssertGreater2));
        e3(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, name, AssertGreater2, name));

        e1(AssertGreater2, () -> AssertState.gtVal(null, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(null, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(null, 1L, name));
        e3(AssertGreater2, () -> AssertState.gtVal(null, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(null, 1L, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtVal(Null.Longs, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(Null.Longs, 1L, name));
        e3(AssertGreater2, () -> AssertState.gtVal(Null.Longs, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(Null.Longs, 1L, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertGreater2, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, name, AssertGreater2, name));
        e1(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, name, msg1));
        e2(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, name, msg2, name));
        e3(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, name));
        e3(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, name, AssertGreater2));
        e3(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, name, AssertGreater2, name));
    }

    @Test
    public void testLeVal() {
        e1(AssertLessEqual2, () -> AssertState.leVal(2, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal(2, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(2, 1, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(2, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal(2, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leVal(1, 1, name, msg1));
        e2(null, () -> AssertState.leVal(1, 1, name, msg2, name));
        e3(null, () -> AssertState.leVal(1, 1, name));
        e3(null, () -> AssertState.leVal(1, 1, name, AssertLessEqual2));
        e3(null, () -> AssertState.leVal(1, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leVal(0, 1, name, msg1));
        e2(null, () -> AssertState.leVal(0, 1, name, msg2, name));
        e3(null, () -> AssertState.leVal(0, 1, name));
        e3(null, () -> AssertState.leVal(0, 1, name, AssertLessEqual2));
        e3(null, () -> AssertState.leVal(0, 1, name, AssertLessEqual2, name));

        e1(AssertLessEqual2, () -> AssertState.leVal(2L, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal(2L, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(2L, 1L, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(2L, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal(2L, 1L, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leVal(1L, 1L, name, msg1));
        e2(null, () -> AssertState.leVal(1L, 1L, name, msg2, name));
        e3(null, () -> AssertState.leVal(1L, 1L, name));
        e3(null, () -> AssertState.leVal(1L, 1L, name, AssertLessEqual2));
        e3(null, () -> AssertState.leVal(1L, 1L, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leVal(0L, 1L, name, msg1));
        e2(null, () -> AssertState.leVal(0L, 1L, name, msg2, name));
        e3(null, () -> AssertState.leVal(0L, 1L, name));
        e3(null, () -> AssertState.leVal(0L, 1L, name, AssertLessEqual2));
        e3(null, () -> AssertState.leVal(0L, 1L, name, AssertLessEqual2, name));

        e1(AssertLessEqual2, () -> AssertState.leVal((int[]) null, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal((int[]) null, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal((int[]) null, 1, name));
        e3(AssertLessEqual2, () -> AssertState.leVal((int[]) null, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal((int[]) null, 1, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertState.leVal(Null.Ints, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal(Null.Ints, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(Null.Ints, 1, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(Null.Ints, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal(Null.Ints, 1, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, name, msg1));
        e2(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, name, msg2, name));
        e3(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, name));
        e3(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, name, AssertLessEqual2));
        e3(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, name, AssertLessEqual2, name));

        e1(AssertLessEqual2, () -> AssertState.leVal(null, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal(null, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(null, 1L, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(null, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal(null, 1L, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertState.leVal(Null.Longs, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(Null.Longs, 1L, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(Null.Longs, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal(Null.Longs, 1L, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, name));
        e3(AssertLessEqual2, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, name, AssertLessEqual2, name));
        e1(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, name, msg1));
        e2(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, name, msg2, name));
        e3(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, name));
        e3(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, name, AssertLessEqual2));
        e3(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, name, AssertLessEqual2, name));
    }

    @Test
    public void testLtVal() {
        e1(AssertLess2, () -> AssertState.ltVal(2, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(2, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(2, 1, name));
        e3(AssertLess2, () -> AssertState.ltVal(2, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(2, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltVal(1, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(1, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(1, 1, name));
        e3(AssertLess2, () -> AssertState.ltVal(1, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(1, 1, name, AssertLess2, name));
        e1(null, () -> AssertState.ltVal(0, 1, name, msg1));
        e2(null, () -> AssertState.ltVal(0, 1, name, msg2, name));
        e3(null, () -> AssertState.ltVal(0, 1, name));
        e3(null, () -> AssertState.ltVal(0, 1, name, AssertLess2));
        e3(null, () -> AssertState.ltVal(0, 1, name, AssertLess2, name));

        e1(AssertLess2, () -> AssertState.ltVal(2L, 1L, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(2L, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(2L, 1L, name));
        e3(AssertLess2, () -> AssertState.ltVal(2L, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(2L, 1L, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltVal(1L, 1L, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(1L, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(1L, 1L, name));
        e3(AssertLess2, () -> AssertState.ltVal(1L, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(1L, 1L, name, AssertLess2, name));
        e1(null, () -> AssertState.ltVal(0L, 1L, name, msg1));
        e2(null, () -> AssertState.ltVal(0L, 1L, name, msg2, name));
        e3(null, () -> AssertState.ltVal(0L, 1L, name));
        e3(null, () -> AssertState.ltVal(0L, 1L, name, AssertLess2));
        e3(null, () -> AssertState.ltVal(0L, 1L, name, AssertLess2, name));

        e1(AssertLess2, () -> AssertState.ltVal((int[]) null, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal((int[]) null, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal((int[]) null, 1, name));
        e3(AssertLess2, () -> AssertState.ltVal((int[]) null, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal((int[]) null, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltVal(Null.Ints, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(Null.Ints, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(Null.Ints, 1, name));
        e3(AssertLess2, () -> AssertState.ltVal(Null.Ints, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(Null.Ints, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertLess2, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, name, AssertLess2, name));
        e1(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, msg1));
        e2(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, msg2, name));
        e3(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name));
        e3(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess2));
        e3(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess2, name));

        e1(AssertLess2, () -> AssertState.ltVal(null, 1L, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(null, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(null, 1L, name));
        e3(AssertLess2, () -> AssertState.ltVal(null, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(null, 1L, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltVal(Null.Longs, 1L, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(Null.Longs, 1L, name));
        e3(AssertLess2, () -> AssertState.ltVal(Null.Longs, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(Null.Longs, 1L, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertLess2, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertLess2, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, name, AssertLess2, name));
        e1(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, name, msg1));
        e2(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, name, msg2, name));
        e3(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, name));
        e3(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, name, AssertLess2));
        e3(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, name, AssertLess2, name));
    }
}