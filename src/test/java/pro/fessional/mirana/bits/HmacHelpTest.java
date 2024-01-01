package pro.fessional.mirana.bits;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;
import pro.fessional.mirana.code.RandCode;

import javax.crypto.Mac;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-24
 */
class HmacHelpTest {

    @Test
    void testAll() {
        for (int i = 0; i < 100; i++) {
            byte[] key = RandCode.human(20).getBytes();
            final HmacHelp md5Help = HmacHelp.md5(key);
            final HmacHelp sha1Help = HmacHelp.sha1(key);
            final HmacHelp sha256Help = HmacHelp.sha256(key);

            String mes = RandCode.human(100);
            final String md5 = md5Help.sum(mes);
            final String sha1 = sha1Help.sum(mes);
            final String sha256 = sha256Help.sum(mes);
            assertTrue(md5Help.check(md5, mes));
            assertTrue(sha1Help.check(sha1, mes));
            assertTrue(sha256Help.check(sha256, mes));

            assertFalse(md5Help.check(md5 + "1", mes));
            assertFalse(sha1Help.check(sha1 + "1", mes));
            assertFalse(sha256Help.check(sha256 + "1", mes));

            final String mes1 = mes + "1";
            assertFalse(md5Help.check(md5, mes1));
            assertFalse(sha1Help.check(sha1, mes1));
            assertFalse(sha256Help.check(sha256, mes1));
        }
    }

    @Test
    void print() {
        String mes = RandCode.human(100);
        final byte[] key = RandCode.human(20).getBytes();
        final HmacHelp md5Help = HmacHelp.md5(key);
        final HmacHelp sha1Help = HmacHelp.sha1(key);
        final HmacHelp sha256Help = HmacHelp.sha256(key);


        final String macMd5 = md5Help.sum(mes);
        final String macSha1 = sha1Help.sum(mes);
        final String macSha256 = sha256Help.sum(mes);

        SystemOut.println("macMd5    =" + macMd5);
        SystemOut.println("macSha1   =" + macSha1);
        SystemOut.println("macSha256 =" + macSha256);
    }

    @Test
    void inside() {
        for (int i = 0; i < 10; i++) {
            final byte[] key = RandCode.human(20).getBytes();
            final HmacHelp md5Help = HmacHelp.md5(key);
            final HmacHelp sha1Help = HmacHelp.sha1(key);
            final HmacHelp sha256Help = HmacHelp.sha256(key);
            inside(md5Help);
            inside(sha1Help);
            inside(sha256Help);
        }
    }

    void inside(HmacHelp md) {
        final Mac inside = md.newOne();
        String s1 = RandCode.human(20);
        String s2 = RandCode.human(20);
        String s3 = RandCode.human(20);

        final String d1 = md.sum(s1 + s2 + s3);
        inside.update(s1.getBytes());
        inside.update(s2.getBytes());
        inside.update(s3.getBytes());
        final String d2 = Bytes.hex(inside.doFinal());

        assertEquals(d1, d2);
    }
}
