package pro.fessional.mirana.i18n;


import java.util.Objects;

/**
 * use name as i18nCode, without hint and args.
 *
 * @author trydofor
 * @since 2025-01-14
 */
public class I18nName implements I18nAware {

    private final String name;

    public I18nName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getI18nCode() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        I18nName i18nName = (I18nName) o;
        return Objects.equals(name, i18nName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return getName();
    }

    public static I18nName of(String name) {
        return new I18nName(name);
    }
}
