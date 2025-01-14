package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.i18n.AssertErrorEnum;
import pro.fessional.mirana.pain.BadArgsException;

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
class AssertArgsTest {

    final String name = "name";
    final String msg1 = "must be true";
    final String msg2 = "{} must be true";

    private void e1(Object error, Executable exec) {
        if (error != null) {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, exec);
            assertEquals(named(name, msg1), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    private void e2(Object error, Executable exec) {
        if (error != null) {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, exec);
            assertEquals(named(name, logback(msg2, name)), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    private void e3(AssertErrorEnum msg, Executable exec) {
        if (msg != null) {
            BadArgsException e = assertThrows(BadArgsException.class, exec);
            assertEquals(msg.getHint(), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    @Test
    public void testIsTrue() {
        e1(AssertTrue1, () -> AssertArgs.isTrue(false, name, msg1));
        e2(AssertTrue1, () -> AssertArgs.isTrue(false, name, msg2, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(false, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(false, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertArgs.isTrue(false, name, AssertTrue1, name));
        e1(AssertTrue1, () -> AssertArgs.isTrue(null, name, msg1));
        e2(AssertTrue1, () -> AssertArgs.isTrue(null, name, msg2, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(null, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(null, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertArgs.isTrue(null, name, AssertTrue1, name));
        e1(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{}, name, msg1));
        e2(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{}, name, msg2, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{}, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{}, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{}, name, AssertTrue1, name));
        e1(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{ true, false }, name, msg1));
        e2(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{ true, false }, name, msg2, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{ true, false }, name));
        e3(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{ true, false }, name, AssertTrue1));
        e3(AssertTrue1, () -> AssertArgs.isTrue(new boolean[]{ true, false }, name, AssertTrue1, name));

        e1(null, () -> AssertArgs.isTrue(true, name, msg1));
        e2(null, () -> AssertArgs.isTrue(true, name, msg2, name));
        e3(null, () -> AssertArgs.isTrue(true, name));
        e3(null, () -> AssertArgs.isTrue(true, name, AssertTrue1));
        e3(null, () -> AssertArgs.isTrue(true, name, AssertTrue1, name));
        e1(null, () -> AssertArgs.isTrue(new boolean[]{ true, true }, name, msg1));
        e2(null, () -> AssertArgs.isTrue(new boolean[]{ true, true }, name, msg2, name));
        e3(null, () -> AssertArgs.isTrue(new boolean[]{ true, true }, name));
        e3(null, () -> AssertArgs.isTrue(new boolean[]{ true, true }, name, AssertTrue1));
        e3(null, () -> AssertArgs.isTrue(new boolean[]{ true, true }, name, AssertTrue1, name));
    }

    @Test
    public void testIsFalse() {
        e1(AssertFalse1, () -> AssertArgs.isFalse(true, name, msg1));
        e2(AssertFalse1, () -> AssertArgs.isFalse(true, name, msg2, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(true, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(true, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertArgs.isFalse(true, name, AssertFalse1, name));
        e1(AssertFalse1, () -> AssertArgs.isFalse(null, name, msg1));
        e2(AssertFalse1, () -> AssertArgs.isFalse(null, name, msg2, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(null, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(null, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertArgs.isFalse(null, name, AssertFalse1, name));
        e1(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{}, name, msg1));
        e2(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{}, name, msg2, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{}, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{}, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{}, name, AssertFalse1, name));
        e1(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{ true, false }, name, msg1));
        e2(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{ true, false }, name, msg2, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{ true, false }, name));
        e3(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{ true, false }, name, AssertFalse1));
        e3(AssertFalse1, () -> AssertArgs.isFalse(new boolean[]{ true, false }, name, AssertFalse1, name));

        e1(null, () -> AssertArgs.isFalse(false, name, msg1));
        e2(null, () -> AssertArgs.isFalse(false, name, msg2, name));
        e3(null, () -> AssertArgs.isFalse(false, name));
        e3(null, () -> AssertArgs.isFalse(false, name, AssertFalse1));
        e3(null, () -> AssertArgs.isFalse(false, name, AssertFalse1, name));
        e1(null, () -> AssertArgs.isFalse(new boolean[]{ false, false }, name, msg1));
        e2(null, () -> AssertArgs.isFalse(new boolean[]{ false, false }, name, msg2, name));
        e3(null, () -> AssertArgs.isFalse(new boolean[]{ false, false }, name));
        e3(null, () -> AssertArgs.isFalse(new boolean[]{ false, false }, name, AssertFalse1));
        e3(null, () -> AssertArgs.isFalse(new boolean[]{ false, false }, name, AssertFalse1, name));
    }

    @Test
    public void testIsNull() {
        e1(AssertNull1, () -> AssertArgs.isNull("", name, msg1));
        e2(AssertNull1, () -> AssertArgs.isNull("", name, msg2, name));
        e3(AssertNull1, () -> AssertArgs.isNull("", name));
        e3(AssertNull1, () -> AssertArgs.isNull("", name, AssertNull1));
        e3(AssertNull1, () -> AssertArgs.isNull("", name, AssertNull1, name));
        e1(null, () -> AssertArgs.isNull(null, name, msg1));
        e2(null, () -> AssertArgs.isNull(null, name, msg2, name));
        e3(null, () -> AssertArgs.isNull(null, name));
        e3(null, () -> AssertArgs.isNull(null, name, AssertNull1));
        e3(null, () -> AssertArgs.isNull(null, name, AssertNull1, name));
    }

    @Test
    public void testNotNull() {
        e1(AssertNotNull1, () -> AssertArgs.notNull(null, name, msg1));
        e2(AssertNotNull1, () -> AssertArgs.notNull(null, name, msg2, name));
        e3(AssertNotNull1, () -> AssertArgs.notNull(null, name));
        e3(AssertNotNull1, () -> AssertArgs.notNull(null, name, AssertNotNull1));
        e3(AssertNotNull1, () -> AssertArgs.notNull(null, name, AssertNotNull1, name));
        e1(null, () -> AssertArgs.notNull("", name, msg1));
        e2(null, () -> AssertArgs.notNull("", name, msg2, name));
        e3(null, () -> AssertArgs.notNull("", name));
        e3(null, () -> AssertArgs.notNull("", name, AssertNotNull1));
        e3(null, () -> AssertArgs.notNull("", name, AssertNotNull1, name));
    }

    @Test
    public void testIsEmpty() {
        e1(AssertEmpty1, () -> AssertArgs.isEmpty("1", name, msg1));
        e2(AssertEmpty1, () -> AssertArgs.isEmpty("1", name, msg2, name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty("1", name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty("1", name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty("1", name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty((String) null, name, msg1));
        e2(null, () -> AssertArgs.isEmpty((String) null, name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty((String) null, name));
        e3(null, () -> AssertArgs.isEmpty((String) null, name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty((String) null, name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty("", name, msg1));
        e2(null, () -> AssertArgs.isEmpty("", name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty("", name));
        e3(null, () -> AssertArgs.isEmpty("", name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty("", name, AssertEmpty1, name));

        e1(AssertEmpty1, () -> AssertArgs.isEmpty(singletonList("1"), name, msg1));
        e2(AssertEmpty1, () -> AssertArgs.isEmpty(singletonList("1"), name, msg2, name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(singletonList("1"), name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(singletonList("1"), name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(singletonList("1"), name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty((List<?>) null, name, msg1));
        e2(null, () -> AssertArgs.isEmpty((List<?>) null, name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty((List<?>) null, name));
        e3(null, () -> AssertArgs.isEmpty((List<?>) null, name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty((List<?>) null, name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty(emptyList(), name, msg1));
        e2(null, () -> AssertArgs.isEmpty(emptyList(), name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty(emptyList(), name));
        e3(null, () -> AssertArgs.isEmpty(emptyList(), name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty(emptyList(), name, AssertEmpty1, name));

        e1(AssertEmpty1, () -> AssertArgs.isEmpty(singletonMap("1", ""), name, msg1));
        e2(AssertEmpty1, () -> AssertArgs.isEmpty(singletonMap("1", ""), name, msg2, name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(singletonMap("1", ""), name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(singletonMap("1", ""), name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(singletonMap("1", ""), name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty((Map<?, ?>) null, name, msg1));
        e2(null, () -> AssertArgs.isEmpty((Map<?, ?>) null, name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty((Map<?, ?>) null, name));
        e3(null, () -> AssertArgs.isEmpty((Map<?, ?>) null, name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty((Map<?, ?>) null, name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty(emptyMap(), name, msg1));
        e2(null, () -> AssertArgs.isEmpty(emptyMap(), name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty(emptyMap(), name));
        e3(null, () -> AssertArgs.isEmpty(emptyMap(), name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty(emptyMap(), name, AssertEmpty1, name));

        e1(AssertEmpty1, () -> AssertArgs.isEmpty(new Object[]{ "1", "" }, name, msg1));
        e2(AssertEmpty1, () -> AssertArgs.isEmpty(new Object[]{ "1", "" }, name, msg2, name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(new Object[]{ "1", "" }, name));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(new Object[]{ "1", "" }, name, AssertEmpty1));
        e3(AssertEmpty1, () -> AssertArgs.isEmpty(new Object[]{ "1", "" }, name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty((Object[]) null, name, msg1));
        e2(null, () -> AssertArgs.isEmpty((Object[]) null, name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty((Object[]) null, name));
        e3(null, () -> AssertArgs.isEmpty((Object[]) null, name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty((Object[]) null, name, AssertEmpty1, name));
        e1(null, () -> AssertArgs.isEmpty(Null.Objects, name, msg1));
        e2(null, () -> AssertArgs.isEmpty(Null.Objects, name, msg2, name));
        e3(null, () -> AssertArgs.isEmpty(Null.Objects, name));
        e3(null, () -> AssertArgs.isEmpty(Null.Objects, name, AssertEmpty1));
        e3(null, () -> AssertArgs.isEmpty(Null.Objects, name, AssertEmpty1, name));
    }

    @Test
    public void testNotEmpty() {
        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty((String) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty((String) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((String) null, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((String) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((String) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty("", name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty("", name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty("", name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty("", name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty("", name, AssertNotEmpty1, name));
        e1(null, () -> AssertArgs.notEmpty("1", name, msg1));
        e2(null, () -> AssertArgs.notEmpty("1", name, msg2, name));
        e3(null, () -> AssertArgs.notEmpty("1", name));
        e3(null, () -> AssertArgs.notEmpty("1", name, AssertNotEmpty1));
        e3(null, () -> AssertArgs.notEmpty("1", name, AssertNotEmpty1, name));

        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty((List<?>) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty((List<?>) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((List<?>) null, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((List<?>) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((List<?>) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyList(), name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyList(), name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyList(), name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyList(), name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyList(), name, AssertNotEmpty1, name));
        e1(null, () -> AssertArgs.notEmpty(singletonList("1"), name, msg1));
        e2(null, () -> AssertArgs.notEmpty(singletonList("1"), name, msg2, name));
        e3(null, () -> AssertArgs.notEmpty(singletonList("1"), name));
        e3(null, () -> AssertArgs.notEmpty(singletonList("1"), name, AssertNotEmpty1));
        e3(null, () -> AssertArgs.notEmpty(singletonList("1"), name, AssertNotEmpty1, name));

        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty((Map<?, ?>) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty((Map<?, ?>) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((Map<?, ?>) null, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((Map<?, ?>) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((Map<?, ?>) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyMap(), name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyMap(), name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyMap(), name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyMap(), name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(emptyMap(), name, AssertNotEmpty1, name));
        e1(null, () -> AssertArgs.notEmpty(singletonMap("1", ""), name, msg1));
        e2(null, () -> AssertArgs.notEmpty(singletonMap("1", ""), name, msg2, name));
        e3(null, () -> AssertArgs.notEmpty(singletonMap("1", ""), name));
        e3(null, () -> AssertArgs.notEmpty(singletonMap("1", ""), name, AssertNotEmpty1));
        e3(null, () -> AssertArgs.notEmpty(singletonMap("1", ""), name, AssertNotEmpty1, name));

        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty((Object[]) null, name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty((Object[]) null, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((Object[]) null, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((Object[]) null, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty((Object[]) null, name, AssertNotEmpty1, name));
        e1(AssertNotEmpty1, () -> AssertArgs.notEmpty(Null.Objects, name, msg1));
        e2(AssertNotEmpty1, () -> AssertArgs.notEmpty(Null.Objects, name, msg2, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(Null.Objects, name));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(Null.Objects, name, AssertNotEmpty1));
        e3(AssertNotEmpty1, () -> AssertArgs.notEmpty(Null.Objects, name, AssertNotEmpty1, name));
        e1(null, () -> AssertArgs.notEmpty(new Object[]{ "1" }, name, msg1));
        e2(null, () -> AssertArgs.notEmpty(new Object[]{ "1" }, name, msg2, name));
        e3(null, () -> AssertArgs.notEmpty(new Object[]{ "1" }, name));
        e3(null, () -> AssertArgs.notEmpty(new Object[]{ "1" }, name, AssertNotEmpty1));
        e3(null, () -> AssertArgs.notEmpty(new Object[]{ "1" }, name, AssertNotEmpty1, name));
    }

    @Test
    public void testIsEqual() {
        e1(AssertEqual2, () -> AssertArgs.isEqual(null, "1", name, msg1));
        e2(AssertEqual2, () -> AssertArgs.isEqual(null, "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.isEqual(null, "1", name));
        e3(AssertEqual2, () -> AssertArgs.isEqual(null, "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.isEqual(null, "1", name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertArgs.isEqual("", "1", name, msg1));
        e2(AssertEqual2, () -> AssertArgs.isEqual("", "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.isEqual("", "1", name));
        e3(AssertEqual2, () -> AssertArgs.isEqual("", "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.isEqual("", "1", name, AssertEqual2, name));
        e1(null, () -> AssertArgs.isEqual("1", "1", name, msg1));
        e2(null, () -> AssertArgs.isEqual("1", "1", name, msg2, name));
        e3(null, () -> AssertArgs.isEqual("1", "1", name));
        e3(null, () -> AssertArgs.isEqual("1", "1", name, AssertEqual2));
        e3(null, () -> AssertArgs.isEqual("1", "1", name, AssertEqual2, name));
    }

    @Test
    public void testNotEqual() {
        e1(AssertNotEqual2, () -> AssertArgs.notEqual("1", "1", name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.notEqual("1", "1", name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.notEqual("1", "1", name));
        e3(AssertNotEqual2, () -> AssertArgs.notEqual("1", "1", name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.notEqual("1", "1", name, AssertNotEqual2, name));
        e1(null, () -> AssertArgs.notEqual(null, "1", name, msg1));
        e2(null, () -> AssertArgs.notEqual(null, "1", name, msg2, name));
        e3(null, () -> AssertArgs.notEqual(null, "1", name));
        e3(null, () -> AssertArgs.notEqual(null, "1", name, AssertNotEqual2));
        e3(null, () -> AssertArgs.notEqual(null, "1", name, AssertNotEqual2, name));
        e1(null, () -> AssertArgs.notEqual("", "1", name, msg1));
        e2(null, () -> AssertArgs.notEqual("", "1", name, msg2, name));
        e3(null, () -> AssertArgs.notEqual("", "1", name));
        e3(null, () -> AssertArgs.notEqual("", "1", name, AssertNotEqual2));
        e3(null, () -> AssertArgs.notEqual("", "1", name, AssertNotEqual2, name));
    }

    @Test
    public void testEqObj() {
        e1(AssertEqual2, () -> AssertArgs.eqObj(null, "1", name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqObj(null, "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqObj(null, "1", name));
        e3(AssertEqual2, () -> AssertArgs.eqObj(null, "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqObj(null, "1", name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertArgs.eqObj("", "1", name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqObj("", "1", name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqObj("", "1", name));
        e3(AssertEqual2, () -> AssertArgs.eqObj("", "1", name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqObj("", "1", name, AssertEqual2, name));
        e1(null, () -> AssertArgs.eqObj("1", "1", name, msg1));
        e2(null, () -> AssertArgs.eqObj("1", "1", name, msg2, name));
        e3(null, () -> AssertArgs.eqObj("1", "1", name));
        e3(null, () -> AssertArgs.eqObj("1", "1", name, AssertEqual2));
        e3(null, () -> AssertArgs.eqObj("1", "1", name, AssertEqual2, name));
    }

    @Test
    public void testNeObj() {
        e1(AssertNotEqual2, () -> AssertArgs.neObj("1", "1", name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neObj("1", "1", name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neObj("1", "1", name));
        e3(AssertNotEqual2, () -> AssertArgs.neObj("1", "1", name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neObj("1", "1", name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertArgs.neObj(null, "1", name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neObj(null, "1", name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neObj(null, "1", name));
        e3(AssertNotEqual2, () -> AssertArgs.neObj(null, "1", name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neObj(null, "1", name, AssertNotEqual2, name));
        e1(null, () -> AssertArgs.neObj("", "1", name, msg1));
        e2(null, () -> AssertArgs.neObj("", "1", name, msg2, name));
        e3(null, () -> AssertArgs.neObj("", "1", name));
        e3(null, () -> AssertArgs.neObj("", "1", name, AssertNotEqual2));
        e3(null, () -> AssertArgs.neObj("", "1", name, AssertNotEqual2, name));
    }

    @Test
    public void testGeObj() {
        e1(AssertGreaterEqual2, () -> AssertArgs.geObj(null, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geObj(null, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geObj(null, 1, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geObj(null, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geObj(null, 1, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertArgs.geObj(0, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geObj(0, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geObj(0, 1, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geObj(0, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geObj(0, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geObj(1, 1, name, msg1));
        e2(null, () -> AssertArgs.geObj(1, 1, name, msg2, name));
        e3(null, () -> AssertArgs.geObj(1, 1, name));
        e3(null, () -> AssertArgs.geObj(1, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geObj(1, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geObj(2, 1, name, msg1));
        e2(null, () -> AssertArgs.geObj(2, 1, name, msg2, name));
        e3(null, () -> AssertArgs.geObj(2, 1, name));
        e3(null, () -> AssertArgs.geObj(2, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geObj(2, 1, name, AssertGreaterEqual2, name));
    }

    @Test
    public void testGtObj() {
        e1(AssertGreater2, () -> AssertArgs.gtObj(null, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtObj(null, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtObj(null, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtObj(null, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtObj(null, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtObj(0, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtObj(0, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtObj(0, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtObj(0, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtObj(0, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtObj(1, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtObj(1, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtObj(1, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtObj(1, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtObj(1, 1, name, AssertGreater2, name));
        e1(null, () -> AssertArgs.gtObj(2, 1, name, msg1));
        e2(null, () -> AssertArgs.gtObj(2, 1, name, msg2, name));
        e3(null, () -> AssertArgs.gtObj(2, 1, name));
        e3(null, () -> AssertArgs.gtObj(2, 1, name, AssertGreater2));
        e3(null, () -> AssertArgs.gtObj(2, 1, name, AssertGreater2, name));
    }

    @Test
    public void testLeObj() {
        e1(AssertLessEqual2, () -> AssertArgs.leObj(null, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leObj(null, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leObj(null, 1, name));
        e3(AssertLessEqual2, () -> AssertArgs.leObj(null, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leObj(null, 1, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertArgs.leObj(2, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leObj(2, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leObj(2, 1, name));
        e3(AssertLessEqual2, () -> AssertArgs.leObj(2, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leObj(2, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leObj(1, 1, name, msg1));
        e2(null, () -> AssertArgs.leObj(1, 1, name, msg2, name));
        e3(null, () -> AssertArgs.leObj(1, 1, name));
        e3(null, () -> AssertArgs.leObj(1, 1, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leObj(1, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leObj(0, 1, name, msg1));
        e2(null, () -> AssertArgs.leObj(0, 1, name, msg2, name));
        e3(null, () -> AssertArgs.leObj(0, 1, name));
        e3(null, () -> AssertArgs.leObj(0, 1, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leObj(0, 1, name, AssertLessEqual2, name));
    }

    @Test
    public void testLtObj() {
        e1(AssertLess2, () -> AssertArgs.ltObj(null, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltObj(null, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltObj(null, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltObj(null, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltObj(null, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltObj(2, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltObj(2, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltObj(2, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltObj(2, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltObj(2, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltObj(1, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltObj(1, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltObj(1, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltObj(1, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltObj(1, 1, name, AssertLess2, name));
        e1(null, () -> AssertArgs.ltObj(0, 1, name, msg1));
        e2(null, () -> AssertArgs.ltObj(0, 1, name, msg2, name));
        e3(null, () -> AssertArgs.ltObj(0, 1, name));
        e3(null, () -> AssertArgs.ltObj(0, 1, name, AssertLess2));
        e3(null, () -> AssertArgs.ltObj(0, 1, name, AssertLess2, name));
    }

    @Test
    public void testEqVal() {
        e1(AssertEqual2, () -> AssertArgs.eqVal(0, 1, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal(0, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(0, 1, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(0, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal(0, 1, name, AssertEqual2, name));
        e1(null, () -> AssertArgs.eqVal(1, 1, name, msg1));
        e2(null, () -> AssertArgs.eqVal(1, 1, name, msg2, name));
        e3(null, () -> AssertArgs.eqVal(1, 1, name));
        e3(null, () -> AssertArgs.eqVal(1, 1, name, AssertEqual2));
        e3(null, () -> AssertArgs.eqVal(1, 1, name, AssertEqual2, name));

        e1(AssertEqual2, () -> AssertArgs.eqVal(0L, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal(0L, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(0L, 1L, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(0L, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal(0L, 1L, name, AssertEqual2, name));
        e1(null, () -> AssertArgs.eqVal(1L, 1L, name, msg1));
        e2(null, () -> AssertArgs.eqVal(1L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.eqVal(1L, 1L, name));
        e3(null, () -> AssertArgs.eqVal(1L, 1L, name, AssertEqual2));
        e3(null, () -> AssertArgs.eqVal(1L, 1L, name, AssertEqual2, name));

        e1(AssertEqual2, () -> AssertArgs.eqVal((int[]) null, 1, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal((int[]) null, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal((int[]) null, 1, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal((int[]) null, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal((int[]) null, 1, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertArgs.eqVal(Null.Ints, 1, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal(Null.Ints, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(Null.Ints, 1, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(Null.Ints, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal(Null.Ints, 1, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertArgs.eqVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(new int[]{ 0, 1 }, 1, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal(new int[]{ 0, 1 }, 1, name, AssertEqual2, name));
        e1(null, () -> AssertArgs.eqVal(new int[]{ 1, 1 }, 1, name, msg1));
        e2(null, () -> AssertArgs.eqVal(new int[]{ 1, 1 }, 1, name, msg2, name));
        e3(null, () -> AssertArgs.eqVal(new int[]{ 1, 1 }, 1, name));
        e3(null, () -> AssertArgs.eqVal(new int[]{ 1, 1 }, 1, name, AssertEqual2));
        e3(null, () -> AssertArgs.eqVal(new int[]{ 1, 1 }, 1, name, AssertEqual2, name));

        e1(AssertEqual2, () -> AssertArgs.eqVal(null, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal(null, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(null, 1L, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(null, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal(null, 1L, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertArgs.eqVal(Null.Longs, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(Null.Longs, 1L, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(Null.Longs, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal(Null.Longs, 1L, name, AssertEqual2, name));
        e1(AssertEqual2, () -> AssertArgs.eqVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertEqual2, () -> AssertArgs.eqVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertEqual2, () -> AssertArgs.eqVal(new long[]{ 0L, 1L }, 1L, name, AssertEqual2));
        e3(AssertEqual2, () -> AssertArgs.eqVal(new long[]{ 0L, 1L }, 1L, name, AssertEqual2, name));
        e1(null, () -> AssertArgs.eqVal(new long[]{ 1L, 1L }, 1L, name, msg1));
        e2(null, () -> AssertArgs.eqVal(new long[]{ 1L, 1L }, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.eqVal(new long[]{ 1L, 1L }, 1L, name));
        e3(null, () -> AssertArgs.eqVal(new long[]{ 1L, 1L }, 1L, name, AssertEqual2));
        e3(null, () -> AssertArgs.eqVal(new long[]{ 1L, 1L }, 1L, name, AssertEqual2, name));
    }

    @Test
    public void testNeVal() {
        e1(AssertNotEqual2, () -> AssertArgs.neVal(1, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal(1, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(1, 1, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(1, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(1, 1, name, AssertNotEqual2, name));
        e1(null, () -> AssertArgs.neVal(0, 1, name, msg1));
        e2(null, () -> AssertArgs.neVal(0, 1, name, msg2, name));
        e3(null, () -> AssertArgs.neVal(0, 1, name));
        e3(null, () -> AssertArgs.neVal(0, 1, name, AssertNotEqual2));
        e3(null, () -> AssertArgs.neVal(0, 1, name, AssertNotEqual2, name));

        e1(AssertNotEqual2, () -> AssertArgs.neVal(1L, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal(1L, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(1L, 1L, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(1L, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(1L, 1L, name, AssertNotEqual2, name));
        e1(null, () -> AssertArgs.neVal(0L, 1L, name, msg1));
        e2(null, () -> AssertArgs.neVal(0L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.neVal(0L, 1L, name));
        e3(null, () -> AssertArgs.neVal(0L, 1L, name, AssertNotEqual2));
        e3(null, () -> AssertArgs.neVal(0L, 1L, name, AssertNotEqual2, name));

        e1(AssertNotEqual2, () -> AssertArgs.neVal((int[]) null, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal((int[]) null, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal((int[]) null, 1, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal((int[]) null, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal((int[]) null, 1, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertArgs.neVal(Null.Ints, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal(Null.Ints, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(Null.Ints, 1, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(Null.Ints, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(Null.Ints, 1, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertArgs.neVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(new int[]{ 0, 1 }, 1, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(new int[]{ 0, 1 }, 1, name, AssertNotEqual2, name));
        e1(null, () -> AssertArgs.neVal(new int[]{ 1, 1 }, 0, name, msg1));
        e2(null, () -> AssertArgs.neVal(new int[]{ 1, 1 }, 0, name, msg2, name));
        e3(null, () -> AssertArgs.neVal(new int[]{ 1, 1 }, 0, name));
        e3(null, () -> AssertArgs.neVal(new int[]{ 1, 1 }, 0, name, AssertNotEqual2));
        e3(null, () -> AssertArgs.neVal(new int[]{ 1, 1 }, 0, name, AssertNotEqual2, name));

        e1(AssertNotEqual2, () -> AssertArgs.neVal(null, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal(null, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(null, 1L, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(null, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(null, 1L, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertArgs.neVal(Null.Longs, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(Null.Longs, 1L, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(Null.Longs, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(Null.Longs, 1L, name, AssertNotEqual2, name));
        e1(AssertNotEqual2, () -> AssertArgs.neVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertNotEqual2, () -> AssertArgs.neVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(new long[]{ 0L, 1L }, 1L, name, AssertNotEqual2));
        e3(AssertNotEqual2, () -> AssertArgs.neVal(new long[]{ 0L, 1L }, 1L, name, AssertNotEqual2, name));
        e1(null, () -> AssertArgs.neVal(new long[]{ 1L, 1L }, 0L, name, msg1));
        e2(null, () -> AssertArgs.neVal(new long[]{ 1L, 1L }, 0L, name, msg2, name));
        e3(null, () -> AssertArgs.neVal(new long[]{ 1L, 1L }, 0L, name));
        e3(null, () -> AssertArgs.neVal(new long[]{ 1L, 1L }, 0L, name, AssertNotEqual2));
        e3(null, () -> AssertArgs.neVal(new long[]{ 1L, 1L }, 0L, name, AssertNotEqual2, name));
    }

    @Test
    public void testGeVal() {
        e1(AssertGreaterEqual2, () -> AssertArgs.geVal(0, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal(0, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(0, 1, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(0, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(0, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geVal(1, 1, name, msg1));
        e2(null, () -> AssertArgs.geVal(1, 1, name, msg2, name));
        e3(null, () -> AssertArgs.geVal(1, 1, name));
        e3(null, () -> AssertArgs.geVal(1, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geVal(1, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geVal(2, 1, name, msg1));
        e2(null, () -> AssertArgs.geVal(2, 1, name, msg2, name));
        e3(null, () -> AssertArgs.geVal(2, 1, name));
        e3(null, () -> AssertArgs.geVal(2, 1, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geVal(2, 1, name, AssertGreaterEqual2, name));

        e1(AssertGreaterEqual2, () -> AssertArgs.geVal(0L, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal(0L, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(0L, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(0L, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(0L, 1L, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geVal(1L, 1L, name, msg1));
        e2(null, () -> AssertArgs.geVal(1L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.geVal(1L, 1L, name));
        e3(null, () -> AssertArgs.geVal(1L, 1L, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geVal(1L, 1L, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geVal(2L, 1L, name, msg1));
        e2(null, () -> AssertArgs.geVal(2L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.geVal(2L, 1L, name));
        e3(null, () -> AssertArgs.geVal(2L, 1L, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geVal(2L, 1L, name, AssertGreaterEqual2, name));

        e1(AssertGreaterEqual2, () -> AssertArgs.geVal((int[]) null, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal((int[]) null, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal((int[]) null, 1, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal((int[]) null, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal((int[]) null, 1, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Ints, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Ints, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Ints, 1, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Ints, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Ints, 1, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertArgs.geVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(new int[]{ 0, 1 }, 1, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(new int[]{ 0, 1 }, 1, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geVal(new int[]{ 1, 0 }, 0, name, msg1));
        e2(null, () -> AssertArgs.geVal(new int[]{ 1, 0 }, 0, name, msg2, name));
        e3(null, () -> AssertArgs.geVal(new int[]{ 1, 0 }, 0, name));
        e3(null, () -> AssertArgs.geVal(new int[]{ 1, 0 }, 0, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geVal(new int[]{ 1, 0 }, 0, name, AssertGreaterEqual2, name));

        e1(AssertGreaterEqual2, () -> AssertArgs.geVal(null, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal(null, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(null, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(null, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(null, 1L, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Longs, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Longs, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Longs, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(Null.Longs, 1L, name, AssertGreaterEqual2, name));
        e1(AssertGreaterEqual2, () -> AssertArgs.geVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertGreaterEqual2, () -> AssertArgs.geVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(new long[]{ 0L, 1L }, 1L, name, AssertGreaterEqual2));
        e3(AssertGreaterEqual2, () -> AssertArgs.geVal(new long[]{ 0L, 1L }, 1L, name, AssertGreaterEqual2, name));
        e1(null, () -> AssertArgs.geVal(new long[]{ 1L, 0L }, 0L, name, msg1));
        e2(null, () -> AssertArgs.geVal(new long[]{ 1L, 0L }, 0L, name, msg2, name));
        e3(null, () -> AssertArgs.geVal(new long[]{ 1L, 0L }, 0L, name));
        e3(null, () -> AssertArgs.geVal(new long[]{ 1L, 0L }, 0L, name, AssertGreaterEqual2));
        e3(null, () -> AssertArgs.geVal(new long[]{ 1L, 0L }, 0L, name, AssertGreaterEqual2, name));
    }

    @Test
    public void testGtVal() {
        e1(AssertGreater2, () -> AssertArgs.gtVal(0, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(0, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(0, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(0, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(0, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtVal(1, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(1, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(1, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(1, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(1, 1, name, AssertGreater2, name));
        e1(null, () -> AssertArgs.gtVal(2, 1, name, msg1));
        e2(null, () -> AssertArgs.gtVal(2, 1, name, msg2, name));
        e3(null, () -> AssertArgs.gtVal(2, 1, name));
        e3(null, () -> AssertArgs.gtVal(2, 1, name, AssertGreater2));
        e3(null, () -> AssertArgs.gtVal(2, 1, name, AssertGreater2, name));

        e1(AssertGreater2, () -> AssertArgs.gtVal(0L, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(0L, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(0L, 1L, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(0L, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(0L, 1L, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtVal(1L, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(1L, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(1L, 1L, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(1L, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(1L, 1L, name, AssertGreater2, name));
        e1(null, () -> AssertArgs.gtVal(2L, 1L, name, msg1));
        e2(null, () -> AssertArgs.gtVal(2L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.gtVal(2L, 1L, name));
        e3(null, () -> AssertArgs.gtVal(2L, 1L, name, AssertGreater2));
        e3(null, () -> AssertArgs.gtVal(2L, 1L, name, AssertGreater2, name));

        e1(AssertGreater2, () -> AssertArgs.gtVal((int[]) null, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal((int[]) null, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal((int[]) null, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal((int[]) null, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal((int[]) null, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtVal(Null.Ints, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(Null.Ints, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(Null.Ints, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(Null.Ints, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(Null.Ints, 1, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(new int[]{ 0, 1 }, 1, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(new int[]{ 0, 1 }, 1, name, AssertGreater2, name));
        e1(null, () -> AssertArgs.gtVal(new int[]{ 1, 1 }, 0, name, msg1));
        e2(null, () -> AssertArgs.gtVal(new int[]{ 1, 1 }, 0, name, msg2, name));
        e3(null, () -> AssertArgs.gtVal(new int[]{ 1, 1 }, 0, name));
        e3(null, () -> AssertArgs.gtVal(new int[]{ 1, 1 }, 0, name, AssertGreater2));
        e3(null, () -> AssertArgs.gtVal(new int[]{ 1, 1 }, 0, name, AssertGreater2, name));

        e1(AssertGreater2, () -> AssertArgs.gtVal(null, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(null, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(null, 1L, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(null, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(null, 1L, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtVal(Null.Longs, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(Null.Longs, 1L, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(Null.Longs, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(Null.Longs, 1L, name, AssertGreater2, name));
        e1(AssertGreater2, () -> AssertArgs.gtVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertGreater2, () -> AssertArgs.gtVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertGreater2, () -> AssertArgs.gtVal(new long[]{ 0L, 1L }, 1L, name, AssertGreater2));
        e3(AssertGreater2, () -> AssertArgs.gtVal(new long[]{ 0L, 1L }, 1L, name, AssertGreater2, name));
        e1(null, () -> AssertArgs.gtVal(new long[]{ 1L, 1L }, 0L, name, msg1));
        e2(null, () -> AssertArgs.gtVal(new long[]{ 1L, 1L }, 0L, name, msg2, name));
        e3(null, () -> AssertArgs.gtVal(new long[]{ 1L, 1L }, 0L, name));
        e3(null, () -> AssertArgs.gtVal(new long[]{ 1L, 1L }, 0L, name, AssertGreater2));
        e3(null, () -> AssertArgs.gtVal(new long[]{ 1L, 1L }, 0L, name, AssertGreater2, name));
    }

    @Test
    public void testLeVal() {
        e1(AssertLessEqual2, () -> AssertArgs.leVal(2, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal(2, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(2, 1, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(2, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(2, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leVal(1, 1, name, msg1));
        e2(null, () -> AssertArgs.leVal(1, 1, name, msg2, name));
        e3(null, () -> AssertArgs.leVal(1, 1, name));
        e3(null, () -> AssertArgs.leVal(1, 1, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leVal(1, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leVal(0, 1, name, msg1));
        e2(null, () -> AssertArgs.leVal(0, 1, name, msg2, name));
        e3(null, () -> AssertArgs.leVal(0, 1, name));
        e3(null, () -> AssertArgs.leVal(0, 1, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leVal(0, 1, name, AssertLessEqual2, name));

        e1(AssertLessEqual2, () -> AssertArgs.leVal(2L, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal(2L, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(2L, 1L, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(2L, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(2L, 1L, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leVal(1L, 1L, name, msg1));
        e2(null, () -> AssertArgs.leVal(1L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.leVal(1L, 1L, name));
        e3(null, () -> AssertArgs.leVal(1L, 1L, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leVal(1L, 1L, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leVal(0L, 1L, name, msg1));
        e2(null, () -> AssertArgs.leVal(0L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.leVal(0L, 1L, name));
        e3(null, () -> AssertArgs.leVal(0L, 1L, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leVal(0L, 1L, name, AssertLessEqual2, name));

        e1(AssertLessEqual2, () -> AssertArgs.leVal((int[]) null, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal((int[]) null, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal((int[]) null, 1, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal((int[]) null, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal((int[]) null, 1, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertArgs.leVal(Null.Ints, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal(Null.Ints, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(Null.Ints, 1, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(Null.Ints, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(Null.Ints, 1, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertArgs.leVal(new int[]{ 2, 1 }, 1, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal(new int[]{ 2, 1 }, 1, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(new int[]{ 2, 1 }, 1, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(new int[]{ 2, 1 }, 1, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(new int[]{ 2, 1 }, 1, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leVal(new int[]{ 1, 0 }, 1, name, msg1));
        e2(null, () -> AssertArgs.leVal(new int[]{ 1, 0 }, 1, name, msg2, name));
        e3(null, () -> AssertArgs.leVal(new int[]{ 1, 0 }, 1, name));
        e3(null, () -> AssertArgs.leVal(new int[]{ 1, 0 }, 1, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leVal(new int[]{ 1, 0 }, 1, name, AssertLessEqual2, name));

        e1(AssertLessEqual2, () -> AssertArgs.leVal(null, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal(null, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(null, 1L, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(null, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(null, 1L, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertArgs.leVal(Null.Longs, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(Null.Longs, 1L, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(Null.Longs, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(Null.Longs, 1L, name, AssertLessEqual2, name));
        e1(AssertLessEqual2, () -> AssertArgs.leVal(new long[]{ 2L, 1L }, 1L, name, msg1));
        e2(AssertLessEqual2, () -> AssertArgs.leVal(new long[]{ 2L, 1L }, 1L, name, msg2, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(new long[]{ 2L, 1L }, 1L, name));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(new long[]{ 2L, 1L }, 1L, name, AssertLessEqual2));
        e3(AssertLessEqual2, () -> AssertArgs.leVal(new long[]{ 2L, 1L }, 1L, name, AssertLessEqual2, name));
        e1(null, () -> AssertArgs.leVal(new long[]{ 1L, 0L }, 1L, name, msg1));
        e2(null, () -> AssertArgs.leVal(new long[]{ 1L, 0L }, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.leVal(new long[]{ 1L, 0L }, 1L, name));
        e3(null, () -> AssertArgs.leVal(new long[]{ 1L, 0L }, 1L, name, AssertLessEqual2));
        e3(null, () -> AssertArgs.leVal(new long[]{ 1L, 0L }, 1L, name, AssertLessEqual2, name));
    }

    @Test
    public void testLtVal() {
        e1(AssertLess2, () -> AssertArgs.ltVal(2, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(2, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(2, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(2, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(2, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltVal(1, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(1, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(1, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(1, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(1, 1, name, AssertLess2, name));
        e1(null, () -> AssertArgs.ltVal(0, 1, name, msg1));
        e2(null, () -> AssertArgs.ltVal(0, 1, name, msg2, name));
        e3(null, () -> AssertArgs.ltVal(0, 1, name));
        e3(null, () -> AssertArgs.ltVal(0, 1, name, AssertLess2));
        e3(null, () -> AssertArgs.ltVal(0, 1, name, AssertLess2, name));

        e1(AssertLess2, () -> AssertArgs.ltVal(2L, 1L, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(2L, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(2L, 1L, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(2L, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(2L, 1L, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltVal(1L, 1L, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(1L, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(1L, 1L, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(1L, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(1L, 1L, name, AssertLess2, name));
        e1(null, () -> AssertArgs.ltVal(0L, 1L, name, msg1));
        e2(null, () -> AssertArgs.ltVal(0L, 1L, name, msg2, name));
        e3(null, () -> AssertArgs.ltVal(0L, 1L, name));
        e3(null, () -> AssertArgs.ltVal(0L, 1L, name, AssertLess2));
        e3(null, () -> AssertArgs.ltVal(0L, 1L, name, AssertLess2, name));

        e1(AssertLess2, () -> AssertArgs.ltVal((int[]) null, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal((int[]) null, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal((int[]) null, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltVal((int[]) null, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal((int[]) null, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltVal(Null.Ints, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(Null.Ints, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(Null.Ints, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(Null.Ints, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(Null.Ints, 1, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltVal(new int[]{ 0, 1 }, 1, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(new int[]{ 0, 1 }, 1, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(new int[]{ 0, 1 }, 1, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(new int[]{ 0, 1 }, 1, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(new int[]{ 0, 1 }, 1, name, AssertLess2, name));
        e1(null, () -> AssertArgs.ltVal(new int[]{ 1, 1 }, 2, name, msg1));
        e2(null, () -> AssertArgs.ltVal(new int[]{ 1, 1 }, 2, name, msg2, name));
        e3(null, () -> AssertArgs.ltVal(new int[]{ 1, 1 }, 2, name));
        e3(null, () -> AssertArgs.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess2));
        e3(null, () -> AssertArgs.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess2, name));

        e1(AssertLess2, () -> AssertArgs.ltVal(null, 1L, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(null, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(null, 1L, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(null, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(null, 1L, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltVal(Null.Longs, 1L, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(Null.Longs, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(Null.Longs, 1L, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(Null.Longs, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(Null.Longs, 1L, name, AssertLess2, name));
        e1(AssertLess2, () -> AssertArgs.ltVal(new long[]{ 0L, 1L }, 1L, name, msg1));
        e2(AssertLess2, () -> AssertArgs.ltVal(new long[]{ 0L, 1L }, 1L, name, msg2, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(new long[]{ 0L, 1L }, 1L, name));
        e3(AssertLess2, () -> AssertArgs.ltVal(new long[]{ 0L, 1L }, 1L, name, AssertLess2));
        e3(AssertLess2, () -> AssertArgs.ltVal(new long[]{ 0L, 1L }, 1L, name, AssertLess2, name));
        e1(null, () -> AssertArgs.ltVal(new long[]{ 1L, 1L }, 2L, name, msg1));
        e2(null, () -> AssertArgs.ltVal(new long[]{ 1L, 1L }, 2L, name, msg2, name));
        e3(null, () -> AssertArgs.ltVal(new long[]{ 1L, 1L }, 2L, name));
        e3(null, () -> AssertArgs.ltVal(new long[]{ 1L, 1L }, 2L, name, AssertLess2));
        e3(null, () -> AssertArgs.ltVal(new long[]{ 1L, 1L }, 2L, name, AssertLess2, name));
    }
}