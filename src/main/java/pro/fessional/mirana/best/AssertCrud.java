package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import pro.fessional.mirana.cond.PredictVal;
import pro.fessional.mirana.i18n.CodeEnum;
import pro.fessional.mirana.i18n.CrudErrorEnum;
import pro.fessional.mirana.pain.CrudException;
import pro.fessional.mirana.text.FormatUtil;

/**
 * affectXxx return para1
 *
 * @author trydofor
 * @since 2025-02-01
 */
public class AssertCrud {

    //
    @Contract("_,_->param1")
    public static int createEq(int a, int b) {
        return affectEq(a, b, CrudErrorEnum.CreateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int selectEq(int a, int b) {
        return affectEq(a, b, CrudErrorEnum.SelectEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int updateEq(int a, int b) {
        return affectEq(a, b, CrudErrorEnum.UpdateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int deleteEq(int a, int b) {
        return affectEq(a, b, CrudErrorEnum.DeleteEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int createNe(int a, int b) {
        return affectNe(a, b, CrudErrorEnum.CreateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int selectNe(int a, int b) {
        return affectNe(a, b, CrudErrorEnum.SelectNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int updateNe(int a, int b) {
        return affectNe(a, b, CrudErrorEnum.UpdateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int deleteNe(int a, int b) {
        return affectNe(a, b, CrudErrorEnum.DeleteNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int createGt(int a, int b) {
        return affectGt(a, b, CrudErrorEnum.CreateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int selectGt(int a, int b) {
        return affectGt(a, b, CrudErrorEnum.SelectGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int updateGt(int a, int b) {
        return affectGt(a, b, CrudErrorEnum.UpdateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int deleteGt(int a, int b) {
        return affectGt(a, b, CrudErrorEnum.DeleteGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int createGe(int a, int b) {
        return affectGe(a, b, CrudErrorEnum.CreateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int selectGe(int a, int b) {
        return affectGe(a, b, CrudErrorEnum.SelectGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int updateGe(int a, int b) {
        return affectGe(a, b, CrudErrorEnum.UpdateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int deleteGe(int a, int b) {
        return affectGe(a, b, CrudErrorEnum.DeleteGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int createLt(int a, int b) {
        return affectLt(a, b, CrudErrorEnum.CreateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int selectLt(int a, int b) {
        return affectLt(a, b, CrudErrorEnum.SelectLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int updateLt(int a, int b) {
        return affectLt(a, b, CrudErrorEnum.UpdateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int deleteLt(int a, int b) {
        return affectLt(a, b, CrudErrorEnum.DeleteLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int createLe(int a, int b) {
        return affectLe(a, b, CrudErrorEnum.CreateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int selectLe(int a, int b) {
        return affectLe(a, b, CrudErrorEnum.SelectLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int updateLe(int a, int b) {
        return affectLe(a, b, CrudErrorEnum.UpdateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int deleteLe(int a, int b) {
        return affectLe(a, b, CrudErrorEnum.DeleteLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] createEq(int[] a, int b) {
        return affectEq(a, b, CrudErrorEnum.CreateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] selectEq(int[] a, int b) {
        return affectEq(a, b, CrudErrorEnum.SelectEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] updateEq(int[] a, int b) {
        return affectEq(a, b, CrudErrorEnum.UpdateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] deleteEq(int[] a, int b) {
        return affectEq(a, b, CrudErrorEnum.DeleteEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] createNe(int[] a, int b) {
        return affectNe(a, b, CrudErrorEnum.CreateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] selectNe(int[] a, int b) {
        return affectNe(a, b, CrudErrorEnum.SelectNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] updateNe(int[] a, int b) {
        return affectNe(a, b, CrudErrorEnum.UpdateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] deleteNe(int[] a, int b) {
        return affectNe(a, b, CrudErrorEnum.DeleteNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] createGt(int[] a, int b) {
        return affectGt(a, b, CrudErrorEnum.CreateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] selectGt(int[] a, int b) {
        return affectGt(a, b, CrudErrorEnum.SelectGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] updateGt(int[] a, int b) {
        return affectGt(a, b, CrudErrorEnum.UpdateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] deleteGt(int[] a, int b) {
        return affectGt(a, b, CrudErrorEnum.DeleteGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] createGe(int[] a, int b) {
        return affectGe(a, b, CrudErrorEnum.CreateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] selectGe(int[] a, int b) {
        return affectGe(a, b, CrudErrorEnum.SelectGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] updateGe(int[] a, int b) {
        return affectGe(a, b, CrudErrorEnum.UpdateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] deleteGe(int[] a, int b) {
        return affectGe(a, b, CrudErrorEnum.DeleteGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] createLt(int[] a, int b) {
        return affectLt(a, b, CrudErrorEnum.CreateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] selectLt(int[] a, int b) {
        return affectLt(a, b, CrudErrorEnum.SelectLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] updateLt(int[] a, int b) {
        return affectLt(a, b, CrudErrorEnum.UpdateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] deleteLt(int[] a, int b) {
        return affectLt(a, b, CrudErrorEnum.DeleteLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] createLe(int[] a, int b) {
        return affectLe(a, b, CrudErrorEnum.CreateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] selectLe(int[] a, int b) {
        return affectLe(a, b, CrudErrorEnum.SelectLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] updateLe(int[] a, int b) {
        return affectLe(a, b, CrudErrorEnum.UpdateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static int[] deleteLe(int[] a, int b) {
        return affectLe(a, b, CrudErrorEnum.DeleteLeRecord1, b);
    }

    //
    @Contract("_,_,_->param1")
    public static int affectEq(int a, int b, CodeEnum err) {
        if (PredictVal.ne(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectEq(int a, int b, CodeEnum err, Object... args) {
        if (PredictVal.ne(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectNe(int a, int b, CodeEnum err) {
        if (PredictVal.eq(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectNe(int a, int b, CodeEnum err, Object... args) {
        if (PredictVal.eq(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectGt(int a, int b, CodeEnum err) {
        if (PredictVal.le(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectGt(int a, int b, CodeEnum err, Object... args) {
        if (PredictVal.le(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectGe(int a, int b, CodeEnum err) {
        if (PredictVal.lt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectGe(int a, int b, CodeEnum err, Object... args) {
        if (PredictVal.lt(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectLt(int a, int b, CodeEnum err) {
        if (PredictVal.ge(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectLt(int a, int b, CodeEnum err, Object... args) {
        if (PredictVal.ge(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectLe(int a, int b, CodeEnum err) {
        if (PredictVal.gt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectLe(int a, int b, CodeEnum err, Object... args) {
        if (PredictVal.gt(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectEq(int[] a, int b, CodeEnum err) {
        if (PredictVal.ne(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectEq(int[] a, int b, CodeEnum err, Object... args) {
        if (PredictVal.ne(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectNe(int[] a, int b, CodeEnum err) {
        if (PredictVal.eq(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectNe(int[] a, int b, CodeEnum err, Object... args) {
        if (PredictVal.eq(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectGt(int[] a, int b, CodeEnum err) {
        if (PredictVal.le(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectGt(int[] a, int b, CodeEnum err, Object... args) {
        if (PredictVal.le(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectGe(int[] a, int b, CodeEnum err) {
        if (PredictVal.lt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectGe(int[] a, int b, CodeEnum err, Object... args) {
        if (PredictVal.lt(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectLt(int[] a, int b, CodeEnum err) {
        if (PredictVal.ge(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectLt(int[] a, int b, CodeEnum err, Object... args) {
        if (PredictVal.ge(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectLe(int[] a, int b, CodeEnum err) {
        if (PredictVal.gt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectLe(int[] a, int b, CodeEnum err, Object... args) {
        if (PredictVal.gt(a, b)) throw new CrudException(err, args);
        return a;
    }

    //
    @Contract("_,_,_->param1")
    public static int affectEq(int a, int b, String err) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectNe(int a, int b, String err) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectGt(int a, int b, String err) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectGe(int a, int b, String err) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectLt(int a, int b, String err) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int affectLe(int a, int b, String err) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectEq(int[] a, int b, String err) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectNe(int[] a, int b, String err) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectGt(int[] a, int b, String err) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectGe(int[] a, int b, String err) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectLt(int[] a, int b, String err) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static int[] affectLe(int[] a, int b, String err) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    //
    @Contract("_,_,_,_->param1")
    public static int affectEq(int a, int b, String err, Object... arg) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectNe(int a, int b, String err, Object... arg) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectGt(int a, int b, String err, Object... arg) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectGe(int a, int b, String err, Object... arg) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectLt(int a, int b, String err, Object... arg) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int affectLe(int a, int b, String err, Object... arg) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectEq(int[] a, int b, String err, Object... arg) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectNe(int[] a, int b, String err, Object... arg) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectGt(int[] a, int b, String err, Object... arg) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectGe(int[] a, int b, String err, Object... arg) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectLt(int[] a, int b, String err, Object... arg) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static int[] affectLe(int[] a, int b, String err, Object... arg) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }
    //
    @Contract("_,_->param1")
    public static long createEq(long a, long b) {
        return affectEq(a, b, CrudErrorEnum.CreateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long selectEq(long a, long b) {
        return affectEq(a, b, CrudErrorEnum.SelectEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long updateEq(long a, long b) {
        return affectEq(a, b, CrudErrorEnum.UpdateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long deleteEq(long a, long b) {
        return affectEq(a, b, CrudErrorEnum.DeleteEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long createNe(long a, long b) {
        return affectNe(a, b, CrudErrorEnum.CreateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long selectNe(long a, long b) {
        return affectNe(a, b, CrudErrorEnum.SelectNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long updateNe(long a, long b) {
        return affectNe(a, b, CrudErrorEnum.UpdateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long deleteNe(long a, long b) {
        return affectNe(a, b, CrudErrorEnum.DeleteNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long createGt(long a, long b) {
        return affectGt(a, b, CrudErrorEnum.CreateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long selectGt(long a, long b) {
        return affectGt(a, b, CrudErrorEnum.SelectGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long updateGt(long a, long b) {
        return affectGt(a, b, CrudErrorEnum.UpdateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long deleteGt(long a, long b) {
        return affectGt(a, b, CrudErrorEnum.DeleteGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long createGe(long a, long b) {
        return affectGe(a, b, CrudErrorEnum.CreateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long selectGe(long a, long b) {
        return affectGe(a, b, CrudErrorEnum.SelectGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long updateGe(long a, long b) {
        return affectGe(a, b, CrudErrorEnum.UpdateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long deleteGe(long a, long b) {
        return affectGe(a, b, CrudErrorEnum.DeleteGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long createLt(long a, long b) {
        return affectLt(a, b, CrudErrorEnum.CreateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long selectLt(long a, long b) {
        return affectLt(a, b, CrudErrorEnum.SelectLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long updateLt(long a, long b) {
        return affectLt(a, b, CrudErrorEnum.UpdateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long deleteLt(long a, long b) {
        return affectLt(a, b, CrudErrorEnum.DeleteLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long createLe(long a, long b) {
        return affectLe(a, b, CrudErrorEnum.CreateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long selectLe(long a, long b) {
        return affectLe(a, b, CrudErrorEnum.SelectLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long updateLe(long a, long b) {
        return affectLe(a, b, CrudErrorEnum.UpdateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long deleteLe(long a, long b) {
        return affectLe(a, b, CrudErrorEnum.DeleteLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] createEq(long[] a, long b) {
        return affectEq(a, b, CrudErrorEnum.CreateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] selectEq(long[] a, long b) {
        return affectEq(a, b, CrudErrorEnum.SelectEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] updateEq(long[] a, long b) {
        return affectEq(a, b, CrudErrorEnum.UpdateEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] deleteEq(long[] a, long b) {
        return affectEq(a, b, CrudErrorEnum.DeleteEqRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] createNe(long[] a, long b) {
        return affectNe(a, b, CrudErrorEnum.CreateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] selectNe(long[] a, long b) {
        return affectNe(a, b, CrudErrorEnum.SelectNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] updateNe(long[] a, long b) {
        return affectNe(a, b, CrudErrorEnum.UpdateNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] deleteNe(long[] a, long b) {
        return affectNe(a, b, CrudErrorEnum.DeleteNeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] createGt(long[] a, long b) {
        return affectGt(a, b, CrudErrorEnum.CreateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] selectGt(long[] a, long b) {
        return affectGt(a, b, CrudErrorEnum.SelectGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] updateGt(long[] a, long b) {
        return affectGt(a, b, CrudErrorEnum.UpdateGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] deleteGt(long[] a, long b) {
        return affectGt(a, b, CrudErrorEnum.DeleteGtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] createGe(long[] a, long b) {
        return affectGe(a, b, CrudErrorEnum.CreateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] selectGe(long[] a, long b) {
        return affectGe(a, b, CrudErrorEnum.SelectGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] updateGe(long[] a, long b) {
        return affectGe(a, b, CrudErrorEnum.UpdateGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] deleteGe(long[] a, long b) {
        return affectGe(a, b, CrudErrorEnum.DeleteGeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] createLt(long[] a, long b) {
        return affectLt(a, b, CrudErrorEnum.CreateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] selectLt(long[] a, long b) {
        return affectLt(a, b, CrudErrorEnum.SelectLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] updateLt(long[] a, long b) {
        return affectLt(a, b, CrudErrorEnum.UpdateLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] deleteLt(long[] a, long b) {
        return affectLt(a, b, CrudErrorEnum.DeleteLtRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] createLe(long[] a, long b) {
        return affectLe(a, b, CrudErrorEnum.CreateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] selectLe(long[] a, long b) {
        return affectLe(a, b, CrudErrorEnum.SelectLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] updateLe(long[] a, long b) {
        return affectLe(a, b, CrudErrorEnum.UpdateLeRecord1, b);
    }

    @Contract("_,_->param1")
    public static long[] deleteLe(long[] a, long b) {
        return affectLe(a, b, CrudErrorEnum.DeleteLeRecord1, b);
    }

    //
    @Contract("_,_,_->param1")
    public static long affectEq(long a, long b, CodeEnum err) {
        if (PredictVal.ne(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectEq(long a, long b, CodeEnum err, Object... args) {
        if (PredictVal.ne(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectNe(long a, long b, CodeEnum err) {
        if (PredictVal.eq(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectNe(long a, long b, CodeEnum err, Object... args) {
        if (PredictVal.eq(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectGt(long a, long b, CodeEnum err) {
        if (PredictVal.le(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectGt(long a, long b, CodeEnum err, Object... args) {
        if (PredictVal.le(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectGe(long a, long b, CodeEnum err) {
        if (PredictVal.lt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectGe(long a, long b, CodeEnum err, Object... args) {
        if (PredictVal.lt(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectLt(long a, long b, CodeEnum err) {
        if (PredictVal.ge(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectLt(long a, long b, CodeEnum err, Object... args) {
        if (PredictVal.ge(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectLe(long a, long b, CodeEnum err) {
        if (PredictVal.gt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectLe(long a, long b, CodeEnum err, Object... args) {
        if (PredictVal.gt(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectEq(long[] a, long b, CodeEnum err) {
        if (PredictVal.ne(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectEq(long[] a, long b, CodeEnum err, Object... args) {
        if (PredictVal.ne(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectNe(long[] a, long b, CodeEnum err) {
        if (PredictVal.eq(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectNe(long[] a, long b, CodeEnum err, Object... args) {
        if (PredictVal.eq(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectGt(long[] a, long b, CodeEnum err) {
        if (PredictVal.le(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectGt(long[] a, long b, CodeEnum err, Object... args) {
        if (PredictVal.le(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectGe(long[] a, long b, CodeEnum err) {
        if (PredictVal.lt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectGe(long[] a, long b, CodeEnum err, Object... args) {
        if (PredictVal.lt(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectLt(long[] a, long b, CodeEnum err) {
        if (PredictVal.ge(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectLt(long[] a, long b, CodeEnum err, Object... args) {
        if (PredictVal.ge(a, b)) throw new CrudException(err, args);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectLe(long[] a, long b, CodeEnum err) {
        if (PredictVal.gt(a, b)) throw new CrudException(err);
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectLe(long[] a, long b, CodeEnum err, Object... args) {
        if (PredictVal.gt(a, b)) throw new CrudException(err, args);
        return a;
    }

    //
    @Contract("_,_,_->param1")
    public static long affectEq(long a, long b, String err) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectNe(long a, long b, String err) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectGt(long a, long b, String err) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectGe(long a, long b, String err) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectLt(long a, long b, String err) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long affectLe(long a, long b, String err) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectEq(long[] a, long b, String err) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectNe(long[] a, long b, String err) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectGt(long[] a, long b, String err) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectGe(long[] a, long b, String err) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectLt(long[] a, long b, String err) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(err);
        return a;
    }

    @Contract("_,_,_->param1")
    public static long[] affectLe(long[] a, long b, String err) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(err);
        return a;
    }

    //
    @Contract("_,_,_,_->param1")
    public static long affectEq(long a, long b, String err, Object... arg) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectNe(long a, long b, String err, Object... arg) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectGt(long a, long b, String err, Object... arg) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectGe(long a, long b, String err, Object... arg) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectLt(long a, long b, String err, Object... arg) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long affectLe(long a, long b, String err, Object... arg) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectEq(long[] a, long b, String err, Object... arg) {
        if (PredictVal.ne(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectNe(long[] a, long b, String err, Object... arg) {
        if (PredictVal.eq(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectGt(long[] a, long b, String err, Object... arg) {
        if (PredictVal.le(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectGe(long[] a, long b, String err, Object... arg) {
        if (PredictVal.lt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectLt(long[] a, long b, String err, Object... arg) {
        if (PredictVal.ge(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }

    @Contract("_,_,_,_->param1")
    public static long[] affectLe(long[] a, long b, String err, Object... arg) {
        if (PredictVal.gt(a, b)) throw new IllegalStateException(FormatUtil.logback(err, arg));
        return a;
    }
}
