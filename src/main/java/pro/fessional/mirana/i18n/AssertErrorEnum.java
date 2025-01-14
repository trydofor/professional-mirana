package pro.fessional.mirana.i18n;


import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 * message rules,
 * - simplicity first. `must not` or `must`       b
 * - make a suggestion. `should`
 * </pre>
 *
 * @author trydofor
 * @since 2025-01-13
 */
public enum AssertErrorEnum implements CodeEnum {

    AssertTrue("error.assert.true", "must be true"),
    AssertFalse("error.assert.false", "must be false"),
    AssertNull("error.assert.null", "must be null"),
    AssertNotNull("error.assert.notNull", "must not be null"),
    AssertEmpty("error.assert.empty", "must be empty"),
    AssertNotEmpty("error.assert.notEmpty", "must not be empty"),
    AssertEqual1("error.assert.equal1", "must equal to {}"),
    AssertNotEqual1("error.assert.notEqual1", "must not equal to {}"),
    AssertGreaterEqual1("error.assert.greaterEqual1", "must be greater than or equal to {}"),
    AssertGreater1("error.assert.greater1", "must be greater than {}"),
    AssertLessEqual1("error.assert.lessEqual1", "must be less than or equal to {}"),
    AssertLess1("error.assert.less1", "must be less than {}"),
    
    AssertTrue1("error.assert.true1", "{} must be true"),
    AssertFalse1("error.assert.false1", "{} must be false"),
    AssertNull1("error.assert.null1", "{} must be null"),
    AssertNotNull1("error.assert.notNull1", "{} must not be null"),
    AssertEmpty1("error.assert.empty1", "{} must be empty"),
    AssertNotEmpty1("error.assert.notEmpty1", "{} must not be empty"),
    AssertEqual2("error.assert.equal2", "{} must equal to {}"),
    AssertNotEqual2("error.assert.notEqual2", "{} must not equal to {}"),
    AssertGreaterEqual2("error.assert.greaterEqual2", "{} must be greater than or equal to {}"),
    AssertGreater2("error.assert.greater2", "{} must be greater than {}"),
    AssertLessEqual2("error.assert.lessEqual2", "{} must be less than or equal to {}"),
    AssertLess2("error.assert.less2", "{} must be less than {}"),
    ;

    AssertErrorEnum(@NotNull String code, @NotNull String hint) {
        this.code = code;
        this.hint = hint;
    }

    private final @NotNull String code;
    private final @NotNull String hint;


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
        return "AssertErrorEnum{" +
               "code='" + code + '\'' +
               ", hint='" + hint + '\'' +
               "} " + super.toString();
    }
}
