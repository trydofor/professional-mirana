package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.util.Locale;

/**
 * @author trydofor
 * @since 2019-07-03
 */
public class LocaleResolverTest {

    @Test
    public void print() {
        Testing.println("zh-CN=" + Locale.forLanguageTag("zh-CN"));
        Testing.println("zh-cn=" + Locale.forLanguageTag("zh-cn"));
        Testing.println("ZH-CN=" + Locale.forLanguageTag("ZH-CN"));
        Testing.println("ZH-cn=" + Locale.forLanguageTag("ZH-cn"));
        Testing.println("CN=" + Locale.forLanguageTag("CN"));
        Testing.println("zh=" + Locale.forLanguageTag("zh"));
        Testing.println("zh_CN=" + Locale.forLanguageTag("zh_CN"));
        Testing.println("zh_cn=" + Locale.forLanguageTag("zh_cn"));
        Testing.println("ZH_CN=" + Locale.forLanguageTag("ZH_CN"));
    }

    @Test
    public void resolve() {
        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("zh-CN"));
        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("zh-cn"));
        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("ZH-CN"));
        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("ZH-cn"));

        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("zh_CN"));
        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("zh_cn"));
        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("ZH_CN"));
        Assertions.assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleResolver.locale("ZH_cn"));
        Assertions.assertEquals(Locale.CHINESE, LocaleResolver.locale("zh"));

        Assertions.assertEquals(Locale.getDefault(), LocaleResolver.locale("und"));

    }

    @Test
    @Disabled("time test")
    public void loop() {
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            Locale.forLanguageTag("zh-CN");
        }
        Testing.println("Locale.forLanguageTag=" + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            LocaleResolver.locale("zh-CN");
        }
        Testing.println("LocaleResolver.locale=" + (System.currentTimeMillis() - s2));
    }
}