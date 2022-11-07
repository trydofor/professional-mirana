package pro.fessional.mirana.tk;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.bits.Aes;
import pro.fessional.mirana.bits.Aes256;
import pro.fessional.mirana.bits.Base64;
import pro.fessional.mirana.bits.HmacHelp;
import pro.fessional.mirana.bits.MdHelp;
import pro.fessional.mirana.code.RandCode;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2021-01-25
 */
class AnyTicketTest {

    private final BiFunction<String, String, Ticket.Mutable> acceptAny = (s, s2) -> new AnyTicket();

    @Test
    void builder0() {
        System.out.println("current second=" + (System.currentTimeMillis() / 1000));
        for (int i = 0; i < 10; i++) {
            String salt = RandCode.human(20);
            final byte[] key = salt.getBytes();

            builderMd0(i, MdHelp.md5, salt);
            builderMd0(i, MdHelp.sha1, salt);
            builderMd0(i, MdHelp.sha256, salt);
            builderHm0(i, HmacHelp.md5(key));
            builderHm0(i, HmacHelp.sha1(key));
            builderHm0(i, HmacHelp.sha256(key));
        }
    }

    @Test
    void builder1() {
        for (int i = 0; i < 10; i++) {
            String salt = RandCode.human(20);
            final byte[] key = salt.getBytes();

            builderMd1(i, MdHelp.md5, salt);
            builderMd1(i, MdHelp.sha1, salt);
            builderMd1(i, MdHelp.sha256, salt);
            builderHm1(i, HmacHelp.md5(key), salt);
            builderHm1(i, HmacHelp.sha1(key), salt);
            builderHm1(i, HmacHelp.sha256(key), salt);
        }
    }

    private String pubMod(String md) {
        return String.format("%10s", md.replace("-", ""));
    }

    private void printTicket(Ticket tk) {
        final String str = tk.serialize();
        System.out.printf("%3d %s\n", str.length(), str);
    }

    private void builderMd0(int seq, MdHelp md, String salt) {
        String mod = pubMod(md.algorithm);
        long exp = System.currentTimeMillis() / 1000 + 3600;
        final byte[] key = salt.getBytes();
        byte[] sig0 = md.digest((mod + "-" + exp + "-" + seq + salt).getBytes());
        final String sigPart = Base64.encode(sig0);
        AnyTicket at1 = new AnyTicket(mod, exp, seq, null, sigPart);

        assertEquals(mod, at1.getPubMod());
        assertEquals(exp, at1.getPubDue());
        assertEquals(seq, at1.getPubSeq());
        assertEquals("", at1.getBizPart());
        assertEquals(sigPart, at1.getSigPart());

        printTicket(at1);
        assertEquals(at1, TicketHelp.parse(at1.serialize(), acceptAny));

        final AnyTicket at2 = new TicketHelp.Builder<>(new AnyTicket())
                .mod(mod)
                .exp(exp)
                .seq(seq)
                .bizEmpty()
                .sig(md, key);

        assertEquals(mod, at2.getPubMod());
        assertEquals(exp, at2.getPubDue());
        assertEquals(seq, at2.getPubSeq());
        assertEquals("", at2.getBizPart());
        assertEquals(sigPart, at2.getSigPart());

        assertEquals(at1, at2);
        assertEquals(at2, TicketHelp.parse(at2.serialize(), acceptAny));
        final TicketHelp.SigFun sigFun = TicketHelp.sig(md, key);
        assertEquals(at1.getSigPart(), sigFun.apply(at1.getSigData()));
        assertEquals(at2.getSigPart(), sigFun.apply(at2.getSigData()));
    }

