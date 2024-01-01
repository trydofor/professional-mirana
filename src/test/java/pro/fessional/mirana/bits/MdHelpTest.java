package pro.fessional.mirana.bits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;
import pro.fessional.mirana.code.RandCode;

import java.security.MessageDigest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-24
 */
class MdHelpTest {

    @Test
    void testAll() {
        for (int i = 0; i < 100; i++) {
            String mes = RandCode.human(100);
            final String md5 = MdHelp.md5.sum(mes);
            final String sha1 = MdHelp.sha1.sum(mes);
            final String sha256 = MdHelp.sha256.sum(mes);
            assertTrue(MdHelp.md5.check(md5, mes));
            assertTrue(MdHelp.sha1.check(sha1, mes));
            assertTrue(MdHelp.sha256.check(sha256, mes));
        }
    }

    @Test
    void print() {
        String mes = RandCode.human(100);
        final String md5Sum = MdHelp.md5.sum(mes);
        final String sha1Sum = MdHelp.sha1.sum(mes);
        final String sha256Sum = MdHelp.sha256.sum(mes);

        SystemOut.println("Md5    Hex=" + md5Sum);
        SystemOut.println("Sha1   Hex=" + sha1Sum);
        SystemOut.println("Sha256 Hex=" + sha256Sum);

        Assertions.assertEquals(MdHelp.LEN_MD5_HEX, md5Sum.length());
        Assertions.assertEquals(MdHelp.LEN_SHA1_HEX, sha1Sum.length());
        Assertions.assertEquals(MdHelp.LEN_SHA256_HEX, sha256Sum.length());
        Assertions.assertTrue(MdHelp.checks(md5Sum, mes));
        Assertions.assertTrue(MdHelp.checks(sha1Sum, mes));
        Assertions.assertTrue(MdHelp.checks(sha256Sum, mes));

        final byte[] bytes = mes.getBytes();
        SystemOut.println("Md5    B64=" + Base64.encode(MdHelp.md5.digest(bytes)));
        SystemOut.println("Sha1   B64=" + Base64.encode(MdHelp.sha1.digest(bytes)));
        SystemOut.println("Sha256 B64=" + Base64.encode(MdHelp.sha256.digest(bytes)));
    }

    @Test
    void inside() {
        for (int i = 0; i < 10; i++) {
            inside(MdHelp.md5);
            inside(MdHelp.sha1);
            inside(MdHelp.sha256);
        }
    }

    void inside(MdHelp md) {
        final MessageDigest inside = md.newOne();
        String s1 = RandCode.human(10);
        String s2 = RandCode.human(10);
        String s3 = RandCode.human(10);

        final String d1 = md.sum(s1 + s2 + s3);
        inside.update(s1.getBytes());
        inside.update(s2.getBytes());
        inside.update(s3.getBytes());
        final String d2 = Bytes.hex(inside.digest());

        assertEquals(d1, d2);

    }
}
