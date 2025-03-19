package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.i18n.AssertErrorEnum;
import pro.fessional.mirana.pain.MessageException;

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
class AssertMessageTest {

    final String name = "name";
    final String msg1 = "must be true";
    final String msg2 = "{} must be true";

    private void e1(Object error, Executable exec) {
        if (error != null) {
            MessageException e = assertThrows(MessageException.class, exec);
            assertEquals(msg1, e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    private void e2(Object error, Executable exec) {
        if (error != null) {
            MessageException e = assertThrows(MessageException.class, exec);
            assertEquals(logback(msg2, name), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    private void e3(AssertErrorEnum msg, Executable exec) {
        if (msg != null) {
            MessageException e = assertThrows(MessageException.class, exec);
            assertEquals(msg.getHint(), e.getMessage());
        }
        else {
            assertDoesNotThrow(exec);
        }
    }

    @Test
    public void testIsTrue() {
        e1(AssertTrue, () -> AssertMessage.isTrue(false, msg1));
        e2(AssertTrue, () -> AssertMessage.isTrue(false, msg2, name));
        e3(AssertTrue, () -> AssertMessage.isTrue(false));
        e3(AssertTrue, () -> AssertMessage.isTrue(false, AssertTrue));
        e3(AssertTrue, () -> AssertMessage.isTrue(false, AssertTrue, name));
        e1(AssertTrue, () -> AssertMessage.isTrue(null, msg1));
        e2(AssertTrue, () -> AssertMessage.isTrue(null, msg2, name));
        e3(AssertTrue, () -> AssertMessage.isTrue(null));
        e3(AssertTrue, () -> AssertMessage.isTrue(null, AssertTrue));
        e3(AssertTrue, () -> AssertMessage.isTrue(null, AssertTrue, name));
        e1(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{}, msg1));
        e2(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{}, msg2, name));
        e3(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{}));
        e3(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{}, AssertTrue));
        e3(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{}, AssertTrue, name));
        e1(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{ true, false }, msg1));
        e2(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{ true, false }, msg2, name));
        e3(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{ true, false }));
        e3(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{ true, false }, AssertTrue));
        e3(AssertTrue, () -> AssertMessage.isTrue(new boolean[]{ true, false }, AssertTrue, name));

        e1(null, () -> AssertMessage.isTrue(true, msg1));
        e2(null, () -> AssertMessage.isTrue(true, msg2, name));
        e3(null, () -> AssertMessage.isTrue(true));
        e3(null, () -> AssertMessage.isTrue(true, AssertTrue));
        e3(null, () -> AssertMessage.isTrue(true, AssertTrue, name));
        e1(null, () -> AssertMessage.isTrue(new boolean[]{ true, true }, msg1));
        e2(null, () -> AssertMessage.isTrue(new boolean[]{ true, true }, msg2, name));
        e3(null, () -> AssertMessage.isTrue(new boolean[]{ true, true }));
        e3(null, () -> AssertMessage.isTrue(new boolean[]{ true, true }, AssertTrue));
        e3(null, () -> AssertMessage.isTrue(new boolean[]{ true, true }, AssertTrue, name));
    }

    @Test
    public void testIsFalse() {
        e1(AssertFalse, () -> AssertMessage.isFalse(true, msg1));
        e2(AssertFalse, () -> AssertMessage.isFalse(true, msg2, name));
        e3(AssertFalse, () -> AssertMessage.isFalse(true));
        e3(AssertFalse, () -> AssertMessage.isFalse(true, AssertFalse));
        e3(AssertFalse, () -> AssertMessage.isFalse(true, AssertFalse, name));
        e1(AssertFalse, () -> AssertMessage.isFalse(null, msg1));
        e2(AssertFalse, () -> AssertMessage.isFalse(null, msg2, name));
        e3(AssertFalse, () -> AssertMessage.isFalse(null));
        e3(AssertFalse, () -> AssertMessage.isFalse(null, AssertFalse));
        e3(AssertFalse, () -> AssertMessage.isFalse(null, AssertFalse, name));
        e1(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{}, msg1));
        e2(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{}, msg2, name));
        e3(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{}));
        e3(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{}, AssertFalse));
        e3(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{}, AssertFalse, name));
        e1(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{ true, false }, msg1));
        e2(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{ true, false }, msg2, name));
        e3(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{ true, false }));
        e3(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{ true, false }, AssertFalse));
        e3(AssertFalse, () -> AssertMessage.isFalse(new boolean[]{ true, false }, AssertFalse, name));

        e1(null, () -> AssertMessage.isFalse(false, msg1));
        e2(null, () -> AssertMessage.isFalse(false, msg2, name));
        e3(null, () -> AssertMessage.isFalse(false));
        e3(null, () -> AssertMessage.isFalse(false, AssertFalse));
        e3(null, () -> AssertMessage.isFalse(false, AssertFalse, name));
        e1(null, () -> AssertMessage.isFalse(new boolean[]{ false, false }, msg1));
        e2(null, () -> AssertMessage.isFalse(new boolean[]{ false, false }, msg2, name));
        e3(null, () -> AssertMessage.isFalse(new boolean[]{ false, false }));
        e3(null, () -> AssertMessage.isFalse(new boolean[]{ false, false }, AssertFalse));
        e3(null, () -> AssertMessage.isFalse(new boolean[]{ false, false }, AssertFalse, name));
    }

    @Test
    public void testIsNull() {
        e1(AssertNull, () -> AssertMessage.isNull("", msg1));
        e2(AssertNull, () -> AssertMessage.isNull("", msg2, name));
        e3(AssertNull, () -> AssertMessage.isNull(""));
        e3(AssertNull, () -> AssertMessage.isNull("", AssertNull));
        e3(AssertNull, () -> AssertMessage.isNull("", AssertNull, name));
        e1(null, () -> AssertMessage.isNull(null, msg1));
        e2(null, () -> AssertMessage.isNull(null, msg2, name));
        e3(null, () -> AssertMessage.isNull(null));
        e3(null, () -> AssertMessage.isNull(null, AssertNull));
        e3(null, () -> AssertMessage.isNull(null, AssertNull, name));
    }

    @Test
    public void testNotNull() {
        e1(AssertNotNull, () -> AssertMessage.notNull(null, msg1));
        e2(AssertNotNull, () -> AssertMessage.notNull(null, msg2, name));
        e3(AssertNotNull, () -> AssertMessage.notNull(null));
        e3(AssertNotNull, () -> AssertMessage.notNull(null, AssertNotNull));
        e3(AssertNotNull, () -> AssertMessage.notNull(null, AssertNotNull, name));
        e1(null, () -> AssertMessage.notNull("", msg1));
        e2(null, () -> AssertMessage.notNull("", msg2, name));
        e3(null, () -> AssertMessage.notNull(""));
        e3(null, () -> AssertMessage.notNull("", AssertNotNull));
        e3(null, () -> AssertMessage.notNull("", AssertNotNull, name));
    }

    @Test
    public void testIsEmpty() {
        e1(AssertEmpty, () -> AssertMessage.isEmpty("1", msg1));
        e2(AssertEmpty, () -> AssertMessage.isEmpty("1", msg2, name));
        e3(AssertEmpty, () -> AssertMessage.isEmpty("1"));
        e3(AssertEmpty, () -> AssertMessage.isEmpty("1", AssertEmpty));
        e3(AssertEmpty, () -> AssertMessage.isEmpty("1", AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty((String) null, msg1));
        e2(null, () -> AssertMessage.isEmpty((String) null, msg2, name));
        e3(null, () -> AssertMessage.isEmpty((String) null));
        e3(null, () -> AssertMessage.isEmpty((String) null, AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty((String) null, AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty("", msg1));
        e2(null, () -> AssertMessage.isEmpty("", msg2, name));
        e3(null, () -> AssertMessage.isEmpty(""));
        e3(null, () -> AssertMessage.isEmpty("", AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty("", AssertEmpty, name));

        e1(AssertEmpty, () -> AssertMessage.isEmpty(singletonList("1"), msg1));
        e2(AssertEmpty, () -> AssertMessage.isEmpty(singletonList("1"), msg2, name));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(singletonList("1")));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(singletonList("1"), AssertEmpty));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(singletonList("1"), AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty((List<?>) null, msg1));
        e2(null, () -> AssertMessage.isEmpty((List<?>) null, msg2, name));
        e3(null, () -> AssertMessage.isEmpty((List<?>) null));
        e3(null, () -> AssertMessage.isEmpty((List<?>) null, AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty((List<?>) null, AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty(emptyList(), msg1));
        e2(null, () -> AssertMessage.isEmpty(emptyList(), msg2, name));
        e3(null, () -> AssertMessage.isEmpty(emptyList()));
        e3(null, () -> AssertMessage.isEmpty(emptyList(), AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty(emptyList(), AssertEmpty, name));

        e1(AssertEmpty, () -> AssertMessage.isEmpty(singletonMap("1", ""), msg1));
        e2(AssertEmpty, () -> AssertMessage.isEmpty(singletonMap("1", ""), msg2, name));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(singletonMap("1", "")));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(singletonMap("1", ""), AssertEmpty));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(singletonMap("1", ""), AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty((Map<?, ?>) null, msg1));
        e2(null, () -> AssertMessage.isEmpty((Map<?, ?>) null, msg2, name));
        e3(null, () -> AssertMessage.isEmpty((Map<?, ?>) null));
        e3(null, () -> AssertMessage.isEmpty((Map<?, ?>) null, AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty((Map<?, ?>) null, AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty(emptyMap(), msg1));
        e2(null, () -> AssertMessage.isEmpty(emptyMap(), msg2, name));
        e3(null, () -> AssertMessage.isEmpty(emptyMap()));
        e3(null, () -> AssertMessage.isEmpty(emptyMap(), AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty(emptyMap(), AssertEmpty, name));

        e1(AssertEmpty, () -> AssertMessage.isEmpty(new Object[]{ "1", "" }, msg1));
        e2(AssertEmpty, () -> AssertMessage.isEmpty(new Object[]{ "1", "" }, msg2, name));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(new Object[]{ "1", "" }));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(new Object[]{ "1", "" }, AssertEmpty));
        e3(AssertEmpty, () -> AssertMessage.isEmpty(new Object[]{ "1", "" }, AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty((Object[]) null, msg1));
        e2(null, () -> AssertMessage.isEmpty((Object[]) null, msg2, name));
        e3(null, () -> AssertMessage.isEmpty((Object[]) null));
        e3(null, () -> AssertMessage.isEmpty((Object[]) null, AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty((Object[]) null, AssertEmpty, name));
        e1(null, () -> AssertMessage.isEmpty(Null.Objects, msg1));
        e2(null, () -> AssertMessage.isEmpty(Null.Objects, msg2, name));
        e3(null, () -> AssertMessage.isEmpty(Null.Objects));
        e3(null, () -> AssertMessage.isEmpty(Null.Objects, AssertEmpty));
        e3(null, () -> AssertMessage.isEmpty(Null.Objects, AssertEmpty, name));
    }

    @Test
    public void testNotEmpty() {
        e1(AssertNotEmpty, () -> AssertMessage.notEmpty((String) null, msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty((String) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((String) null));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((String) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((String) null, AssertNotEmpty, name));
        e1(AssertNotEmpty, () -> AssertMessage.notEmpty("", msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty("", msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(""));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty("", AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty("", AssertNotEmpty, name));
        e1(null, () -> AssertMessage.notEmpty("1", msg1));
        e2(null, () -> AssertMessage.notEmpty("1", msg2, name));
        e3(null, () -> AssertMessage.notEmpty("1"));
        e3(null, () -> AssertMessage.notEmpty("1", AssertNotEmpty));
        e3(null, () -> AssertMessage.notEmpty("1", AssertNotEmpty, name));

        e1(AssertNotEmpty, () -> AssertMessage.notEmpty((List<?>) null, msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty((List<?>) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((List<?>) null));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((List<?>) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((List<?>) null, AssertNotEmpty, name));
        e1(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyList(), msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyList(), msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyList()));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyList(), AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyList(), AssertNotEmpty, name));
        e1(null, () -> AssertMessage.notEmpty(singletonList("1"), msg1));
        e2(null, () -> AssertMessage.notEmpty(singletonList("1"), msg2, name));
        e3(null, () -> AssertMessage.notEmpty(singletonList("1")));
        e3(null, () -> AssertMessage.notEmpty(singletonList("1"), AssertNotEmpty));
        e3(null, () -> AssertMessage.notEmpty(singletonList("1"), AssertNotEmpty, name));

        e1(AssertNotEmpty, () -> AssertMessage.notEmpty((Map<?, ?>) null, msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty((Map<?, ?>) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((Map<?, ?>) null));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((Map<?, ?>) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((Map<?, ?>) null, AssertNotEmpty, name));
        e1(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyMap(), msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyMap(), msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyMap()));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyMap(), AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(emptyMap(), AssertNotEmpty, name));
        e1(null, () -> AssertMessage.notEmpty(singletonMap("1", ""), msg1));
        e2(null, () -> AssertMessage.notEmpty(singletonMap("1", ""), msg2, name));
        e3(null, () -> AssertMessage.notEmpty(singletonMap("1", "")));
        e3(null, () -> AssertMessage.notEmpty(singletonMap("1", ""), AssertNotEmpty));
        e3(null, () -> AssertMessage.notEmpty(singletonMap("1", ""), AssertNotEmpty, name));

        e1(AssertNotEmpty, () -> AssertMessage.notEmpty((Object[]) null, msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty((Object[]) null, msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((Object[]) null));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((Object[]) null, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty((Object[]) null, AssertNotEmpty, name));
        e1(AssertNotEmpty, () -> AssertMessage.notEmpty(Null.Objects, msg1));
        e2(AssertNotEmpty, () -> AssertMessage.notEmpty(Null.Objects, msg2, name));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(Null.Objects));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(Null.Objects, AssertNotEmpty));
        e3(AssertNotEmpty, () -> AssertMessage.notEmpty(Null.Objects, AssertNotEmpty, name));
        e1(null, () -> AssertMessage.notEmpty(new Object[]{ "1" }, msg1));
        e2(null, () -> AssertMessage.notEmpty(new Object[]{ "1" }, msg2, name));
        e3(null, () -> AssertMessage.notEmpty(new Object[]{ "1" }));
        e3(null, () -> AssertMessage.notEmpty(new Object[]{ "1" }, AssertNotEmpty));
        e3(null, () -> AssertMessage.notEmpty(new Object[]{ "1" }, AssertNotEmpty, name));
    }

    @Test
    public void testIsEqual() {
        e1(AssertEqual1, () -> AssertMessage.isEqual(null, "1", msg1));
        e2(AssertEqual1, () -> AssertMessage.isEqual(null, "1", msg2, name));
        e3(AssertEqual1, () -> AssertMessage.isEqual(null, "1"));
        e3(AssertEqual1, () -> AssertMessage.isEqual(null, "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.isEqual(null, "1", AssertEqual1, name));
        e1(AssertEqual1, () -> AssertMessage.isEqual("", "1", msg1));
        e2(AssertEqual1, () -> AssertMessage.isEqual("", "1", msg2, name));
        e3(AssertEqual1, () -> AssertMessage.isEqual("", "1"));
        e3(AssertEqual1, () -> AssertMessage.isEqual("", "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.isEqual("", "1", AssertEqual1, name));
        e1(null, () -> AssertMessage.isEqual("1", "1", msg1));
        e2(null, () -> AssertMessage.isEqual("1", "1", msg2, name));
        e3(null, () -> AssertMessage.isEqual("1", "1"));
        e3(null, () -> AssertMessage.isEqual("1", "1", AssertEqual1));
        e3(null, () -> AssertMessage.isEqual("1", "1", AssertEqual1, name));
    }

    @Test
    public void testNotEqual() {
        e1(AssertNotEqual1, () -> AssertMessage.notEqual("1", "1", msg1));
        e2(AssertNotEqual1, () -> AssertMessage.notEqual("1", "1", msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.notEqual("1", "1"));
        e3(AssertNotEqual1, () -> AssertMessage.notEqual("1", "1", AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.notEqual("1", "1", AssertNotEqual1, name));
        e1(null, () -> AssertMessage.notEqual(null, "1", msg1));
        e2(null, () -> AssertMessage.notEqual(null, "1", msg2, name));
        e3(null, () -> AssertMessage.notEqual(null, "1"));
        e3(null, () -> AssertMessage.notEqual(null, "1", AssertNotEqual1));
        e3(null, () -> AssertMessage.notEqual(null, "1", AssertNotEqual1, name));
        e1(null, () -> AssertMessage.notEqual("", "1", msg1));
        e2(null, () -> AssertMessage.notEqual("", "1", msg2, name));
        e3(null, () -> AssertMessage.notEqual("", "1"));
        e3(null, () -> AssertMessage.notEqual("", "1", AssertNotEqual1));
        e3(null, () -> AssertMessage.notEqual("", "1", AssertNotEqual1, name));
    }

    @Test
    public void testEqObj() {
        e1(AssertEqual1, () -> AssertMessage.eqObj(null, "1", msg1));
        e2(AssertEqual1, () -> AssertMessage.eqObj(null, "1", msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqObj(null, "1"));
        e3(AssertEqual1, () -> AssertMessage.eqObj(null, "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqObj(null, "1", AssertEqual1, name));
        e1(AssertEqual1, () -> AssertMessage.eqObj("", "1", msg1));
        e2(AssertEqual1, () -> AssertMessage.eqObj("", "1", msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqObj("", "1"));
        e3(AssertEqual1, () -> AssertMessage.eqObj("", "1", AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqObj("", "1", AssertEqual1, name));
        e1(null, () -> AssertMessage.eqObj("1", "1", msg1));
        e2(null, () -> AssertMessage.eqObj("1", "1", msg2, name));
        e3(null, () -> AssertMessage.eqObj("1", "1"));
        e3(null, () -> AssertMessage.eqObj("1", "1", AssertEqual1));
        e3(null, () -> AssertMessage.eqObj("1", "1", AssertEqual1, name));
    }

    @Test
    public void testNeObj() {
        e1(AssertNotEqual1, () -> AssertMessage.neObj("1", "1", msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neObj("1", "1", msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neObj("1", "1"));
        e3(AssertNotEqual1, () -> AssertMessage.neObj("1", "1", AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neObj("1", "1", AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertMessage.neObj(null, "1", msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neObj(null, "1", msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neObj(null, "1"));
        e3(AssertNotEqual1, () -> AssertMessage.neObj(null, "1", AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neObj(null, "1", AssertNotEqual1, name));
        e1(null, () -> AssertMessage.neObj("", "1", msg1));
        e2(null, () -> AssertMessage.neObj("", "1", msg2, name));
        e3(null, () -> AssertMessage.neObj("", "1"));
        e3(null, () -> AssertMessage.neObj("", "1", AssertNotEqual1));
        e3(null, () -> AssertMessage.neObj("", "1", AssertNotEqual1, name));
    }

    @Test
    public void testGeObj() {
        e1(AssertGreaterEqual1, () -> AssertMessage.geObj(null, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geObj(null, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geObj(null, 1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geObj(null, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geObj(null, 1, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertMessage.geObj(0, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geObj(0, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geObj(0, 1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geObj(0, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geObj(0, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geObj(1, 1, msg1));
        e2(null, () -> AssertMessage.geObj(1, 1, msg2, name));
        e3(null, () -> AssertMessage.geObj(1, 1));
        e3(null, () -> AssertMessage.geObj(1, 1, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geObj(1, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geObj(2, 1, msg1));
        e2(null, () -> AssertMessage.geObj(2, 1, msg2, name));
        e3(null, () -> AssertMessage.geObj(2, 1));
        e3(null, () -> AssertMessage.geObj(2, 1, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geObj(2, 1, AssertGreaterEqual1, name));
    }

    @Test
    public void testGtObj() {
        e1(AssertGreater1, () -> AssertMessage.gtObj(null, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtObj(null, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtObj(null, 1));
        e3(AssertGreater1, () -> AssertMessage.gtObj(null, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtObj(null, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtObj(0, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtObj(0, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtObj(0, 1));
        e3(AssertGreater1, () -> AssertMessage.gtObj(0, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtObj(0, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtObj(1, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtObj(1, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtObj(1, 1));
        e3(AssertGreater1, () -> AssertMessage.gtObj(1, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtObj(1, 1, AssertGreater1, name));
        e1(null, () -> AssertMessage.gtObj(2, 1, msg1));
        e2(null, () -> AssertMessage.gtObj(2, 1, msg2, name));
        e3(null, () -> AssertMessage.gtObj(2, 1));
        e3(null, () -> AssertMessage.gtObj(2, 1, AssertGreater1));
        e3(null, () -> AssertMessage.gtObj(2, 1, AssertGreater1, name));
    }

    @Test
    public void testLeObj() {
        e1(AssertLessEqual1, () -> AssertMessage.leObj(null, 1, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leObj(null, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leObj(null, 1));
        e3(AssertLessEqual1, () -> AssertMessage.leObj(null, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leObj(null, 1, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertMessage.leObj(2, 1, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leObj(2, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leObj(2, 1));
        e3(AssertLessEqual1, () -> AssertMessage.leObj(2, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leObj(2, 1, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leObj(1, 1, msg1));
        e2(null, () -> AssertMessage.leObj(1, 1, msg2, name));
        e3(null, () -> AssertMessage.leObj(1, 1));
        e3(null, () -> AssertMessage.leObj(1, 1, AssertLessEqual1));
        e3(null, () -> AssertMessage.leObj(1, 1, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leObj(0, 1, msg1));
        e2(null, () -> AssertMessage.leObj(0, 1, msg2, name));
        e3(null, () -> AssertMessage.leObj(0, 1));
        e3(null, () -> AssertMessage.leObj(0, 1, AssertLessEqual1));
        e3(null, () -> AssertMessage.leObj(0, 1, AssertLessEqual1, name));
    }

    @Test
    public void testLtObj() {
        e1(AssertLess1, () -> AssertMessage.ltObj(null, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltObj(null, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltObj(null, 1));
        e3(AssertLess1, () -> AssertMessage.ltObj(null, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltObj(null, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltObj(2, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltObj(2, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltObj(2, 1));
        e3(AssertLess1, () -> AssertMessage.ltObj(2, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltObj(2, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltObj(1, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltObj(1, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltObj(1, 1));
        e3(AssertLess1, () -> AssertMessage.ltObj(1, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltObj(1, 1, AssertLess1, name));
        e1(null, () -> AssertMessage.ltObj(0, 1, msg1));
        e2(null, () -> AssertMessage.ltObj(0, 1, msg2, name));
        e3(null, () -> AssertMessage.ltObj(0, 1));
        e3(null, () -> AssertMessage.ltObj(0, 1, AssertLess1));
        e3(null, () -> AssertMessage.ltObj(0, 1, AssertLess1, name));
    }

    @Test
    public void testEqVal() {
        e1(AssertEqual1, () -> AssertMessage.eqVal(0, 1, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal(0, 1, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal(0, 1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(0, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(0, 1, AssertEqual1, name));
        e1(null, () -> AssertMessage.eqVal(1, 1, msg1));
        e2(null, () -> AssertMessage.eqVal(1, 1, msg2, name));
        e3(null, () -> AssertMessage.eqVal(1, 1));
        e3(null, () -> AssertMessage.eqVal(1, 1, AssertEqual1));
        e3(null, () -> AssertMessage.eqVal(1, 1, AssertEqual1, name));

        e1(AssertEqual1, () -> AssertMessage.eqVal(0L, 1L, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal(0L, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal(0L, 1L));
        e3(AssertEqual1, () -> AssertMessage.eqVal(0L, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(0L, 1L, AssertEqual1, name));
        e1(null, () -> AssertMessage.eqVal(1L, 1L, msg1));
        e2(null, () -> AssertMessage.eqVal(1L, 1L, msg2, name));
        e3(null, () -> AssertMessage.eqVal(1L, 1L));
        e3(null, () -> AssertMessage.eqVal(1L, 1L, AssertEqual1));
        e3(null, () -> AssertMessage.eqVal(1L, 1L, AssertEqual1, name));

        e1(AssertEqual1, () -> AssertMessage.eqVal((int[]) null, 1, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal((int[]) null, 1, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal((int[]) null, 1));
        e3(AssertEqual1, () -> AssertMessage.eqVal((int[]) null, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal((int[]) null, 1, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertMessage.eqVal(Null.Ints, 1, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal(Null.Ints, 1, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal(Null.Ints, 1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(Null.Ints, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(Null.Ints, 1, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertMessage.eqVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal(new int[]{ 0, 1 }, 1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(new int[]{ 0, 1 }, 1, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(new int[]{ 0, 1 }, 1, AssertEqual1, name));
        e1(null, () -> AssertMessage.eqVal(new int[]{ 1, 1 }, 1, msg1));
        e2(null, () -> AssertMessage.eqVal(new int[]{ 1, 1 }, 1, msg2, name));
        e3(null, () -> AssertMessage.eqVal(new int[]{ 1, 1 }, 1));
        e3(null, () -> AssertMessage.eqVal(new int[]{ 1, 1 }, 1, AssertEqual1));
        e3(null, () -> AssertMessage.eqVal(new int[]{ 1, 1 }, 1, AssertEqual1, name));

        e1(AssertEqual1, () -> AssertMessage.eqVal(null, 1L, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal(null, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal(null, 1L));
        e3(AssertEqual1, () -> AssertMessage.eqVal(null, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(null, 1L, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertMessage.eqVal(Null.Longs, 1L, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal(Null.Longs, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal(Null.Longs, 1L));
        e3(AssertEqual1, () -> AssertMessage.eqVal(Null.Longs, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(Null.Longs, 1L, AssertEqual1, name));
        e1(AssertEqual1, () -> AssertMessage.eqVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertEqual1, () -> AssertMessage.eqVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertEqual1, () -> AssertMessage.eqVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertEqual1, () -> AssertMessage.eqVal(new long[]{ 0L, 1L }, 1L, AssertEqual1));
        e3(AssertEqual1, () -> AssertMessage.eqVal(new long[]{ 0L, 1L }, 1L, AssertEqual1, name));
        e1(null, () -> AssertMessage.eqVal(new long[]{ 1L, 1L }, 1L, msg1));
        e2(null, () -> AssertMessage.eqVal(new long[]{ 1L, 1L }, 1L, msg2, name));
        e3(null, () -> AssertMessage.eqVal(new long[]{ 1L, 1L }, 1L));
        e3(null, () -> AssertMessage.eqVal(new long[]{ 1L, 1L }, 1L, AssertEqual1));
        e3(null, () -> AssertMessage.eqVal(new long[]{ 1L, 1L }, 1L, AssertEqual1, name));
    }

    @Test
    public void testNeVal() {
        e1(AssertNotEqual1, () -> AssertMessage.neVal(1, 1, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal(1, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(1, 1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(1, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(1, 1, AssertNotEqual1, name));
        e1(null, () -> AssertMessage.neVal(0, 1, msg1));
        e2(null, () -> AssertMessage.neVal(0, 1, msg2, name));
        e3(null, () -> AssertMessage.neVal(0, 1));
        e3(null, () -> AssertMessage.neVal(0, 1, AssertNotEqual1));
        e3(null, () -> AssertMessage.neVal(0, 1, AssertNotEqual1, name));

        e1(AssertNotEqual1, () -> AssertMessage.neVal(1L, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal(1L, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(1L, 1L));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(1L, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(1L, 1L, AssertNotEqual1, name));
        e1(null, () -> AssertMessage.neVal(0L, 1L, msg1));
        e2(null, () -> AssertMessage.neVal(0L, 1L, msg2, name));
        e3(null, () -> AssertMessage.neVal(0L, 1L));
        e3(null, () -> AssertMessage.neVal(0L, 1L, AssertNotEqual1));
        e3(null, () -> AssertMessage.neVal(0L, 1L, AssertNotEqual1, name));

        e1(AssertNotEqual1, () -> AssertMessage.neVal((int[]) null, 1, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal((int[]) null, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal((int[]) null, 1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal((int[]) null, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal((int[]) null, 1, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertMessage.neVal(Null.Ints, 1, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal(Null.Ints, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(Null.Ints, 1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(Null.Ints, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(Null.Ints, 1, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertMessage.neVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(new int[]{ 0, 1 }, 1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(new int[]{ 0, 1 }, 1, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(new int[]{ 0, 1 }, 1, AssertNotEqual1, name));
        e1(null, () -> AssertMessage.neVal(new int[]{ 1, 1 }, 0, name, msg1));
        e2(null, () -> AssertMessage.neVal(new int[]{ 1, 1 }, 0, name, msg2, name));
        e3(null, () -> AssertMessage.neVal(new int[]{ 1, 1 }, 0, name));
        e3(null, () -> AssertMessage.neVal(new int[]{ 1, 1 }, 0, name, AssertNotEqual1));
        e3(null, () -> AssertMessage.neVal(new int[]{ 1, 1 }, 0, name, AssertNotEqual1, name));

        e1(AssertNotEqual1, () -> AssertMessage.neVal(null, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal(null, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(null, 1L));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(null, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(null, 1L, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertMessage.neVal(Null.Longs, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal(Null.Longs, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(Null.Longs, 1L));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(Null.Longs, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(Null.Longs, 1L, AssertNotEqual1, name));
        e1(AssertNotEqual1, () -> AssertMessage.neVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertNotEqual1, () -> AssertMessage.neVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(new long[]{ 0L, 1L }, 1L, AssertNotEqual1));
        e3(AssertNotEqual1, () -> AssertMessage.neVal(new long[]{ 0L, 1L }, 1L, AssertNotEqual1, name));
        e1(null, () -> AssertMessage.neVal(new long[]{ 1L, 1L }, 0L, msg1));
        e2(null, () -> AssertMessage.neVal(new long[]{ 1L, 1L }, 0L, msg2, name));
        e3(null, () -> AssertMessage.neVal(new long[]{ 1L, 1L }, 0L));
        e3(null, () -> AssertMessage.neVal(new long[]{ 1L, 1L }, 0L, AssertNotEqual1));
        e3(null, () -> AssertMessage.neVal(new long[]{ 1L, 1L }, 0L, AssertNotEqual1, name));
    }

    @Test
    public void testGeVal() {
        e1(AssertGreaterEqual1, () -> AssertMessage.geVal(0, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal(0, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(0, 1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(0, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(0, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geVal(1, 1, msg1));
        e2(null, () -> AssertMessage.geVal(1, 1, msg2, name));
        e3(null, () -> AssertMessage.geVal(1, 1));
        e3(null, () -> AssertMessage.geVal(1, 1, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geVal(1, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geVal(2, 1, msg1));
        e2(null, () -> AssertMessage.geVal(2, 1, msg2, name));
        e3(null, () -> AssertMessage.geVal(2, 1));
        e3(null, () -> AssertMessage.geVal(2, 1, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geVal(2, 1, AssertGreaterEqual1, name));

        e1(AssertGreaterEqual1, () -> AssertMessage.geVal(0L, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal(0L, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(0L, 1L));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(0L, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(0L, 1L, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geVal(1L, 1L, msg1));
        e2(null, () -> AssertMessage.geVal(1L, 1L, msg2, name));
        e3(null, () -> AssertMessage.geVal(1L, 1L));
        e3(null, () -> AssertMessage.geVal(1L, 1L, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geVal(1L, 1L, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geVal(2L, 1L, msg1));
        e2(null, () -> AssertMessage.geVal(2L, 1L, msg2, name));
        e3(null, () -> AssertMessage.geVal(2L, 1L));
        e3(null, () -> AssertMessage.geVal(2L, 1L, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geVal(2L, 1L, AssertGreaterEqual1, name));

        e1(AssertGreaterEqual1, () -> AssertMessage.geVal((int[]) null, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal((int[]) null, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal((int[]) null, 1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal((int[]) null, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal((int[]) null, 1, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Ints, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Ints, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Ints, 1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Ints, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Ints, 1, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertMessage.geVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(new int[]{ 0, 1 }, 1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(new int[]{ 0, 1 }, 1, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(new int[]{ 0, 1 }, 1, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geVal(new int[]{ 1, 0 }, 0, name, msg1));
        e2(null, () -> AssertMessage.geVal(new int[]{ 1, 0 }, 0, name, msg2, name));
        e3(null, () -> AssertMessage.geVal(new int[]{ 1, 0 }, 0, name));
        e3(null, () -> AssertMessage.geVal(new int[]{ 1, 0 }, 0, name, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geVal(new int[]{ 1, 0 }, 0, name, AssertGreaterEqual1, name));

        e1(AssertGreaterEqual1, () -> AssertMessage.geVal(null, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal(null, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(null, 1L));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(null, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(null, 1L, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Longs, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Longs, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Longs, 1L));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Longs, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(Null.Longs, 1L, AssertGreaterEqual1, name));
        e1(AssertGreaterEqual1, () -> AssertMessage.geVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertGreaterEqual1, () -> AssertMessage.geVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(new long[]{ 0L, 1L }, 1L, AssertGreaterEqual1));
        e3(AssertGreaterEqual1, () -> AssertMessage.geVal(new long[]{ 0L, 1L }, 1L, AssertGreaterEqual1, name));
        e1(null, () -> AssertMessage.geVal(new long[]{ 1L, 0L }, 0L, msg1));
        e2(null, () -> AssertMessage.geVal(new long[]{ 1L, 0L }, 0L, msg2, name));
        e3(null, () -> AssertMessage.geVal(new long[]{ 1L, 0L }, 0L));
        e3(null, () -> AssertMessage.geVal(new long[]{ 1L, 0L }, 0L, AssertGreaterEqual1));
        e3(null, () -> AssertMessage.geVal(new long[]{ 1L, 0L }, 0L, AssertGreaterEqual1, name));
    }

    @Test
    public void testGtVal() {
        e1(AssertGreater1, () -> AssertMessage.gtVal(0, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(0, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(0, 1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(0, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(0, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtVal(1, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(1, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(1, 1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(1, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(1, 1, AssertGreater1, name));
        e1(null, () -> AssertMessage.gtVal(2, 1, msg1));
        e2(null, () -> AssertMessage.gtVal(2, 1, msg2, name));
        e3(null, () -> AssertMessage.gtVal(2, 1));
        e3(null, () -> AssertMessage.gtVal(2, 1, AssertGreater1));
        e3(null, () -> AssertMessage.gtVal(2, 1, AssertGreater1, name));

        e1(AssertGreater1, () -> AssertMessage.gtVal(0L, 1L, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(0L, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(0L, 1L));
        e3(AssertGreater1, () -> AssertMessage.gtVal(0L, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(0L, 1L, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtVal(1L, 1L, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(1L, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(1L, 1L));
        e3(AssertGreater1, () -> AssertMessage.gtVal(1L, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(1L, 1L, AssertGreater1, name));
        e1(null, () -> AssertMessage.gtVal(2L, 1L, msg1));
        e2(null, () -> AssertMessage.gtVal(2L, 1L, msg2, name));
        e3(null, () -> AssertMessage.gtVal(2L, 1L));
        e3(null, () -> AssertMessage.gtVal(2L, 1L, AssertGreater1));
        e3(null, () -> AssertMessage.gtVal(2L, 1L, AssertGreater1, name));

        e1(AssertGreater1, () -> AssertMessage.gtVal((int[]) null, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal((int[]) null, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal((int[]) null, 1));
        e3(AssertGreater1, () -> AssertMessage.gtVal((int[]) null, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal((int[]) null, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtVal(Null.Ints, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(Null.Ints, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(Null.Ints, 1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(Null.Ints, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(Null.Ints, 1, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(new int[]{ 0, 1 }, 1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(new int[]{ 0, 1 }, 1, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(new int[]{ 0, 1 }, 1, AssertGreater1, name));
        e1(null, () -> AssertMessage.gtVal(new int[]{ 1, 1 }, 0, name, msg1));
        e2(null, () -> AssertMessage.gtVal(new int[]{ 1, 1 }, 0, name, msg2, name));
        e3(null, () -> AssertMessage.gtVal(new int[]{ 1, 1 }, 0, name));
        e3(null, () -> AssertMessage.gtVal(new int[]{ 1, 1 }, 0, name, AssertGreater1));
        e3(null, () -> AssertMessage.gtVal(new int[]{ 1, 1 }, 0, name, AssertGreater1, name));

        e1(AssertGreater1, () -> AssertMessage.gtVal(null, 1L, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(null, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(null, 1L));
        e3(AssertGreater1, () -> AssertMessage.gtVal(null, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(null, 1L, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtVal(Null.Longs, 1L, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(Null.Longs, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(Null.Longs, 1L));
        e3(AssertGreater1, () -> AssertMessage.gtVal(Null.Longs, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(Null.Longs, 1L, AssertGreater1, name));
        e1(AssertGreater1, () -> AssertMessage.gtVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertGreater1, () -> AssertMessage.gtVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertGreater1, () -> AssertMessage.gtVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertGreater1, () -> AssertMessage.gtVal(new long[]{ 0L, 1L }, 1L, AssertGreater1));
        e3(AssertGreater1, () -> AssertMessage.gtVal(new long[]{ 0L, 1L }, 1L, AssertGreater1, name));
        e1(null, () -> AssertMessage.gtVal(new long[]{ 1L, 1L }, 0L, msg1));
        e2(null, () -> AssertMessage.gtVal(new long[]{ 1L, 1L }, 0L, msg2, name));
        e3(null, () -> AssertMessage.gtVal(new long[]{ 1L, 1L }, 0L));
        e3(null, () -> AssertMessage.gtVal(new long[]{ 1L, 1L }, 0L, AssertGreater1));
        e3(null, () -> AssertMessage.gtVal(new long[]{ 1L, 1L }, 0L, AssertGreater1, name));
    }

    @Test
    public void testLeVal() {
        e1(AssertLessEqual1, () -> AssertMessage.leVal(2, 1, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal(2, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(2, 1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(2, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(2, 1, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leVal(1, 1, msg1));
        e2(null, () -> AssertMessage.leVal(1, 1, msg2, name));
        e3(null, () -> AssertMessage.leVal(1, 1));
        e3(null, () -> AssertMessage.leVal(1, 1, AssertLessEqual1));
        e3(null, () -> AssertMessage.leVal(1, 1, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leVal(0, 1, msg1));
        e2(null, () -> AssertMessage.leVal(0, 1, msg2, name));
        e3(null, () -> AssertMessage.leVal(0, 1));
        e3(null, () -> AssertMessage.leVal(0, 1, AssertLessEqual1));
        e3(null, () -> AssertMessage.leVal(0, 1, AssertLessEqual1, name));

        e1(AssertLessEqual1, () -> AssertMessage.leVal(2L, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal(2L, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(2L, 1L));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(2L, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(2L, 1L, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leVal(1L, 1L, msg1));
        e2(null, () -> AssertMessage.leVal(1L, 1L, msg2, name));
        e3(null, () -> AssertMessage.leVal(1L, 1L));
        e3(null, () -> AssertMessage.leVal(1L, 1L, AssertLessEqual1));
        e3(null, () -> AssertMessage.leVal(1L, 1L, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leVal(0L, 1L, msg1));
        e2(null, () -> AssertMessage.leVal(0L, 1L, msg2, name));
        e3(null, () -> AssertMessage.leVal(0L, 1L));
        e3(null, () -> AssertMessage.leVal(0L, 1L, AssertLessEqual1));
        e3(null, () -> AssertMessage.leVal(0L, 1L, AssertLessEqual1, name));

        e1(AssertLessEqual1, () -> AssertMessage.leVal((int[]) null, 1, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal((int[]) null, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal((int[]) null, 1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal((int[]) null, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal((int[]) null, 1, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertMessage.leVal(Null.Ints, 1, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal(Null.Ints, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(Null.Ints, 1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(Null.Ints, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(Null.Ints, 1, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertMessage.leVal(new int[]{ 2, 1 }, 1, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal(new int[]{ 2, 1 }, 1, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(new int[]{ 2, 1 }, 1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(new int[]{ 2, 1 }, 1, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(new int[]{ 2, 1 }, 1, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leVal(new int[]{ 1, 0 }, 1, msg1));
        e2(null, () -> AssertMessage.leVal(new int[]{ 1, 0 }, 1, msg2, name));
        e3(null, () -> AssertMessage.leVal(new int[]{ 1, 0 }, 1));
        e3(null, () -> AssertMessage.leVal(new int[]{ 1, 0 }, 1, AssertLessEqual1));
        e3(null, () -> AssertMessage.leVal(new int[]{ 1, 0 }, 1, AssertLessEqual1, name));

        e1(AssertLessEqual1, () -> AssertMessage.leVal(null, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal(null, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(null, 1L));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(null, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(null, 1L, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertMessage.leVal(Null.Longs, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal(Null.Longs, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(Null.Longs, 1L));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(Null.Longs, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(Null.Longs, 1L, AssertLessEqual1, name));
        e1(AssertLessEqual1, () -> AssertMessage.leVal(new long[]{ 2L, 1L }, 1L, msg1));
        e2(AssertLessEqual1, () -> AssertMessage.leVal(new long[]{ 2L, 1L }, 1L, msg2, name));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(new long[]{ 2L, 1L }, 1L));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(new long[]{ 2L, 1L }, 1L, AssertLessEqual1));
        e3(AssertLessEqual1, () -> AssertMessage.leVal(new long[]{ 2L, 1L }, 1L, AssertLessEqual1, name));
        e1(null, () -> AssertMessage.leVal(new long[]{ 1L, 0L }, 1L, msg1));
        e2(null, () -> AssertMessage.leVal(new long[]{ 1L, 0L }, 1L, msg2, name));
        e3(null, () -> AssertMessage.leVal(new long[]{ 1L, 0L }, 1L));
        e3(null, () -> AssertMessage.leVal(new long[]{ 1L, 0L }, 1L, AssertLessEqual1));
        e3(null, () -> AssertMessage.leVal(new long[]{ 1L, 0L }, 1L, AssertLessEqual1, name));
    }

    @Test
    public void testLtVal() {
        e1(AssertLess1, () -> AssertMessage.ltVal(2, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(2, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(2, 1));
        e3(AssertLess1, () -> AssertMessage.ltVal(2, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(2, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltVal(1, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(1, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(1, 1));
        e3(AssertLess1, () -> AssertMessage.ltVal(1, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(1, 1, AssertLess1, name));
        e1(null, () -> AssertMessage.ltVal(0, 1, msg1));
        e2(null, () -> AssertMessage.ltVal(0, 1, msg2, name));
        e3(null, () -> AssertMessage.ltVal(0, 1));
        e3(null, () -> AssertMessage.ltVal(0, 1, AssertLess1));
        e3(null, () -> AssertMessage.ltVal(0, 1, AssertLess1, name));

        e1(AssertLess1, () -> AssertMessage.ltVal(2L, 1L, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(2L, 1L, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(2L, 1L));
        e3(AssertLess1, () -> AssertMessage.ltVal(2L, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(2L, 1L, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltVal(1L, 1L, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(1L, 1L, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(1L, 1L));
        e3(AssertLess1, () -> AssertMessage.ltVal(1L, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(1L, 1L, AssertLess1, name));
        e1(null, () -> AssertMessage.ltVal(0L, 1L, msg1));
        e2(null, () -> AssertMessage.ltVal(0L, 1L, msg2, name));
        e3(null, () -> AssertMessage.ltVal(0L, 1L));
        e3(null, () -> AssertMessage.ltVal(0L, 1L, AssertLess1));
        e3(null, () -> AssertMessage.ltVal(0L, 1L, AssertLess1, name));

        e1(AssertLess1, () -> AssertMessage.ltVal((int[]) null, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal((int[]) null, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal((int[]) null, 1));
        e3(AssertLess1, () -> AssertMessage.ltVal((int[]) null, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal((int[]) null, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltVal(Null.Ints, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(Null.Ints, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(Null.Ints, 1));
        e3(AssertLess1, () -> AssertMessage.ltVal(Null.Ints, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(Null.Ints, 1, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltVal(new int[]{ 0, 1 }, 1, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(new int[]{ 0, 1 }, 1, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(new int[]{ 0, 1 }, 1));
        e3(AssertLess1, () -> AssertMessage.ltVal(new int[]{ 0, 1 }, 1, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(new int[]{ 0, 1 }, 1, AssertLess1, name));
        e1(null, () -> AssertMessage.ltVal(new int[]{ 1, 1 }, 2, name, msg1));
        e2(null, () -> AssertMessage.ltVal(new int[]{ 1, 1 }, 2, name, msg2, name));
        e3(null, () -> AssertMessage.ltVal(new int[]{ 1, 1 }, 2, name));
        e3(null, () -> AssertMessage.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess1));
        e3(null, () -> AssertMessage.ltVal(new int[]{ 1, 1 }, 2, name, AssertLess1, name));

        e1(AssertLess1, () -> AssertMessage.ltVal(null, 1L, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(null, 1L, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(null, 1L));
        e3(AssertLess1, () -> AssertMessage.ltVal(null, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(null, 1L, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltVal(Null.Longs, 1L, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(Null.Longs, 1L, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(Null.Longs, 1L));
        e3(AssertLess1, () -> AssertMessage.ltVal(Null.Longs, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(Null.Longs, 1L, AssertLess1, name));
        e1(AssertLess1, () -> AssertMessage.ltVal(new long[]{ 0L, 1L }, 1L, msg1));
        e2(AssertLess1, () -> AssertMessage.ltVal(new long[]{ 0L, 1L }, 1L, msg2, name));
        e3(AssertLess1, () -> AssertMessage.ltVal(new long[]{ 0L, 1L }, 1L));
        e3(AssertLess1, () -> AssertMessage.ltVal(new long[]{ 0L, 1L }, 1L, AssertLess1));
        e3(AssertLess1, () -> AssertMessage.ltVal(new long[]{ 0L, 1L }, 1L, AssertLess1, name));
        e1(null, () -> AssertMessage.ltVal(new long[]{ 1L, 1L }, 2L, msg1));
        e2(null, () -> AssertMessage.ltVal(new long[]{ 1L, 1L }, 2L, msg2, name));
        e3(null, () -> AssertMessage.ltVal(new long[]{ 1L, 1L }, 2L));
        e3(null, () -> AssertMessage.ltVal(new long[]{ 1L, 1L }, 2L, AssertLess1));
        e3(null, () -> AssertMessage.ltVal(new long[]{ 1L, 1L }, 2L, AssertLess1, name));
    }
}