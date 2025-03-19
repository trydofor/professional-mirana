package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pro.fessional.mirana.i18n.AssertErrorEnum;
import pro.fessional.mirana.pain.CrudException;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author trydofor
 * @since 2025-02-01
 */
class AssertCrudTest {

    private final int[] ints1 = { 1 };
    private final long[] longs1 = { 1L };

    private void testCrudException(Executable action) {
        CrudException ce = assertThrows(CrudException.class, action);
        String str = ce.toString(Locale.US);
        assertFalse(str.contains("{0}"), str);
    }

    private void testStateException(Executable action) {
        assertThrows(IllegalStateException.class, action);
    }

    @Test
    void assertEq() {
        assertEquals(1, AssertCrud.affectEq(1, 1, "err"));
        assertEquals(1, AssertCrud.affectEq(1, 1, "err", 1));
        assertSame(ints1, AssertCrud.affectEq(ints1, 1, "err"));
        assertSame(ints1, AssertCrud.affectEq(ints1, 1, "err", 1));
        assertEquals(1, AssertCrud.affectEq(1, 1, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createEq(1, 1));
        assertEquals(1, AssertCrud.selectEq(1, 1));
        assertEquals(1, AssertCrud.updateEq(1, 1));
        assertEquals(1, AssertCrud.deleteEq(1, 1));
        assertSame(ints1, AssertCrud.affectEq(ints1, 1, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createEq(ints1, 1));
        assertSame(ints1, AssertCrud.selectEq(ints1, 1));
        assertSame(ints1, AssertCrud.updateEq(ints1, 1));
        assertSame(ints1, AssertCrud.deleteEq(ints1, 1));

        testStateException(() -> AssertCrud.affectEq(1, 0, "err"));
        testStateException(() -> AssertCrud.affectEq(1, 0, "err", 1));
        testStateException(() -> AssertCrud.affectEq(ints1, 0, "err"));
        testStateException(() -> AssertCrud.affectEq(ints1, 0, "err", 1));
        testCrudException(() -> AssertCrud.affectEq(1, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createEq(1, 0));
        testCrudException(() -> AssertCrud.selectEq(1, 0));
        testCrudException(() -> AssertCrud.updateEq(1, 0));
        testCrudException(() -> AssertCrud.deleteEq(1, 0));
        testCrudException(() -> AssertCrud.affectEq(ints1, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createEq(ints1, 0));
        testCrudException(() -> AssertCrud.selectEq(ints1, 0));
        testCrudException(() -> AssertCrud.updateEq(ints1, 0));
        testCrudException(() -> AssertCrud.deleteEq(ints1, 0));
    }

    @Test
    void assertNe() {
        assertEquals(1, AssertCrud.affectNe(1, 0, "err"));
        assertEquals(1, AssertCrud.affectNe(1, 0, "err", 1));
        assertSame(ints1, AssertCrud.affectNe(ints1, 0, "err"));
        assertSame(ints1, AssertCrud.affectNe(ints1, 0, "err", 1));
        assertEquals(1, AssertCrud.affectNe(1, 0, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createNe(1, 0));
        assertEquals(1, AssertCrud.selectNe(1, 0));
        assertEquals(1, AssertCrud.updateNe(1, 0));
        assertEquals(1, AssertCrud.deleteNe(1, 0));
        assertSame(ints1, AssertCrud.affectNe(ints1, 0, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createNe(ints1, 0));
        assertSame(ints1, AssertCrud.selectNe(ints1, 0));
        assertSame(ints1, AssertCrud.updateNe(ints1, 0));
        assertSame(ints1, AssertCrud.deleteNe(ints1, 0));

        testStateException(() -> AssertCrud.affectNe(1, 1, "err"));
        testStateException(() -> AssertCrud.affectNe(1, 1, "err", 1));
        testStateException(() -> AssertCrud.affectNe(ints1, 1, "err"));
        testStateException(() -> AssertCrud.affectNe(ints1, 1, "err", 1));
        testCrudException(() -> AssertCrud.affectNe(1, 1, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createNe(1, 1));
        testCrudException(() -> AssertCrud.selectNe(1, 1));
        testCrudException(() -> AssertCrud.updateNe(1, 1));
        testCrudException(() -> AssertCrud.deleteNe(1, 1));
        testCrudException(() -> AssertCrud.affectNe(ints1, 1, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createNe(ints1, 1));
        testCrudException(() -> AssertCrud.selectNe(ints1, 1));
        testCrudException(() -> AssertCrud.updateNe(ints1, 1));
        testCrudException(() -> AssertCrud.deleteNe(ints1, 1));
    }
    
    @Test
    void assertGt() {
        assertEquals(1, AssertCrud.affectGt(1, 0, "err"));
        assertEquals(1, AssertCrud.affectGt(1, 0, "err", 1));
        assertSame(ints1, AssertCrud.affectGt(ints1, 0, "err"));
        assertSame(ints1, AssertCrud.affectGt(ints1, 0, "err", 1));
        assertEquals(1, AssertCrud.affectGt(1, 0, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createGt(1, 0));
        assertEquals(1, AssertCrud.selectGt(1, 0));
        assertEquals(1, AssertCrud.updateGt(1, 0));
        assertEquals(1, AssertCrud.deleteGt(1, 0));
        assertSame(ints1, AssertCrud.affectGt(ints1, 0, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createGt(ints1, 0));
        assertSame(ints1, AssertCrud.selectGt(ints1, 0));
        assertSame(ints1, AssertCrud.updateGt(ints1, 0));
        assertSame(ints1, AssertCrud.deleteGt(ints1, 0));

        testStateException(() -> AssertCrud.affectGt(1, 1, "err"));
        testStateException(() -> AssertCrud.affectGt(1, 1, "err", 1));
        testStateException(() -> AssertCrud.affectGt(ints1, 1, "err"));
        testStateException(() -> AssertCrud.affectGt(ints1, 1, "err", 1));
        testCrudException(() -> AssertCrud.affectGt(1, 1, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGt(1, 1));
        testCrudException(() -> AssertCrud.selectGt(1, 1));
        testCrudException(() -> AssertCrud.updateGt(1, 1));
        testCrudException(() -> AssertCrud.deleteGt(1, 1));
        testCrudException(() -> AssertCrud.affectGt(ints1, 1, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGt(ints1, 1));
        testCrudException(() -> AssertCrud.selectGt(ints1, 1));
        testCrudException(() -> AssertCrud.updateGt(ints1, 1));
    }
    
    @Test
    void assertGe() {
        assertEquals(1, AssertCrud.affectGe(1, 0, "err"));
        assertEquals(1, AssertCrud.affectGe(1, 0, "err", 1));
        assertSame(ints1, AssertCrud.affectGe(ints1, 0, "err"));
        assertSame(ints1, AssertCrud.affectGe(ints1, 0, "err", 1));
        assertEquals(1, AssertCrud.affectGe(1, 0, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createGe(1, 0));
        assertEquals(1, AssertCrud.selectGe(1, 0));
        assertEquals(1, AssertCrud.updateGe(1, 0));
        assertEquals(1, AssertCrud.deleteGe(1, 0));
        assertSame(ints1, AssertCrud.affectGe(ints1, 0, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createGe(ints1, 0));
        assertSame(ints1, AssertCrud.selectGe(ints1, 0));
        assertSame(ints1, AssertCrud.updateGe(ints1, 0));
        assertSame(ints1, AssertCrud.deleteGe(ints1, 0));
        assertEquals(1, AssertCrud.affectGe(1, 1, "err"));
        assertEquals(1, AssertCrud.affectGe(1, 1, "err", 1));
        assertSame(ints1, AssertCrud.affectGe(ints1, 1, "err"));
        assertSame(ints1, AssertCrud.affectGe(ints1, 1, "err", 1));
        assertEquals(1, AssertCrud.affectGe(1, 1, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createGe(1, 1));
        assertEquals(1, AssertCrud.selectGe(1, 1));
        assertEquals(1, AssertCrud.updateGe(1, 1));
        assertEquals(1, AssertCrud.deleteGe(1, 1));
        assertSame(ints1, AssertCrud.affectGe(ints1, 1, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createGe(ints1, 1));
        assertSame(ints1, AssertCrud.selectGe(ints1, 1));
        assertSame(ints1, AssertCrud.updateGe(ints1, 1));
        assertSame(ints1, AssertCrud.deleteGe(ints1, 1));

        testStateException(() -> AssertCrud.affectGe(1, 2, "err"));
        testStateException(() -> AssertCrud.affectGe(1, 2, "err", 1));
        testStateException(() -> AssertCrud.affectGe(ints1, 2, "err"));
        testStateException(() -> AssertCrud.affectGe(ints1, 2, "err", 1));
        testCrudException(() -> AssertCrud.affectGe(1, 2, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGe(1, 2));
        testCrudException(() -> AssertCrud.selectGe(1, 2));
        testCrudException(() -> AssertCrud.updateGe(1, 2));
        testCrudException(() -> AssertCrud.deleteGe(1, 2));
        testCrudException(() -> AssertCrud.affectGe(ints1, 2, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGe(ints1, 2));
        testCrudException(() -> AssertCrud.selectGe(ints1, 2));
        testCrudException(() -> AssertCrud.updateGe(ints1, 2));
    }    
    
    @Test
    void assertLt() {
        assertEquals(1, AssertCrud.affectLt(1, 2, "err"));
        assertEquals(1, AssertCrud.affectLt(1, 2, "err", 1));
        assertSame(ints1, AssertCrud.affectLt(ints1, 2, "err"));
        assertSame(ints1, AssertCrud.affectLt(ints1, 2, "err", 1));
        assertEquals(1, AssertCrud.affectLt(1, 2, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createLt(1, 2));
        assertEquals(1, AssertCrud.selectLt(1, 2));
        assertEquals(1, AssertCrud.updateLt(1, 2));
        assertEquals(1, AssertCrud.deleteLt(1, 2));
        assertSame(ints1, AssertCrud.affectLt(ints1, 2, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createLt(ints1, 2));
        assertSame(ints1, AssertCrud.selectLt(ints1, 2));
        assertSame(ints1, AssertCrud.updateLt(ints1, 2));
        assertSame(ints1, AssertCrud.deleteLt(ints1, 2));

        testStateException(() -> AssertCrud.affectLt(1, 1, "err"));
        testStateException(() -> AssertCrud.affectLt(1, 1, "err", 1));
        testStateException(() -> AssertCrud.affectLt(ints1, 1, "err"));
        testStateException(() -> AssertCrud.affectLt(ints1, 1, "err", 1));
        testCrudException(() -> AssertCrud.affectLt(1, 1, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLt(1, 1));
        testCrudException(() -> AssertCrud.selectLt(1, 1));
        testCrudException(() -> AssertCrud.updateLt(1, 1));
        testCrudException(() -> AssertCrud.deleteLt(1, 1));
        testCrudException(() -> AssertCrud.affectLt(ints1, 1, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLt(ints1, 1));
        testCrudException(() -> AssertCrud.selectLt(ints1, 1));
        testCrudException(() -> AssertCrud.updateLt(ints1, 1));
    }
    
    @Test
    void assertLe() {
        assertEquals(1, AssertCrud.affectLe(1, 2, "err"));
        assertEquals(1, AssertCrud.affectLe(1, 2, "err", 1));
        assertSame(ints1, AssertCrud.affectLe(ints1, 2, "err"));
        assertSame(ints1, AssertCrud.affectLe(ints1, 2, "err", 1));
        assertEquals(1, AssertCrud.affectLe(1, 2, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createLe(1, 2));
        assertEquals(1, AssertCrud.selectLe(1, 2));
        assertEquals(1, AssertCrud.updateLe(1, 2));
        assertEquals(1, AssertCrud.deleteLe(1, 2));
        assertSame(ints1, AssertCrud.affectLe(ints1, 2, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createLe(ints1, 2));
        assertSame(ints1, AssertCrud.selectLe(ints1, 2));
        assertSame(ints1, AssertCrud.updateLe(ints1, 2));
        assertSame(ints1, AssertCrud.deleteLe(ints1, 2));
        assertEquals(1, AssertCrud.affectLe(1, 1, "err"));
        assertEquals(1, AssertCrud.affectLe(1, 1, "err", 1));
        assertSame(ints1, AssertCrud.affectLe(ints1, 1, "err"));
        assertSame(ints1, AssertCrud.affectLe(ints1, 1, "err", 1));
        assertEquals(1, AssertCrud.affectLe(1, 1, AssertErrorEnum.AssertTrue));
        assertEquals(1, AssertCrud.createLe(1, 1));
        assertEquals(1, AssertCrud.selectLe(1, 1));
        assertEquals(1, AssertCrud.updateLe(1, 1));
        assertEquals(1, AssertCrud.deleteLe(1, 1));
        assertSame(ints1, AssertCrud.affectLe(ints1, 1, AssertErrorEnum.AssertTrue));
        assertSame(ints1, AssertCrud.createLe(ints1, 1));
        assertSame(ints1, AssertCrud.selectLe(ints1, 1));
        assertSame(ints1, AssertCrud.updateLe(ints1, 1));
        assertSame(ints1, AssertCrud.deleteLe(ints1, 1));

        testStateException(() -> AssertCrud.affectLe(1, 0, "err"));
        testStateException(() -> AssertCrud.affectLe(1, 0, "err", 1));
        testStateException(() -> AssertCrud.affectLe(ints1, 0, "err"));
        testStateException(() -> AssertCrud.affectLe(ints1, 0, "err", 1));
        testCrudException(() -> AssertCrud.affectLe(1, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLe(1, 0));
        testCrudException(() -> AssertCrud.selectLe(1, 0));
        testCrudException(() -> AssertCrud.updateLe(1, 0));
        testCrudException(() -> AssertCrud.deleteLe(1, 0));
        testCrudException(() -> AssertCrud.affectLe(ints1, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLe(ints1, 0));
    }
    
    @Test
    void assertEq1L() {
        assertEquals(1L, AssertCrud.affectEq(1L, 1L, "err"));
        assertEquals(1L, AssertCrud.affectEq(1L, 1L, "err", 1L));
        assertSame(longs1, AssertCrud.affectEq(longs1, 1L, "err"));
        assertSame(longs1, AssertCrud.affectEq(longs1, 1L, "err", 1L));
        assertEquals(1L, AssertCrud.affectEq(1L, 1L, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createEq(1L, 1L));
        assertEquals(1L, AssertCrud.selectEq(1L, 1L));
        assertEquals(1L, AssertCrud.updateEq(1L, 1L));
        assertEquals(1L, AssertCrud.deleteEq(1L, 1L));
        assertSame(longs1, AssertCrud.affectEq(longs1, 1L, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createEq(longs1, 1L));
        assertSame(longs1, AssertCrud.selectEq(longs1, 1L));
        assertSame(longs1, AssertCrud.updateEq(longs1, 1L));
        assertSame(longs1, AssertCrud.deleteEq(longs1, 1L));

        testStateException(() -> AssertCrud.affectEq(1L, 0, "err"));
        testStateException(() -> AssertCrud.affectEq(1L, 0, "err", 1L));
        testStateException(() -> AssertCrud.affectEq(longs1, 0, "err"));
        testStateException(() -> AssertCrud.affectEq(longs1, 0, "err", 1L));
        testCrudException(() -> AssertCrud.affectEq(1L, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createEq(1L, 0));
        testCrudException(() -> AssertCrud.selectEq(1L, 0));
        testCrudException(() -> AssertCrud.updateEq(1L, 0));
        testCrudException(() -> AssertCrud.deleteEq(1L, 0));
        testCrudException(() -> AssertCrud.affectEq(longs1, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createEq(longs1, 0));
        testCrudException(() -> AssertCrud.selectEq(longs1, 0));
        testCrudException(() -> AssertCrud.updateEq(longs1, 0));
        testCrudException(() -> AssertCrud.deleteEq(longs1, 0));
    }

    @Test
    void assertNe1L() {
        assertEquals(1L, AssertCrud.affectNe(1L, 0, "err"));
        assertEquals(1L, AssertCrud.affectNe(1L, 0, "err", 1L));
        assertSame(longs1, AssertCrud.affectNe(longs1, 0, "err"));
        assertSame(longs1, AssertCrud.affectNe(longs1, 0, "err", 1L));
        assertEquals(1L, AssertCrud.affectNe(1L, 0, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createNe(1L, 0));
        assertEquals(1L, AssertCrud.selectNe(1L, 0));
        assertEquals(1L, AssertCrud.updateNe(1L, 0));
        assertEquals(1L, AssertCrud.deleteNe(1L, 0));
        assertSame(longs1, AssertCrud.affectNe(longs1, 0, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createNe(longs1, 0));
        assertSame(longs1, AssertCrud.selectNe(longs1, 0));
        assertSame(longs1, AssertCrud.updateNe(longs1, 0));
        assertSame(longs1, AssertCrud.deleteNe(longs1, 0));

        testStateException(() -> AssertCrud.affectNe(1L, 1L, "err"));
        testStateException(() -> AssertCrud.affectNe(1L, 1L, "err", 1L));
        testStateException(() -> AssertCrud.affectNe(longs1, 1L, "err"));
        testStateException(() -> AssertCrud.affectNe(longs1, 1L, "err", 1L));
        testCrudException(() -> AssertCrud.affectNe(1L, 1L, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createNe(1L, 1L));
        testCrudException(() -> AssertCrud.selectNe(1L, 1L));
        testCrudException(() -> AssertCrud.updateNe(1L, 1L));
        testCrudException(() -> AssertCrud.deleteNe(1L, 1L));
        testCrudException(() -> AssertCrud.affectNe(longs1, 1L, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createNe(longs1, 1L));
        testCrudException(() -> AssertCrud.selectNe(longs1, 1L));
        testCrudException(() -> AssertCrud.updateNe(longs1, 1L));
        testCrudException(() -> AssertCrud.deleteNe(longs1, 1L));
    }
    
    @Test
    void assertGt1L() {
        assertEquals(1L, AssertCrud.affectGt(1L, 0, "err"));
        assertEquals(1L, AssertCrud.affectGt(1L, 0, "err", 1L));
        assertSame(longs1, AssertCrud.affectGt(longs1, 0, "err"));
        assertSame(longs1, AssertCrud.affectGt(longs1, 0, "err", 1L));
        assertEquals(1L, AssertCrud.affectGt(1L, 0, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createGt(1L, 0));
        assertEquals(1L, AssertCrud.selectGt(1L, 0));
        assertEquals(1L, AssertCrud.updateGt(1L, 0));
        assertEquals(1L, AssertCrud.deleteGt(1L, 0));
        assertSame(longs1, AssertCrud.affectGt(longs1, 0, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createGt(longs1, 0));
        assertSame(longs1, AssertCrud.selectGt(longs1, 0));
        assertSame(longs1, AssertCrud.updateGt(longs1, 0));
        assertSame(longs1, AssertCrud.deleteGt(longs1, 0));

        testStateException(() -> AssertCrud.affectGt(1L, 1L, "err"));
        testStateException(() -> AssertCrud.affectGt(1L, 1L, "err", 1L));
        testStateException(() -> AssertCrud.affectGt(longs1, 1L, "err"));
        testStateException(() -> AssertCrud.affectGt(longs1, 1L, "err", 1L));
        testCrudException(() -> AssertCrud.affectGt(1L, 1L, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGt(1L, 1L));
        testCrudException(() -> AssertCrud.selectGt(1L, 1L));
        testCrudException(() -> AssertCrud.updateGt(1L, 1L));
        testCrudException(() -> AssertCrud.deleteGt(1L, 1L));
        testCrudException(() -> AssertCrud.affectGt(longs1, 1L, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGt(longs1, 1L));
        testCrudException(() -> AssertCrud.selectGt(longs1, 1L));
        testCrudException(() -> AssertCrud.updateGt(longs1, 1L));
    }
    
    @Test
    void assertGe1L() {
        assertEquals(1L, AssertCrud.affectGe(1L, 0, "err"));
        assertEquals(1L, AssertCrud.affectGe(1L, 0, "err", 1L));
        assertSame(longs1, AssertCrud.affectGe(longs1, 0, "err"));
        assertSame(longs1, AssertCrud.affectGe(longs1, 0, "err", 1L));
        assertEquals(1L, AssertCrud.affectGe(1L, 0, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createGe(1L, 0));
        assertEquals(1L, AssertCrud.selectGe(1L, 0));
        assertEquals(1L, AssertCrud.updateGe(1L, 0));
        assertEquals(1L, AssertCrud.deleteGe(1L, 0));
        assertSame(longs1, AssertCrud.affectGe(longs1, 0, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createGe(longs1, 0));
        assertSame(longs1, AssertCrud.selectGe(longs1, 0));
        assertSame(longs1, AssertCrud.updateGe(longs1, 0));
        assertSame(longs1, AssertCrud.deleteGe(longs1, 0));
        assertEquals(1L, AssertCrud.affectGe(1L, 1L, "err"));
        assertEquals(1L, AssertCrud.affectGe(1L, 1L, "err", 1L));
        assertSame(longs1, AssertCrud.affectGe(longs1, 1L, "err"));
        assertSame(longs1, AssertCrud.affectGe(longs1, 1L, "err", 1L));
        assertEquals(1L, AssertCrud.affectGe(1L, 1L, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createGe(1L, 1L));
        assertEquals(1L, AssertCrud.selectGe(1L, 1L));
        assertEquals(1L, AssertCrud.updateGe(1L, 1L));
        assertEquals(1L, AssertCrud.deleteGe(1L, 1L));
        assertSame(longs1, AssertCrud.affectGe(longs1, 1L, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createGe(longs1, 1L));
        assertSame(longs1, AssertCrud.selectGe(longs1, 1L));
        assertSame(longs1, AssertCrud.updateGe(longs1, 1L));
        assertSame(longs1, AssertCrud.deleteGe(longs1, 1L));

        testStateException(() -> AssertCrud.affectGe(1L, 2, "err"));
        testStateException(() -> AssertCrud.affectGe(1L, 2, "err", 1L));
        testStateException(() -> AssertCrud.affectGe(longs1, 2, "err"));
        testStateException(() -> AssertCrud.affectGe(longs1, 2, "err", 1L));
        testCrudException(() -> AssertCrud.affectGe(1L, 2, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGe(1L, 2));
        testCrudException(() -> AssertCrud.selectGe(1L, 2));
        testCrudException(() -> AssertCrud.updateGe(1L, 2));
        testCrudException(() -> AssertCrud.deleteGe(1L, 2));
        testCrudException(() -> AssertCrud.affectGe(longs1, 2, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createGe(longs1, 2));
        testCrudException(() -> AssertCrud.selectGe(longs1, 2));
        testCrudException(() -> AssertCrud.updateGe(longs1, 2));
    }    
    
    @Test
    void assertLt1L() {
        assertEquals(1L, AssertCrud.affectLt(1L, 2, "err"));
        assertEquals(1L, AssertCrud.affectLt(1L, 2, "err", 1L));
        assertSame(longs1, AssertCrud.affectLt(longs1, 2, "err"));
        assertSame(longs1, AssertCrud.affectLt(longs1, 2, "err", 1L));
        assertEquals(1L, AssertCrud.affectLt(1L, 2, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createLt(1L, 2));
        assertEquals(1L, AssertCrud.selectLt(1L, 2));
        assertEquals(1L, AssertCrud.updateLt(1L, 2));
        assertEquals(1L, AssertCrud.deleteLt(1L, 2));
        assertSame(longs1, AssertCrud.affectLt(longs1, 2, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createLt(longs1, 2));
        assertSame(longs1, AssertCrud.selectLt(longs1, 2));
        assertSame(longs1, AssertCrud.updateLt(longs1, 2));
        assertSame(longs1, AssertCrud.deleteLt(longs1, 2));

        testStateException(() -> AssertCrud.affectLt(1L, 1L, "err"));
        testStateException(() -> AssertCrud.affectLt(1L, 1L, "err", 1L));
        testStateException(() -> AssertCrud.affectLt(longs1, 1L, "err"));
        testStateException(() -> AssertCrud.affectLt(longs1, 1L, "err", 1L));
        testCrudException(() -> AssertCrud.affectLt(1L, 1L, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLt(1L, 1L));
        testCrudException(() -> AssertCrud.selectLt(1L, 1L));
        testCrudException(() -> AssertCrud.updateLt(1L, 1L));
        testCrudException(() -> AssertCrud.deleteLt(1L, 1L));
        testCrudException(() -> AssertCrud.affectLt(longs1, 1L, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLt(longs1, 1L));
        testCrudException(() -> AssertCrud.selectLt(longs1, 1L));
        testCrudException(() -> AssertCrud.updateLt(longs1, 1L));
    }
    
    @Test
    void assertLe1L() {
        assertEquals(1L, AssertCrud.affectLe(1L, 2, "err"));
        assertEquals(1L, AssertCrud.affectLe(1L, 2, "err", 1L));
        assertSame(longs1, AssertCrud.affectLe(longs1, 2, "err"));
        assertSame(longs1, AssertCrud.affectLe(longs1, 2, "err", 1L));
        assertEquals(1L, AssertCrud.affectLe(1L, 2, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createLe(1L, 2));
        assertEquals(1L, AssertCrud.selectLe(1L, 2));
        assertEquals(1L, AssertCrud.updateLe(1L, 2));
        assertEquals(1L, AssertCrud.deleteLe(1L, 2));
        assertSame(longs1, AssertCrud.affectLe(longs1, 2, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createLe(longs1, 2));
        assertSame(longs1, AssertCrud.selectLe(longs1, 2));
        assertSame(longs1, AssertCrud.updateLe(longs1, 2));
        assertSame(longs1, AssertCrud.deleteLe(longs1, 2));
        assertEquals(1L, AssertCrud.affectLe(1L, 1L, "err"));
        assertEquals(1L, AssertCrud.affectLe(1L, 1L, "err", 1L));
        assertSame(longs1, AssertCrud.affectLe(longs1, 1L, "err"));
        assertSame(longs1, AssertCrud.affectLe(longs1, 1L, "err", 1L));
        assertEquals(1L, AssertCrud.affectLe(1L, 1L, AssertErrorEnum.AssertTrue));
        assertEquals(1L, AssertCrud.createLe(1L, 1L));
        assertEquals(1L, AssertCrud.selectLe(1L, 1L));
        assertEquals(1L, AssertCrud.updateLe(1L, 1L));
        assertEquals(1L, AssertCrud.deleteLe(1L, 1L));
        assertSame(longs1, AssertCrud.affectLe(longs1, 1L, AssertErrorEnum.AssertTrue));
        assertSame(longs1, AssertCrud.createLe(longs1, 1L));
        assertSame(longs1, AssertCrud.selectLe(longs1, 1L));
        assertSame(longs1, AssertCrud.updateLe(longs1, 1L));
        assertSame(longs1, AssertCrud.deleteLe(longs1, 1L));

        testStateException(() -> AssertCrud.affectLe(1L, 0, "err"));
        testStateException(() -> AssertCrud.affectLe(1L, 0, "err", 1L));
        testStateException(() -> AssertCrud.affectLe(longs1, 0, "err"));
        testStateException(() -> AssertCrud.affectLe(longs1, 0, "err", 1L));
        testCrudException(() -> AssertCrud.affectLe(1L, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLe(1L, 0));
        testCrudException(() -> AssertCrud.selectLe(1L, 0));
        testCrudException(() -> AssertCrud.updateLe(1L, 0));
        testCrudException(() -> AssertCrud.deleteLe(1L, 0));
        testCrudException(() -> AssertCrud.affectLe(longs1, 0, AssertErrorEnum.AssertTrue));
        testCrudException(() -> AssertCrud.createLe(longs1, 0));
    }
}