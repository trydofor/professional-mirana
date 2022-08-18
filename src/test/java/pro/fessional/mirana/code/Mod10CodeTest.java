package pro.fessional.mirana.code;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-05-21
 */
public class Mod10CodeTest {
    @Test
    public void check() {
        assertTrue(Mod10Code.check("9101123456789000000013"));
        assertTrue(Mod10Code.check("92750902212248000000409253"));
        assertTrue(Mod10Code.check("92001902643733000106138567"));
        assertTrue(Mod10Code.check("92001902643733000106190169"));
        assertTrue(Mod10Code.check("92001902643733000106211963"));
    }
}
