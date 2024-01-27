package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.util.Locale;

/**
 * @author trydofor
 * @since 2019-07-03
 */
public class LocaleResolverTest {

    @Test
    public void print() {
        SystemOut.println("zh-CN=" + Locale.forLanguageTag("zh-CN"));
        SystemOut.println("zh-cn=" + Locale.forLanguageTag("zh-cn"));
        SystemOut.println("ZH-CN=" + Locale.forLanguageTag("ZH-CN"));
        SystemOut.println("ZH-cn=" + Locale.forLanguageTag("ZH-cn"));
        SystemOut.println("CN=" + Locale.forLanguageTag("CN"));
        SystemOut.println("zh=" + Locale.forLanguageTag("zh"));
        SystemOut.println("zh_CN=" + Locale.forLanguageTag("zh_CN"));
        SystemOut.println("zh_cn=" + Locale.forLanguageTag("zh_cn"));
        SystemOut.println("ZH_CN=" + Locale.forLanguageTag("ZH_CN"));
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
        SystemOut.println("Locale.forLanguageTag=" + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            LocaleResolver.locale("zh-CN");
        }
        SystemOut.println("LocaleResolver.locale=" + (System.currentTimeMillis() - s2));
    }
}