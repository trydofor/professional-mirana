package pro.fessional.mirana.tk;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.code.RandCode;
import pro.fessional.mirana.time.ThreadNow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2021-01-27
 */
class TicketHelpTest {

    private long pubDue() {
        return ThreadNow.millis() / 1000 + 60L;
    }

    @Test
    void am0() {
        System.out.println("current second=" + (System.currentTimeMillis() / 1000));
        byte[] salt = RandCode.human(20).getBytes();
        String biz = RandCode.human(100);
        final TicketHelp.Am0Help am0Help = new TicketHelp.Am0Help(salt);
        final Ticket at0 = am0Help.encode(10, pubDue(), biz);
        final String tk = at0.serialize();
        Ticket at1 = TicketHelp.parse(tk, am0Help::accept);
        System.out.println(tk);

        assertEquals(biz, am0Help.decode(at0));
        assertTrue(am0Help.verify(at0));
        assertEquals(biz, am0Help.decode(at1));
        assertTrue(am0Help.verify(at1));
    }

    @Test
    void am1() {
        byte[] salt = RandCode.human(20).getBytes();
        String biz = RandCode.human(100);
        final TicketHelp.Am1Help am1Help = new TicketHelp.Am1Help(salt);
        final Ticket at0 = am1Help.encode(10, pubDue(), biz);

        final String tk = at0.serialize();
        Ticket at1 = TicketHelp.parse(tk, am1Help::accept);
        System.out.println(tk);

        assertEquals(biz, am1Help.decode(at0));
        assertTrue(am1Help.verify(at0));
        assertEquals(biz, am1Help.decode(at1));
        assertTrue(am1Help.verify(at1));
    }

    @Test
    void ah1() {
        byte[] salt = RandCode.human(20).getBytes();
        String biz = RandCode.human(100);
        final TicketHelp.Ah1Help ah1Help = new TicketHelp.Ah1Help(salt);
        final Ticket at0 = ah1Help.encode(10, pubDue(), biz);

        final String tk = at0.serialize();
        Ticket at1 = TicketHelp.parse(tk, ah1Help::accept);

        assertEquals(biz, ah1Help.decode(at0));
        assertTrue(ah1Help.verify(at0));
        assertEquals(biz, ah1Help.decode(at1));
        assertTrue(ah1Help.verify(at1));
    }
}