    private void builderMd1(int seq, MdHelp md, String salt) {
        String mod = pubMod(md.algorithm);
        long exp = System.currentTimeMillis() / 1000 + 3600;
        final byte[] key = salt.getBytes();
        String biz0 = RandCode.human(30);
        String bizPart = Aes256.of(salt).encode64(biz0);
        byte[] sig0 = md.digest((mod + "-" + exp + "-" + seq + "." + bizPart + salt).getBytes());
        final String sigPart = Base64.encode(sig0);
        AnyTicket at1 = new AnyTicket(mod, exp, seq, bizPart, sigPart);

        assertEquals(mod, at1.getPubMod());
        assertEquals(exp, at1.getPubDue());
        assertEquals(seq, at1.getPubSeq());
        assertEquals(bizPart, at1.getBizPart());
        assertEquals(sigPart, at1.getSigPart());

        printTicket(at1);
        assertEquals(at1, TicketHelp.parse(at1.serialize(), acceptAny));

        final AnyTicket at2 = new TicketHelp.Builder<>(new AnyTicket())
                .mod(mod)
                .exp(exp)
                .seq(seq)
                .bizAes(biz0, salt)
                .sig(md, key);

        assertEquals(mod, at2.getPubMod());
        assertEquals(exp, at2.getPubDue());
        assertEquals(seq, at2.getPubSeq());
        assertEquals(bizPart, at2.getBizPart());
        assertEquals(sigPart, at2.getSigPart());

        assertEquals(at1, at2);
        assertEquals(at2, TicketHelp.parse(at2.serialize(), acceptAny));
        final TicketHelp.SigFun sigFun = TicketHelp.sig(md, key);
        assertEquals(at1.getSigPart(), sigFun.apply(at1.getSigData()));
        assertEquals(at2.getSigPart(), sigFun.apply(at2.getSigData()));
    }

    private void builderHm0(int seq, HmacHelp md) {
        String mod = pubMod(md.algorithm);
        long exp = System.currentTimeMillis() / 1000 + 3600;
        byte[] sig0 = md.digest((mod + "-" + exp + "-" + seq).getBytes());
        final String sigPart = Base64.encode(sig0);
        AnyTicket at1 = new AnyTicket(mod, exp, seq, null, sigPart);

        assertEquals(mod, at1.getPubMod());
        assertEquals(exp, at1.getPubDue());
        assertEquals(seq, at1.getPubSeq());
        assertEquals("", at1.getBizPart());
        assertEquals(sigPart, at1.getSigPart());

        printTicket(at1);
        assertEquals(at1, TicketHelp.parse(at1.serialize(), acceptAny));
        final AnyTicket at2 = new TicketHelp.Builder<>(new AnyTicket())
                .mod(mod)
                .exp(exp)
                .seq(seq)
                .bizEmpty()
                .sig(md);

        assertEquals(mod, at2.getPubMod());
        assertEquals(exp, at2.getPubDue());
        assertEquals(seq, at2.getPubSeq());
        assertEquals("", at2.getBizPart());
        assertEquals(sigPart, at2.getSigPart());
        assertEquals(at1, at2);
        final TicketHelp.SigHmac sigFun = TicketHelp.sig(md);
        assertEquals(at1.getSigPart(), sigFun.apply(at1.getSigData()));
        assertEquals(at2.getSigPart(), sigFun.apply(at2.getSigData()));
    }

    private void builderHm1(int seq, HmacHelp md, String key) {
        String mod = pubMod(md.algorithm);
        long exp = System.currentTimeMillis() / 1000 + 3600;
        String biz0 = RandCode.human(30);
        final Aes aes = Aes256.of(key);
        String bizPart = aes.encode64(biz0);
        byte[] sig0 = md.digest((mod + "-" + exp + "-" + seq + "." + bizPart).getBytes());
        final String sigPart = Base64.encode(sig0);
        AnyTicket at1 = new AnyTicket(mod, exp, seq, bizPart, sigPart);

        assertEquals(mod, at1.getPubMod());
        assertEquals(exp, at1.getPubDue());
        assertEquals(seq, at1.getPubSeq());
        assertEquals(bizPart, at1.getBizPart());
        assertEquals(sigPart, at1.getSigPart());

        printTicket(at1);
        assertEquals(at1, TicketHelp.parse(at1.serialize(), acceptAny));
        final AnyTicket at2 = new TicketHelp.Builder<>(new AnyTicket())
                .mod(mod)
                .exp(exp)
                .seq(seq)
                .bizAes(biz0, aes)
                .sig(md);

        assertEquals(mod, at2.getPubMod());
        assertEquals(exp, at2.getPubDue());
        assertEquals(seq, at2.getPubSeq());
        assertEquals(bizPart, at2.getBizPart());
        assertEquals(sigPart, at2.getSigPart());
        assertEquals(at1, at2);
        final TicketHelp.SigHmac sigFun = TicketHelp.sig(md);
        assertEquals(at1.getSigPart(), sigFun.apply(at1.getSigData()));
        assertEquals(at2.getSigPart(), sigFun.apply(at2.getSigData()));

    }
}
