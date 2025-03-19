package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;

/**
 * @author trydofor
 * @since 2025-01-10
 */

public enum CrudErrorEnum implements CodeEnum {
    CreateEqRecord1("error.crud.createEqRecord1", "must create exactly {0} record(s)"),
    SelectEqRecord1("error.crud.selectEqRecord1", "must select exactly {0} record(s)"),
    UpdateEqRecord1("error.crud.updateEqRecord1", "must update exactly {0} record(s)"),
    DeleteEqRecord1("error.crud.deleteEqRecord1", "must delete exactly {0} record(s)"),

    CreateNeRecord1("error.crud.createNeRecord1", "must not create {0} record(s)"),
    SelectNeRecord1("error.crud.selectNeRecord1", "must not select {0} record(s)"),
    UpdateNeRecord1("error.crud.updateNeRecord1", "must not update {0} record(s)"),
    DeleteNeRecord1("error.crud.deleteNeRecord1", "must not delete {0} record(s)"),

    CreateGtRecord1("error.crud.createGtRecord1", "must create more than {0} record(s)"),
    SelectGtRecord1("error.crud.selectGtRecord1", "must select more than {0} record(s)"),
    UpdateGtRecord1("error.crud.updateGtRecord1", "must update more than {0} record(s)"),
    DeleteGtRecord1("error.crud.deleteGtRecord1", "must delete more than {0} record(s)"),

    CreateGeRecord1("error.crud.createGeRecord1", "must create at least {0} record(s)"),
    SelectGeRecord1("error.crud.selectGeRecord1", "must select at least {0} record(s)"),
    UpdateGeRecord1("error.crud.updateGeRecord1", "must update at least {0} record(s)"),
    DeleteGeRecord1("error.crud.deleteGeRecord1", "must delete at least {0} record(s)"),

    CreateLtRecord1("error.crud.createLtRecord1", "must create fewer than {0} record(s)"),
    SelectLtRecord1("error.crud.selectLtRecord1", "must select fewer than {0} record(s)"),
    UpdateLtRecord1("error.crud.updateLtRecord1", "must update fewer than {0} record(s)"),
    DeleteLtRecord1("error.crud.deleteLtRecord1", "must delete fewer than {0} record(s)"),

    CreateLeRecord1("error.crud.createLeRecord1", "must create at most {0} record(s)"),
    SelectLeRecord1("error.crud.selectLeRecord1", "must select at most {0} record(s)"),
    UpdateLeRecord1("error.crud.updateLeRecord1", "must update at most {0} record(s)"),
    DeleteLeRecord1("error.crud.deleteLeRecord1", "must delete at most {0} record(s)"),
    ;

    private final @NotNull String code;
    private final @NotNull String hint;

    CrudErrorEnum(@NotNull String code, @NotNull String hint) {
        this.code = code;
        this.hint = hint;
    }


    @Override
    @NotNull
    public String getCode() {
        return code;
    }

    @Override
    @NotNull
    public String getHint() {
        return hint;
    }

    @Override
    public String toString() {
        return "CrudErrorEnum{" +
               "code='" + code + '\'' +
               ", hint='" + hint + '\'' +
               "} " + super.toString();
    }
}
