package pro.fessional.mirana.tk;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.code.RandCode;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-27
 */
class TicketHelpTest {

    private final TicketHelp.Parser<AnyTicket> parser = TicketHelp.parser(AnyTicket::new);

    @Test
    void am0() {
        System.out.println("current second=" + (System.currentTimeMillis() / 1000));
        byte[] salt = RandCode.human(20).getBytes();
        String biz = RandCode.human(100);
        final TicketHelp.Am0Help am0Help = TicketHelp.am0(salt);
        final AnyTicket at0 = TicketHelp
                .builder(new AnyTicket())
                .mod(am0Help.mod)
                .expAfterNow(10, TimeUnit.MINUTES)
                .seq(10)
                .bizAes(biz, salt)
                .sig(am0Help.sigVerify);

        final String tk = at0.serialize();
        AnyTicket at1 = parser.parse(tk);
        System.out.println(tk);

        assertEquals(biz, am0Help.decodeBiz(at0, String.class));
        assertTrue(am0Help.verifySig(at0));
        assertEquals(biz, am0Help.decodeBiz(at1, String.class));
        assertTrue(am0Help.verifySig(at1));
    }

    @Test
    void am1() {
        byte[] salt = RandCode.human(20).getBytes();
        String biz = RandCode.human(100);
        final TicketHelp.Am1Help am1Help = TicketHelp.am1(salt);
        final AnyTicket at0 = TicketHelp
                .builder(new AnyTicket())
                .mod(am1Help.mod)
                .expAfter(10, TimeUnit.MINUTES)
                .seq(10)
                .bizAes(biz, salt)
                .sig(am1Help.sigVerify);

        final String tk = at0.serialize();
        AnyTicket at1 = parser.parse(tk);
        System.out.println(tk);

        assertEquals(biz, am1Help.decodeBiz(at0, String.class));
        assertTrue(am1Help.verifySig(at0));
        assertEquals(biz, am1Help.decodeBiz(at1, String.class));
        assertTrue(am1Help.verifySig(at1));
    }

    @Test
    void ah1() {
        byte[] salt = RandCode.human(20).getBytes();
        String biz = RandCode.human(100);
        final TicketHelp.Ah1Help ah1Help = TicketHelp.ah1(salt);
        final AnyTicket at0 = TicketHelp
                .builder(new AnyTicket())
                .mod(ah1Help.mod)
                .expAfterNow(10, TimeUnit.MINUTES)
                .seq(10)
                .bizAes(biz, salt)
                .sig(ah1Help.sigVerify);

        final String tk = at0.serialize();
        AnyTicket at1 = parser.parse(tk);

        assertEquals(biz, ah1Help.decodeBiz(at0, String.class));
        assertTrue(ah1Help.verifySig(at0));
        assertEquals(biz, ah1Help.decodeBiz(at1, String.class));
        assertTrue(ah1Help.verifySig(at1));
    }
}
