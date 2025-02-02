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
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertEmpty;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertEqual1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertFalse;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertGreater1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertGreaterEqual1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertLess1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertLessEqual1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNotEmpty;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNotEqual1;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNotNull;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertNull;
import static pro.fessional.mirana.i18n.AssertErrorEnum.AssertTrue;
import static pro.fessional.mirana.text.FormatUtil.logback;

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
            assertEquals(msg1, e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    private void e2(Object error, Executable exec) {
        if (error != null) {
            IllegalStateException e = assertThrows(IllegalStateException.class, exec);
            assertEquals(logback(msg2, name), e.getMessage());
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
        e1(AssertTrue, () -> AssertState.isTrue(false, msg1));
        e2(AssertTrue, () -> AssertState.isTrue(false, msg2, name));
        e3(AssertTrue, () -> AssertState.isTrue(false));
        e3(AssertTrue, () -> AssertState.isTrue(false, AssertTrue));
        e3(AssertTrue, () -> AssertState.isTrue(false, AssertTrue));
        e1(AssertTrue, () -> AssertState.isTrue(null, msg1));
        e2(AssertTrue, () -> AssertState.isTrue(null, msg2, name));
        e3(AssertTrue, () -> AssertState.isTrue(null));
        e3(AssertTrue, () -> AssertState.isTrue(null, AssertTrue));
        e3(AssertTrue, () -> AssertState.isTrue(null, AssertTrue));
        e1(AssertTrue, () -> AssertState.isTrue(new boolean[]{}, msg1));
        e2(AssertTrue, () -> AssertState.isTrue(new boolean[]{}, msg2, name));
        e3(AssertTrue, () -> AssertState.isTrue(new boolean[]{}));
        e3(AssertTrue, () -> AssertState.isTrue(new boolean[]{}, AssertTrue));
        e3(AssertTrue, () -> AssertState.isTrue(new boolean[]{}, AssertTrue));
        e1(AssertTrue, () -> AssertState.isTrue(new boolean[]{ true, false }, msg1));
        e2(AssertTrue, () -> AssertState.isTrue(new boolean[]{ true, false }, msg2, name));
        e3(AssertTrue, () -> AssertState.isTrue(new boolean[]{ true, false }));
        e3(AssertTrue, () -> AssertState.isTrue(new boolean[]{ true, false }, AssertTrue));
        e3(AssertTrue, () -> AssertState.isTrue(new boolean[]{ true, false }, AssertTrue, 1));

        e1(null, () -> AssertState.isTrue(true, msg1));
        e2(null, () -> AssertState.isTrue(true, msg2, name));
        e3(null, () -> AssertState.isTrue(true));
        e3(null, () -> AssertState.isTrue(true, AssertTrue));
        e3(null, () -> AssertState.isTrue(true, AssertTrue));
        e1(null, () -> AssertState.isTrue(new boolean[]{ true, true }, msg1));
        e2(null, () -> AssertState.isTrue(new boolean[]{ true, true }, msg2, name));
        e3(null, () -> AssertState.isTrue(new boolean[]{ true, true }));
        e3(null, () -> AssertState.isTrue(new boolean[]{ true, true }, AssertTrue));
        e3(null, () -> AssertState.isTrue(new boolean[]{ true, true }, AssertTrue, 1));
    }

    @Test
    public void testIsFalse() {
        e1(AssertFalse, () -> AssertState.isFalse(true, msg1));
        e2(AssertFalse, () -> AssertState.isFalse(true, msg2, name));
        e3(AssertFalse, () -> AssertState.isFalse(true));
        e3(AssertFalse, () -> AssertState.isFalse(true, AssertFalse));
        e3(AssertFalse, () -> AssertState.isFalse(true, AssertFalse, 1));
        e1(AssertFalse, () -> AssertState.isFalse(null, msg1));
        e2(AssertFalse, () -> AssertState.isFalse(null, msg2, name));
        e3(AssertFalse, () -> AssertState.isFalse(null));
        e3(AssertFalse, () -> AssertState.isFalse(null, AssertFalse));
        e3(AssertFalse, () -> AssertState.isFalse(null, AssertFalse, 1));
        e1(AssertFalse, () -> AssertState.isFalse(new boolean[]{}, msg1));
        e2(AssertFalse, () -> AssertState.isFalse(new boolean[]{}, msg2, name));
        e3(AssertFalse, () -> AssertState.isFalse(new boolean[]{}));
        e3(AssertFalse, () -> AssertState.isFalse(new boolean[]{}, AssertFalse));
        e3(AssertFalse, () -> AssertState.isFalse(new boolean[]{}, AssertFalse, 1));
        e1(AssertFalse, () -> AssertState.isFalse(new boolean[]{ true, false }, msg1));
        e2(AssertFalse, () -> AssertState.isFalse(new boolean[]{ true, false }, msg2, name));
        e3(AssertFalse, () -> AssertState.isFalse(new boolean[]{ true, false }));
        e3(AssertFalse, () -> AssertState.isFalse(new boolean[]{ true, false }, AssertFalse));
        e3(AssertFalse, () -> AssertState.isFalse(new boolean[]{ true, false }, AssertFalse, 1));

        e1(null, () -> AssertState.isFalse(false, msg1));
        e2(null, () -> AssertState.isFalse(false, msg2, name));
        e3(null, () -> AssertState.isFalse(false));
        e3(null, () -> AssertState.isFalse(false, AssertFalse));
        e3(null, () -> AssertState.isFalse(false, AssertFalse, 1));
        e1(null, () -> AssertState.isFalse(new boolean[]{ false, false }, msg1));
        e2(null, () -> AssertState.isFalse(new boolean[]{ false, false }, msg2, name));
        e3(null, () -> AssertState.isFalse(new boolean[]{ false, false }));
        e3(null, () -> AssertState.isFalse(new boolean[]{ false, false }, AssertFalse));
        e3(null, () -> AssertState.isFalse(new boolean[]{ false, false }, AssertFalse, 1));
    }

    @Test
    public void testIsNull() {
        e1(AssertNull, () -> AssertState.isNull("", msg1));
        e2(AssertNull, () -> AssertState.isNull("", msg2, name));
        e3(AssertNull, () -> AssertState.isNull(""));
        e3(AssertNull, () -> AssertState.isNull("", AssertNull));
        e3(AssertNull, () -> AssertState.isNull("", AssertNull, 1));
        e1(null, () -> AssertState.isNull(null, msg1));
        e2(null, () -> AssertState.isNull(null, msg2, name));
        e3(null, () -> AssertState.isNull(null));
        e3(null, () -> AssertState.isNull(null, AssertNull));
        e3(null, () -> AssertState.isNull(null, AssertNull, 1));
    }

    @Test
    public void testNotNull() {
        e1(AssertNotNull, () -> AssertState.notNull(null, msg1));
        e2(AssertNotNull, () -> AssertState.notNull(null, msg2, name));
        e3(AssertNotNull, () -> AssertState.notNull(null));
        e3(AssertNotNull, () -> AssertState.notNull(null, AssertNotNull));
        e3(AssertNotNull, () -> AssertState.notNull(null, AssertNotNull, 1));
        e1(null, () -> AssertState.notNull("", msg1));
        e2(null, () -> AssertState.notNull("", msg2, name));
        e3(null, () -> AssertState.notNull(""));
        e3(null, () -> AssertState.notNull("", AssertNotNull));
        e3(null, () -> AssertState.notNull("", AssertNotNull, 1));
    }

    @Test
    public void testIsEmpty() {
        e1(AssertEmpty, () -> AssertState.isEmpty("1", msg1));
        e2(AssertEmpty, () -> AssertState.isEmpty("1", msg2, name));
        e3(AssertEmpty, () -> AssertState.isEmpty("1"));
        e3(AssertEmpty, () -> AssertState.isEmpty("1", AssertEmpty));
        e3(AssertEmpty, () -> AssertState.isEmpty("1", AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty((String) null, msg1));
        e2(null, () -> AssertState.isEmpty((String) null, msg2, name));
        e3(null, () -> AssertState.isEmpty((String) null));
        e3(null, () -> AssertState.isEmpty((String) null, AssertEmpty));
        e3(null, () -> AssertState.isEmpty((String) null, AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty("", msg1));
        e2(null, () -> AssertState.isEmpty("", msg2, name));
        e3(null, () -> AssertState.isEmpty(""));
        e3(null, () -> AssertState.isEmpty("", AssertEmpty));
        e3(null, () -> AssertState.isEmpty("", AssertEmpty, 1));

        e1(AssertEmpty, () -> AssertState.isEmpty(singletonList("1"), msg1));
        e2(AssertEmpty, () -> AssertState.isEmpty(singletonList("1"), msg2, name));
        e3(AssertEmpty, () -> AssertState.isEmpty(singletonList("1")));
        e3(AssertEmpty, () -> AssertState.isEmpty(singletonList("1"), AssertEmpty));
        e3(AssertEmpty, () -> AssertState.isEmpty(singletonList("1"), AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty((List<?>) null, msg1));
        e2(null, () -> AssertState.isEmpty((List<?>) null, msg2, name));
        e3(null, () -> AssertState.isEmpty((List<?>) null));
        e3(null, () -> AssertState.isEmpty((List<?>) null, AssertEmpty));
        e3(null, () -> AssertState.isEmpty((List<?>) null, AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty(emptyList(), msg1));
        e2(null, () -> AssertState.isEmpty(emptyList(), msg2, name));
        e3(null, () -> AssertState.isEmpty(emptyList()));
        e3(null, () -> AssertState.isEmpty(emptyList(), AssertEmpty));
        e3(null, () -> AssertState.isEmpty(emptyList(), AssertEmpty, 1));

        e1(AssertEmpty, () -> AssertState.isEmpty(singletonMap("1", ""), msg1));
        e2(AssertEmpty, () -> AssertState.isEmpty(singletonMap("1", ""), msg2, name));
        e3(AssertEmpty, () -> AssertState.isEmpty(singletonMap("1", "")));
        e3(AssertEmpty, () -> AssertState.isEmpty(singletonMap("1", ""), AssertEmpty));
        e3(AssertEmpty, () -> AssertState.isEmpty(singletonMap("1", ""), AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty((Map<?, ?>) null, msg1));
        e2(null, () -> AssertState.isEmpty((Map<?, ?>) null, msg2, name));
        e3(null, () -> AssertState.isEmpty((Map<?, ?>) null));
        e3(null, () -> AssertState.isEmpty((Map<?, ?>) null, AssertEmpty));
        e3(null, () -> AssertState.isEmpty((Map<?, ?>) null, AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty(emptyMap(), msg1));
        e2(null, () -> AssertState.isEmpty(emptyMap(), msg2, name));
        e3(null, () -> AssertState.isEmpty(emptyMap()));
        e3(null, () -> AssertState.isEmpty(emptyMap(), AssertEmpty));
        e3(null, () -> AssertState.isEmpty(emptyMap(), AssertEmpty, 1));

        e1(AssertEmpty, () -> AssertState.isEmpty(new Object[]{ "1", "" }, msg1));
        e2(AssertEmpty, () -> AssertState.isEmpty(new Object[]{ "1", "" }, msg2, name));
        e3(AssertEmpty, () -> AssertState.isEmpty(new Object[]{ "1", "" }));
        e3(AssertEmpty, () -> AssertState.isEmpty(new Object[]{ "1", "" }, AssertEmpty));
        e3(AssertEmpty, () -> AssertState.isEmpty(new Object[]{ "1", "" }, AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty((Object[]) null, msg1));
        e2(null, () -> AssertState.isEmpty((Object[]) null, msg2, name));
        e3(null, () -> AssertState.isEmpty((Object[]) null));
        e3(null, () -> AssertState.isEmpty((Object[]) null, AssertEmpty));
        e3(null, () -> AssertState.isEmpty((Object[]) null, AssertEmpty, 1));
        e1(null, () -> AssertState.isEmpty(Null.Objects, msg1));
        e2(null, () -> AssertState.isEmpty(Null.Objects, msg2, name));
        e3(null, () -> AssertState.isEmpty(Null.Objects));
        e3(null, () -> AssertState.isEmpty(Null.Objects, AssertEmpty));
        e3(null, () -> AssertState.isEmpty(Null.Objects, AssertEmpty, 1));
    }

    @Test
    public void testNotEmpty() {
        e1(AssertNotEmpty, () -> AssertState.notEmpty((String) null, msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty((String) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((String) null));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((String) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((String) null, AssertNotEmpty, 1));
        e1(AssertNotEmpty, () -> AssertState.notEmpty("", msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty("", msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(""));
        e3(AssertNotEmpty, () -> AssertState.notEmpty("", AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty("", AssertNotEmpty, 1));
        e1(null, () -> AssertState.notEmpty("1", msg1));
        e2(null, () -> AssertState.notEmpty("1", msg2, name));
        e3(null, () -> AssertState.notEmpty("1"));
        e3(null, () -> AssertState.notEmpty("1", AssertNotEmpty));
        e3(null, () -> AssertState.notEmpty("1", AssertNotEmpty, 1));

        e1(AssertNotEmpty, () -> AssertState.notEmpty((List<?>) null, msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty((List<?>) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((List<?>) null));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((List<?>) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((List<?>) null, AssertNotEmpty, 1));
        e1(AssertNotEmpty, () -> AssertState.notEmpty(emptyList(), msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty(emptyList(), msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(emptyList()));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(emptyList(), AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(emptyList(), AssertNotEmpty, 1));
        e1(null, () -> AssertState.notEmpty(singletonList("1"), msg1));
        e2(null, () -> AssertState.notEmpty(singletonList("1"), msg2, name));
        e3(null, () -> AssertState.notEmpty(singletonList("1")));
        e3(null, () -> AssertState.notEmpty(singletonList("1"), AssertNotEmpty));
        e3(null, () -> AssertState.notEmpty(singletonList("1"), AssertNotEmpty, 1));

        e1(AssertNotEmpty, () -> AssertState.notEmpty((Map<?, ?>) null, msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty((Map<?, ?>) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((Map<?, ?>) null));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((Map<?, ?>) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((Map<?, ?>) null, AssertNotEmpty, 1));
        e1(AssertNotEmpty, () -> AssertState.notEmpty(emptyMap(), msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty(emptyMap(), msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(emptyMap()));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(emptyMap(), AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(emptyMap(), AssertNotEmpty, 1));
        e1(null, () -> AssertState.notEmpty(singletonMap("1", ""), msg1));
        e2(null, () -> AssertState.notEmpty(singletonMap("1", ""), msg2, name));
        e3(null, () -> AssertState.notEmpty(singletonMap("1", "")));
        e3(null, () -> AssertState.notEmpty(singletonMap("1", ""), AssertNotEmpty));
        e3(null, () -> AssertState.notEmpty(singletonMap("1", ""), AssertNotEmpty, 1));

        e1(AssertNotEmpty, () -> AssertState.notEmpty((Object[]) null, msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty((Object[]) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((Object[]) null));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((Object[]) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty((Object[]) null, AssertNotEmpty, 1));
        e1(AssertNotEmpty, () -> AssertState.notEmpty(Null.Objects, msg1));
        e2(AssertNotEmpty, () -> AssertState.notEmpty(Null.Objects, msg2, name));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(Null.Objects));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(Null.Objects, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertState.notEmpty(Null.Objects, AssertNotEmpty, 1));
        e1(null, () -> AssertState.notEmpty(new Object[]{ "1" }, msg1));
        e2(null, () -> AssertState.notEmpty(new Object[]{ "1" }, msg2, name));
        e3(null, () -> AssertState.notEmpty(new Object[]{ "1" }));
        e3(null, () -> AssertState.notEmpty(new Object[]{ "1" }, AssertNotEmpty));
        e3(null, () -> AssertState.notEmpty(new Object[]{ "1" }, AssertNotEmpty, 1));
    }

    @Test
    public void testIsEqual() {
        e1(AssertEqual1, () -> AssertState.isEqual(null, "1", msg1));
        e2(AssertEqual1, () -> AssertState.isEqual(null, "1", msg2, name));
        e3(AssertEqual1, () -> AssertState.isEqual(null, "1"));
        e3(AssertEqual1, () -> AssertState.isEqual(null, "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertState.isEqual(null, "1", AssertEqual1, name));
        e1(AssertEqual1, () -> AssertState.isEqual("", "1", msg1));
        e2(AssertEqual1, () -> AssertState.isEqual("", "1", msg2, name));
        e3(AssertEqual1, () -> AssertState.isEqual("", "1"));
        e3(AssertEqual1, () -> AssertState.isEqual("", "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertState.isEqual("", "1", AssertEqual1, name));
        e1(null, () -> AssertState.isEqual("1", "1", msg1));
        e2(null, () -> AssertState.isEqual("1", "1", msg2, name));
        e3(null, () -> AssertState.isEqual("1", "1"));
        e3(null, () -> AssertState.isEqual("1", "1", AssertEqual1));
        e3(null, () -> AssertState.isEqual("1", "1", AssertEqual1, name));
    }

    @Test
    public void testNotEqual() {
        e1(AssertNotEqual1, () -> AssertState.notEqual("1", "1", msg1));
        e2(AssertNotEqual1, () -> AssertState.notEqual("1", "1", msg2, name));
        e3(AssertNotEqual1, () -> AssertState.notEqual("1", "1"));
        e3(AssertNotEqual1, () -> AssertState.notEqual("1", "1", AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.notEqual("1", "1", AssertNotEqual1, name));
        e1(null, () -> AssertState.notEqual(null, "1", msg1));
        e2(null, () -> AssertState.notEqual(null, "1", msg2, name));
        e3(null, () -> AssertState.notEqual(null, "1"));
        e3(null, () -> AssertState.notEqual(null, "1", AssertNotEqual1));
        e3(null, () -> AssertState.notEqual(null, "1", AssertNotEqual1, name));
        e1(null, () -> AssertState.notEqual("", "1", msg1));
        e2(null, () -> AssertState.notEqual("", "1", msg2, name));
        e3(null, () -> AssertState.notEqual("", "1"));
        e3(null, () -> AssertState.notEqual("", "1", AssertNotEqual1));
        e3(null, () -> AssertState.notEqual("", "1", AssertNotEqual1, name));
    }

    @Test
    public void testEqObj() {
        e1(AssertEqual1, () -> AssertState.eqObj(null, "1", msg1));
        e2(AssertEqual1, () -> AssertState.eqObj(null, "1", msg2, name));
        e3(AssertEqual1, () -> AssertState.eqObj(null, "1"));
        e3(AssertEqual1, () -> AssertState.eqObj(null, "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqObj(null, "1", AssertEqual1, name));
        e1(AssertEqual1, () -> AssertState.eqObj("", "1", msg1));
        e2(AssertEqual1, () -> AssertState.eqObj("", "1", msg2, name));
        e3(AssertEqual1, () -> AssertState.eqObj("", "1"));
        e3(AssertEqual1, () -> AssertState.eqObj("", "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqObj("", "1", AssertEqual1, name));
        e1(null, () -> AssertState.eqObj("1", "1", msg1));
        e2(null, () -> AssertState.eqObj("1", "1", msg2, name));
        e3(null, () -> AssertState.eqObj("1", "1"));
        e3(null, () -> AssertState.eqObj("1", "1", AssertEqual1));
        e3(null, () -> AssertState.eqObj("1", "1", AssertEqual1, name));
    }

    @Test
    public void testNeObj() {
        e1(AssertNotEqual1, () -> AssertState.neObj("1", "1", msg1));
        e2(AssertNotEqual1, () -> AssertState.neObj("1", "1", msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neObj("1", "1"));
        e3(AssertNotEqual1, () -> AssertState.neObj("1", "1", AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neObj("1", "1", AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertState.neObj(null, "1", msg1));
        e2(AssertNotEqual1, () -> AssertState.neObj(null, "1", msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neObj(null, "1"));
        e3(AssertNotEqual1, () -> AssertState.neObj(null, "1", AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neObj(null, "1", AssertNotEqual1, name));
        e1(null, () -> AssertState.neObj("", "1", msg1));
        e2(null, () -> AssertState.neObj("", "1", msg2, name));
        e3(null, () -> AssertState.neObj("", "1"));
        e3(null, () -> AssertState.neObj("", "1", AssertNotEqual1));
        e3(null, () -> AssertState.neObj("", "1", AssertNotEqual1, name));
    }

    @Test
    public void testGeObj() {
        e1(AssertGreaterEqual1, () -> AssertState.geObj(null, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geObj(null, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geObj(null, 1));
        e3(AssertGreaterEqual1, () -> AssertState.geObj(null, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geObj(null, 1, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertState.geObj(0, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geObj(0, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geObj(0, 1));
        e3(AssertGreaterEqual1, () -> AssertState.geObj(0, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geObj(0, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geObj(1, 1, msg1));
        e2(null, () -> AssertState.geObj(1, 1, msg2, name));
        e3(null, () -> AssertState.geObj(1, 1));
        e3(null, () -> AssertState.geObj(1, 1, AssertGreaterEqual1));
        e3(null, () -> AssertState.geObj(1, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geObj(2, 1, msg1));
        e2(null, () -> AssertState.geObj(2, 1, msg2, name));
        e3(null, () -> AssertState.geObj(2, 1));
        e3(null, () -> AssertState.geObj(2, 1, AssertGreaterEqual1));
        e3(null, () -> AssertState.geObj(2, 1, AssertGreaterEqual1, name));
    }

    @Test
    public void testGtObj() {
        e1(AssertGreater1, () -> AssertState.gtObj(null, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtObj(null, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtObj(null, 1));
        e3(AssertGreater1, () -> AssertState.gtObj(null, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtObj(null, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtObj(0, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtObj(0, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtObj(0, 1));
        e3(AssertGreater1, () -> AssertState.gtObj(0, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtObj(0, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtObj(1, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtObj(1, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtObj(1, 1));
        e3(AssertGreater1, () -> AssertState.gtObj(1, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtObj(1, 1, AssertGreater1, name));
        e1(null, () -> AssertState.gtObj(2, 1, msg1));
        e2(null, () -> AssertState.gtObj(2, 1, msg2, name));
        e3(null, () -> AssertState.gtObj(2, 1));
        e3(null, () -> AssertState.gtObj(2, 1, AssertGreater1));
        e3(null, () -> AssertState.gtObj(2, 1, AssertGreater1, name));
    }

    @Test
    public void testLeObj() {
        e1(AssertLessEqual1, () -> AssertState.leObj(null, 1, msg1));
        e2(AssertLessEqual1, () -> AssertState.leObj(null, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leObj(null, 1));
        e3(AssertLessEqual1, () -> AssertState.leObj(null, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leObj(null, 1, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertState.leObj(2, 1, msg1));
        e2(AssertLessEqual1, () -> AssertState.leObj(2, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leObj(2, 1));
        e3(AssertLessEqual1, () -> AssertState.leObj(2, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leObj(2, 1, AssertLessEqual1, name));
        e1(null, () -> AssertState.leObj(1, 1, msg1));
        e2(null, () -> AssertState.leObj(1, 1, msg2, name));
        e3(null, () -> AssertState.leObj(1, 1));
        e3(null, () -> AssertState.leObj(1, 1, AssertLessEqual1));
        e3(null, () -> AssertState.leObj(1, 1, AssertLessEqual1, name));
        e1(null, () -> AssertState.leObj(0, 1, msg1));
        e2(null, () -> AssertState.leObj(0, 1, msg2, name));
        e3(null, () -> AssertState.leObj(0, 1));
        e3(null, () -> AssertState.leObj(0, 1, AssertLessEqual1));
        e3(null, () -> AssertState.leObj(0, 1, AssertLessEqual1, name));
    }

    @Test
    public void testLtObj() {
        e1(AssertLess1, () -> AssertState.ltObj(null, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltObj(null, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltObj(null, 1));
        e3(AssertLess1, () -> AssertState.ltObj(null, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltObj(null, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltObj(2, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltObj(2, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltObj(2, 1));
        e3(AssertLess1, () -> AssertState.ltObj(2, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltObj(2, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltObj(1, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltObj(1, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltObj(1, 1));
        e3(AssertLess1, () -> AssertState.ltObj(1, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltObj(1, 1, AssertLess1, name));
        e1(null, () -> AssertState.ltObj(0, 1, msg1));
        e2(null, () -> AssertState.ltObj(0, 1, msg2, name));
        e3(null, () -> AssertState.ltObj(0, 1));
        e3(null, () -> AssertState.ltObj(0, 1, AssertLess1));
        e3(null, () -> AssertState.ltObj(0, 1, AssertLess1, name));
    }

    @Test
    public void testEqVal() {
        e1(AssertEqual1, () -> AssertState.eqVal(0, 1, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal(0, 1, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal(0, 1));
        e3(AssertEqual1, () -> AssertState.eqVal(0, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal(0, 1, AssertEqual1, name));
        e1(null, () -> AssertState.eqVal(1, 1, msg1));
        e2(null, () -> AssertState.eqVal(1, 1, msg2, name));
        e3(null, () -> AssertState.eqVal(1, 1));
        e3(null, () -> AssertState.eqVal(1, 1, AssertEqual1));
        e3(null, () -> AssertState.eqVal(1, 1, AssertEqual1, name));

        e1(AssertEqual1, () -> AssertState.eqVal(0L, 1L, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal(0L, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal(0L, 1L));
        e3(AssertEqual1, () -> AssertState.eqVal(0L, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal(0L, 1L, AssertEqual1, name));
        e1(null, () -> AssertState.eqVal(1L, 1L, msg1));
        e2(null, () -> AssertState.eqVal(1L, 1L, msg2, name));
        e3(null, () -> AssertState.eqVal(1L, 1L));
        e3(null, () -> AssertState.eqVal(1L, 1L, AssertEqual1));
        e3(null, () -> AssertState.eqVal(1L, 1L, AssertEqual1, name));

        e1(AssertEqual1, () -> AssertState.eqVal((int[]) null, 1, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal((int[]) null, 1, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal((int[]) null, 1));
        e3(AssertEqual1, () -> AssertState.eqVal((int[]) null, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal((int[]) null, 1, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertState.eqVal(Null.Ints, 1, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal(Null.Ints, 1, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal(Null.Ints, 1));
        e3(AssertEqual1, () -> AssertState.eqVal(Null.Ints, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal(Null.Ints, 1, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1));
        e3(AssertEqual1, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal(new int[]{ 0, 1 }, 1, AssertEqual1, name));
        e1(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, msg1));
        e2(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, msg2, name));
        e3(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1));
        e3(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, AssertEqual1));
        e3(null, () -> AssertState.eqVal(new int[]{ 1, 1 }, 1, AssertEqual1, name));

        e1(AssertEqual1, () -> AssertState.eqVal(null, 1L, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal(null, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal(null, 1L));
        e3(AssertEqual1, () -> AssertState.eqVal(null, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal(null, 1L, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertState.eqVal(Null.Longs, 1L, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal(Null.Longs, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal(Null.Longs, 1L));
        e3(AssertEqual1, () -> AssertState.eqVal(Null.Longs, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal(Null.Longs, 1L, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertEqual1, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertEqual1, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertState.eqVal(new long[]{ 0L, 1L }, 1L, AssertEqual1, name));
        e1(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, msg1));
        e2(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, msg2, name));
        e3(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L));
        e3(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, AssertEqual1));
        e3(null, () -> AssertState.eqVal(new long[]{ 1L, 1L }, 1L, AssertEqual1, name));
    }

    @Test
    public void testNeVal() {
        e1(AssertNotEqual1, () -> AssertState.neVal(1, 1, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal(1, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal(1, 1));
        e3(AssertNotEqual1, () -> AssertState.neVal(1, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal(1, 1, AssertNotEqual1, name));
        e1(null, () -> AssertState.neVal(0, 1, msg1));
        e2(null, () -> AssertState.neVal(0, 1, msg2, name));
        e3(null, () -> AssertState.neVal(0, 1));
        e3(null, () -> AssertState.neVal(0, 1, AssertNotEqual1));
        e3(null, () -> AssertState.neVal(0, 1, AssertNotEqual1, name));

        e1(AssertNotEqual1, () -> AssertState.neVal(1L, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal(1L, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal(1L, 1L));
        e3(AssertNotEqual1, () -> AssertState.neVal(1L, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal(1L, 1L, AssertNotEqual1, name));
        e1(null, () -> AssertState.neVal(0L, 1L, msg1));
        e2(null, () -> AssertState.neVal(0L, 1L, msg2, name));
        e3(null, () -> AssertState.neVal(0L, 1L));
        e3(null, () -> AssertState.neVal(0L, 1L, AssertNotEqual1));
        e3(null, () -> AssertState.neVal(0L, 1L, AssertNotEqual1, name));

        e1(AssertNotEqual1, () -> AssertState.neVal((int[]) null, 1, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal((int[]) null, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal((int[]) null, 1));
        e3(AssertNotEqual1, () -> AssertState.neVal((int[]) null, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal((int[]) null, 1, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertState.neVal(Null.Ints, 1, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal(Null.Ints, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal(Null.Ints, 1));
        e3(AssertNotEqual1, () -> AssertState.neVal(Null.Ints, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal(Null.Ints, 1, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal(new int[]{ 0, 1 }, 1));
        e3(AssertNotEqual1, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal(new int[]{ 0, 1 }, 1, AssertNotEqual1, name));
        e1(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, msg1));
        e2(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, msg2, name));
        e3(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0));
        e3(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, AssertNotEqual1));
        e3(null, () -> AssertState.neVal(new int[]{ 1, 1 }, 0, AssertNotEqual1, name));

        e1(AssertNotEqual1, () -> AssertState.neVal(null, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal(null, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal(null, 1L));
        e3(AssertNotEqual1, () -> AssertState.neVal(null, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal(null, 1L, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertState.neVal(Null.Longs, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal(Null.Longs, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal(Null.Longs, 1L));
        e3(AssertNotEqual1, () -> AssertState.neVal(Null.Longs, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal(Null.Longs, 1L, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertNotEqual1, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertState.neVal(new long[]{ 0L, 1L }, 1L, AssertNotEqual1, name));
        e1(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, msg1));
        e2(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, msg2, name));
        e3(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L));
        e3(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, AssertNotEqual1));
        e3(null, () -> AssertState.neVal(new long[]{ 1L, 1L }, 0L, AssertNotEqual1, name));
    }

    @Test
    public void testGeVal() {
        e1(AssertGreaterEqual1, () -> AssertState.geVal(0, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal(0, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(0, 1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(0, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(0, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geVal(1, 1, msg1));
        e2(null, () -> AssertState.geVal(1, 1, msg2, name));
        e3(null, () -> AssertState.geVal(1, 1));
        e3(null, () -> AssertState.geVal(1, 1, AssertGreaterEqual1));
        e3(null, () -> AssertState.geVal(1, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geVal(2, 1, msg1));
        e2(null, () -> AssertState.geVal(2, 1, msg2, name));
        e3(null, () -> AssertState.geVal(2, 1));
        e3(null, () -> AssertState.geVal(2, 1, AssertGreaterEqual1));
        e3(null, () -> AssertState.geVal(2, 1, AssertGreaterEqual1, name));

        e1(AssertGreaterEqual1, () -> AssertState.geVal(0L, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal(0L, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(0L, 1L));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(0L, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(0L, 1L, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geVal(1L, 1L, msg1));
        e2(null, () -> AssertState.geVal(1L, 1L, msg2, name));
        e3(null, () -> AssertState.geVal(1L, 1L));
        e3(null, () -> AssertState.geVal(1L, 1L, AssertGreaterEqual1));
        e3(null, () -> AssertState.geVal(1L, 1L, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geVal(2L, 1L, msg1));
        e2(null, () -> AssertState.geVal(2L, 1L, msg2, name));
        e3(null, () -> AssertState.geVal(2L, 1L));
        e3(null, () -> AssertState.geVal(2L, 1L, AssertGreaterEqual1));
        e3(null, () -> AssertState.geVal(2L, 1L, AssertGreaterEqual1, name));

        e1(AssertGreaterEqual1, () -> AssertState.geVal((int[]) null, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal((int[]) null, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal((int[]) null, 1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal((int[]) null, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal((int[]) null, 1, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertState.geVal(Null.Ints, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal(Null.Ints, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(Null.Ints, 1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(Null.Ints, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(Null.Ints, 1, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(new int[]{ 0, 1 }, 1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(new int[]{ 0, 1 }, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, msg1));
        e2(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, msg2, name));
        e3(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0));
        e3(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, AssertGreaterEqual1));
        e3(null, () -> AssertState.geVal(new int[]{ 1, 0 }, 0, AssertGreaterEqual1, name));

        e1(AssertGreaterEqual1, () -> AssertState.geVal(null, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal(null, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(null, 1L));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(null, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(null, 1L, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertState.geVal(Null.Longs, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal(Null.Longs, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(Null.Longs, 1L));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(Null.Longs, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(Null.Longs, 1L, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertState.geVal(new long[]{ 0L, 1L }, 1L, AssertGreaterEqual1, name));
        e1(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, msg1));
        e2(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, msg2, name));
        e3(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L));
        e3(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, AssertGreaterEqual1));
        e3(null, () -> AssertState.geVal(new long[]{ 1L, 0L }, 0L, AssertGreaterEqual1, name));
    }

    @Test
    public void testGtVal() {
        e1(AssertGreater1, () -> AssertState.gtVal(0, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(0, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(0, 1));
        e3(AssertGreater1, () -> AssertState.gtVal(0, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(0, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtVal(1, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(1, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(1, 1));
        e3(AssertGreater1, () -> AssertState.gtVal(1, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(1, 1, AssertGreater1, name));
        e1(null, () -> AssertState.gtVal(2, 1, msg1));
        e2(null, () -> AssertState.gtVal(2, 1, msg2, name));
        e3(null, () -> AssertState.gtVal(2, 1));
        e3(null, () -> AssertState.gtVal(2, 1, AssertGreater1));
        e3(null, () -> AssertState.gtVal(2, 1, AssertGreater1, name));

        e1(AssertGreater1, () -> AssertState.gtVal(0L, 1L, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(0L, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(0L, 1L));
        e3(AssertGreater1, () -> AssertState.gtVal(0L, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(0L, 1L, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtVal(1L, 1L, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(1L, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(1L, 1L));
        e3(AssertGreater1, () -> AssertState.gtVal(1L, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(1L, 1L, AssertGreater1, name));
        e1(null, () -> AssertState.gtVal(2L, 1L, msg1));
        e2(null, () -> AssertState.gtVal(2L, 1L, msg2, name));
        e3(null, () -> AssertState.gtVal(2L, 1L));
        e3(null, () -> AssertState.gtVal(2L, 1L, AssertGreater1));
        e3(null, () -> AssertState.gtVal(2L, 1L, AssertGreater1, name));

        e1(AssertGreater1, () -> AssertState.gtVal((int[]) null, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal((int[]) null, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal((int[]) null, 1));
        e3(AssertGreater1, () -> AssertState.gtVal((int[]) null, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal((int[]) null, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtVal(Null.Ints, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(Null.Ints, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(Null.Ints, 1));
        e3(AssertGreater1, () -> AssertState.gtVal(Null.Ints, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(Null.Ints, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1));
        e3(AssertGreater1, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(new int[]{ 0, 1 }, 1, AssertGreater1, name));
        e1(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, msg1));
        e2(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, msg2, name));
        e3(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0));
        e3(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, AssertGreater1));
        e3(null, () -> AssertState.gtVal(new int[]{ 1, 1 }, 0, AssertGreater1, name));

        e1(AssertGreater1, () -> AssertState.gtVal(null, 1L, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(null, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(null, 1L));
        e3(AssertGreater1, () -> AssertState.gtVal(null, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(null, 1L, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtVal(Null.Longs, 1L, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(Null.Longs, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(Null.Longs, 1L));
        e3(AssertGreater1, () -> AssertState.gtVal(Null.Longs, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(Null.Longs, 1L, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertGreater1, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertGreater1, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertState.gtVal(new long[]{ 0L, 1L }, 1L, AssertGreater1, name));
        e1(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, msg1));
        e2(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, msg2, name));
        e3(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L));
        e3(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, AssertGreater1));
        e3(null, () -> AssertState.gtVal(new long[]{ 1L, 1L }, 0L, AssertGreater1, name));
    }

    @Test
    public void testLeVal() {
        e1(AssertLessEqual1, () -> AssertState.leVal(2, 1, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal(2, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal(2, 1));
        e3(AssertLessEqual1, () -> AssertState.leVal(2, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal(2, 1, AssertLessEqual1, name));
        e1(null, () -> AssertState.leVal(1, 1, msg1));
        e2(null, () -> AssertState.leVal(1, 1, msg2, name));
        e3(null, () -> AssertState.leVal(1, 1));
        e3(null, () -> AssertState.leVal(1, 1, AssertLessEqual1));
        e3(null, () -> AssertState.leVal(1, 1, AssertLessEqual1, name));
        e1(null, () -> AssertState.leVal(0, 1, msg1));
        e2(null, () -> AssertState.leVal(0, 1, msg2, name));
        e3(null, () -> AssertState.leVal(0, 1));
        e3(null, () -> AssertState.leVal(0, 1, AssertLessEqual1));
        e3(null, () -> AssertState.leVal(0, 1, AssertLessEqual1, name));

        e1(AssertLessEqual1, () -> AssertState.leVal(2L, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal(2L, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal(2L, 1L));
        e3(AssertLessEqual1, () -> AssertState.leVal(2L, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal(2L, 1L, AssertLessEqual1, name));
        e1(null, () -> AssertState.leVal(1L, 1L, msg1));
        e2(null, () -> AssertState.leVal(1L, 1L, msg2, name));
        e3(null, () -> AssertState.leVal(1L, 1L));
        e3(null, () -> AssertState.leVal(1L, 1L, AssertLessEqual1));
        e3(null, () -> AssertState.leVal(1L, 1L, AssertLessEqual1, name));
        e1(null, () -> AssertState.leVal(0L, 1L, msg1));
        e2(null, () -> AssertState.leVal(0L, 1L, msg2, name));
        e3(null, () -> AssertState.leVal(0L, 1L));
        e3(null, () -> AssertState.leVal(0L, 1L, AssertLessEqual1));
        e3(null, () -> AssertState.leVal(0L, 1L, AssertLessEqual1, name));

        e1(AssertLessEqual1, () -> AssertState.leVal((int[]) null, 1, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal((int[]) null, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal((int[]) null, 1));
        e3(AssertLessEqual1, () -> AssertState.leVal((int[]) null, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal((int[]) null, 1, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertState.leVal(Null.Ints, 1, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal(Null.Ints, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal(Null.Ints, 1));
        e3(AssertLessEqual1, () -> AssertState.leVal(Null.Ints, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal(Null.Ints, 1, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal(new int[]{ 2, 1 }, 1));
        e3(AssertLessEqual1, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal(new int[]{ 2, 1 }, 1, AssertLessEqual1, name));
        e1(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, msg1));
        e2(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, msg2, name));
        e3(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1));
        e3(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, AssertLessEqual1));
        e3(null, () -> AssertState.leVal(new int[]{ 1, 0 }, 1, AssertLessEqual1, name));

        e1(AssertLessEqual1, () -> AssertState.leVal(null, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal(null, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal(null, 1L));
        e3(AssertLessEqual1, () -> AssertState.leVal(null, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal(null, 1L, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertState.leVal(Null.Longs, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal(Null.Longs, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal(Null.Longs, 1L));
        e3(AssertLessEqual1, () -> AssertState.leVal(Null.Longs, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal(Null.Longs, 1L, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L));
        e3(AssertLessEqual1, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertState.leVal(new long[]{ 2L, 1L }, 1L, AssertLessEqual1, name));
        e1(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, msg1));
        e2(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, msg2, name));
        e3(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L));
        e3(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, AssertLessEqual1));
        e3(null, () -> AssertState.leVal(new long[]{ 1L, 0L }, 1L, AssertLessEqual1, name));
    }

    @Test
    public void testLtVal() {
        e1(AssertLess1, () -> AssertState.ltVal(2, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(2, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(2, 1));
        e3(AssertLess1, () -> AssertState.ltVal(2, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(2, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltVal(1, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(1, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(1, 1));
        e3(AssertLess1, () -> AssertState.ltVal(1, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(1, 1, AssertLess1, name));
        e1(null, () -> AssertState.ltVal(0, 1, msg1));
        e2(null, () -> AssertState.ltVal(0, 1, msg2, name));
        e3(null, () -> AssertState.ltVal(0, 1));
        e3(null, () -> AssertState.ltVal(0, 1, AssertLess1));
        e3(null, () -> AssertState.ltVal(0, 1, AssertLess1, name));

        e1(AssertLess1, () -> AssertState.ltVal(2L, 1L, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(2L, 1L, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(2L, 1L));
        e3(AssertLess1, () -> AssertState.ltVal(2L, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(2L, 1L, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltVal(1L, 1L, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(1L, 1L, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(1L, 1L));
        e3(AssertLess1, () -> AssertState.ltVal(1L, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(1L, 1L, AssertLess1, name));
        e1(null, () -> AssertState.ltVal(0L, 1L, msg1));
        e2(null, () -> AssertState.ltVal(0L, 1L, msg2, name));
        e3(null, () -> AssertState.ltVal(0L, 1L));
        e3(null, () -> AssertState.ltVal(0L, 1L, AssertLess1));
        e3(null, () -> AssertState.ltVal(0L, 1L, AssertLess1, name));

        e1(AssertLess1, () -> AssertState.ltVal((int[]) null, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltVal((int[]) null, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal((int[]) null, 1));
        e3(AssertLess1, () -> AssertState.ltVal((int[]) null, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal((int[]) null, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltVal(Null.Ints, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(Null.Ints, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(Null.Ints, 1));
        e3(AssertLess1, () -> AssertState.ltVal(Null.Ints, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(Null.Ints, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1));
        e3(AssertLess1, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(new int[]{ 0, 1 }, 1, AssertLess1, name));
        e1(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, msg1));
        e2(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, msg2, name));
        e3(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name));
        e3(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess1));
        e3(null, () -> AssertState.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess1, name));

        e1(AssertLess1, () -> AssertState.ltVal(null, 1L, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(null, 1L, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(null, 1L));
        e3(AssertLess1, () -> AssertState.ltVal(null, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(null, 1L, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltVal(Null.Longs, 1L, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(Null.Longs, 1L, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(Null.Longs, 1L));
        e3(AssertLess1, () -> AssertState.ltVal(Null.Longs, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(Null.Longs, 1L, AssertLess1, name));
        e1(AssertLess1, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertLess1, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertLess1, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertLess1, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertState.ltVal(new long[]{ 0L, 1L }, 1L, AssertLess1, name));
        e1(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, msg1));
        e2(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, msg2, name));
        e3(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L));
        e3(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, AssertLess1));
        e3(null, () -> AssertState.ltVal(new long[]{ 1L, 1L }, 2L, AssertLess1, name));
    }
}