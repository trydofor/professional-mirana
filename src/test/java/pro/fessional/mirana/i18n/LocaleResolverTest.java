package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * @author trydofor
 * @since 2019-07-03
 */
public class LocaleResolverTest {

    @Test
    public void print() {
        System.out.println("zh-CN=" + Locale.forLanguageTag("zh-CN"));
        System.out.println("zh-cn=" + Locale.forLanguageTag("zh-cn"));
        System.out.println("ZH-CN=" + Locale.forLanguageTag("ZH-CN"));
        System.out.println("ZH-cn=" + Locale.forLanguageTag("ZH-cn"));
        System.out.println("CN=" + Locale.forLanguageTag("CN"));
        System.out.println("zh=" + Locale.forLanguageTag("zh"));
        System.out.println("zh_CN=" + Locale.forLanguageTag("zh_CN"));
        System.out.println("zh_cn=" + Locale.forLanguageTag("zh_cn"));
        System.out.println("ZH_CN=" + Locale.forLanguageTag("ZH_CN"));
    }

    @Test
    public void resolve() {
        System.out.println("zh-CN=" + LocaleResolver.locale("zh-CN"));
        System.out.println("zh-cn=" + LocaleResolver.locale("zh-cn"));
        System.out.println("ZH-CN=" + LocaleResolver.locale("ZH-CN"));
        System.out.println("ZH-cn=" + LocaleResolver.locale("ZH-cn"));
        System.out.println("CN=" + LocaleResolver.locale("CN"));
        System.out.println("zh=" + LocaleResolver.locale("zh"));
        System.out.println("zh_CN=" + LocaleResolver.locale("zh_CN"));
        System.out.println("zh_cn=" + LocaleResolver.locale("zh_cn"));
        System.out.println("ZH_CN=" + LocaleResolver.locale("ZH_CN"));
    }

    @Test
    public void loop() {
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            Locale.forLanguageTag("zh-CN");
        }
        System.out.println("Locale.forLanguageTag=" + (System.currentTimeMillis() - s1));

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            LocaleResolver.locale("zh-CN");
        }
        System.out.println("LocaleResolver.locale=" + (System.currentTimeMillis() - s2));
    }
}