package pro.fessional.mirana.data;

import pro.fessional.mirana.i18n.I18nString;

/**
 * @author trydofor
 * @since 2019-09-09
 */
public interface CodeResult<T> extends DataResult<T> {

    /**
     * 信息编码
     *
     * @return 信息编码
     */
    String getCode();


    default CodeResult<T> setI18nArgs(Object... args) {
        return this;
    }

    default Object[] getI18nArgs() {
        return I18nString.EMPTY_ARGS;
    }

    default I18nString toI18nString(Object... args) {
        Object[] ags = args;
        if (ags == null || ags.length == 0) {
            ags = getI18nArgs();
        }
        return new I18nString(getCode(), getMessage(), ags);
    }
}
