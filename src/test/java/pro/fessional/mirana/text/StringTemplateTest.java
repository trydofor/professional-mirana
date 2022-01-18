package pro.fessional.mirana.text;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author trydofor
 * @since 2020-11-30
 */
public class StringTemplateTest {

    @Test
    public void fix() {
        String url = StringTemplate.fix("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                   .bindStr("ACCESS_TOKEN", "abc123")
                                   .bindStr("OPENID", "bcd456")
                                   .toString();
        String url2 = StringTemplate.fix("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                    .bindStr("ACCESS_TOKEN", "abc123")
                                    .bindStr("OPENID", "bcd456")
                                    .toString();
        assertSame(url, url2);
        assertEquals("https://api.weixin.qq.com/cgi-bin/user/info?access_token=abc123&openid=bcd456&lang=en", url);

        // TODO bug
        final String body = StringTemplate
                .fix("key='',ttl=")
                .bindStr("${key}", "KEY")
                .bindStr("${ttl}", "123")
                .toString();
        assertEquals("key='',ttl=", body);
    }

    @Test
    public void dyn() {
        String url = StringTemplate.dyn("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                   .bindStr("ACCESS_TOKEN", "abc123")
                                   .bindStr("OPENID", "bcd456")
                                   .toString();
        String url2 = StringTemplate.dyn("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                    .bindStr("ACCESS_TOKEN", "abc123")
                                    .bindStr("OPENID", "bcd456")
                                    .toString();
        assertNotSame(url, url2);
        assertEquals(url, url2);
        assertEquals("https://api.weixin.qq.com/cgi-bin/user/info?access_token=abc123&openid=bcd456&lang=en", url);

        final String body = StringTemplate
                .dyn("key='',ttl=")
                .bindStr("${key}", "KEY")
                .bindStr("${ttl}", "123")
                .toString();
        assertEquals("key='',ttl=", body);

        final String next = StringTemplate
                .dyn("${key}${ttl}")
                .bindStr("${key}", "KEY")
                .bindStr("${ttl}", "123")
                .toString();
        assertEquals("KEY123", next);
    }

    @Test
    public void crx() {
        String url = StringTemplate.dyn("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                   .bindStr("ACCESS_TOKEN", "abc123")
                                   .bindStr("CCESS_TOKEN&", "bc1234") // 交叉
                                   .bindStr("ACCESS", "abc1") // 包含
                                   .bindStr("OPENID", "bcd456")
                                   .bindReg("OPEN", "bcd4") // 包含
                                   .toString();

        String url2 = StringTemplate.dyn("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                    .bindStr("ACCESS_TOKEN", "abc123")
                                    .bindStr("OPENID", "bcd456")
                                    .toString();
        assertEquals(url, url2);
        assertEquals("https://api.weixin.qq.com/cgi-bin/user/info?access_token=abc123&openid=bcd456&lang=en", url);
    }

    @Test
    public void thd() throws InterruptedException {
        final int cnt = 400;
        final int stp = 100000;
        final CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatch mainl = new CountDownLatch(cnt);
        for (int i = 0; i < cnt; i++) {
            final int t = i;
            new Thread(() -> {
                try {
                    latch.await();
                    mainl.countDown();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < stp; j++) {
                    String token = "token_" + (t * stp + j);
                    String t1 = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                                + token + "&openid="
                                + token + "&lang=en";

                    String t2 = StringTemplate.dyn("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                              .bindStr("ACCESS_TOKEN", token)
                                              .bindStr("OPENID", token)
                                              .toString();
                    assertEquals(t1, t2);
                    if (j % 1000 == 5) System.out.print('.');
                }
            }).start();
        }
        latch.countDown();
        mainl.await();
    }

    /*
    String.concat = 195
    String.append = 95
    String.replace = 1737
    String.format = 1354
    StringTemplate.fix = 290
    StringTemplate.dyn = 352
    StringTemplate.one = 1489
     */
    public static void main(String[] args) {
        int len = 100_0000;
        long s = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            String token = "token_" + i;
            String t = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                       + token + "&openid="
                       + token + "&lang=en";
        }
        System.out.println("String.concat = " + (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            String token = "token_" + i;
            String t = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" +
                       token + "&openid=" +
                       token + "&lang=en";
        }
        System.out.println("String.append = " + (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            String token = "token_" + i;
            String t = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en"
                    .replace("ACCESS_TOKEN", token)
                    .replace("OPENID", token);
        }
        System.out.println("String.replace = " + (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            String token = "token_" + i;
            String t = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=en", token, token);
        }
        System.out.println("String.format = " + (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            String token = "token_" + i;
            String t = StringTemplate.fix("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                     .bindStr("ACCESS_TOKEN", token)
                                     .bindStr("OPENID", token)
                                     .toString();
        }
        System.out.println("StringTemplate.fix = " + (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            String token = "token_" + i;
            String t = StringTemplate.dyn("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                     .bindStr("ACCESS_TOKEN", token)
                                     .bindStr("OPENID", token)
                                     .toString();
        }
        System.out.println("StringTemplate.dyn = " + (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            String token = "token_" + i;
            String t = StringTemplate.one("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                     .bindStr("ACCESS_TOKEN", token)
                                     .bindStr("OPENID", token)
                                     .toString();
        }
        System.out.println("StringTemplate.one = " + (System.currentTimeMillis() - s));
    }
}
