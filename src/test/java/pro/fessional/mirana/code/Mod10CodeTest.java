package pro.fessional.mirana.code;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author trydofor
 * @since 2020-05-21
 */
public class Mod10CodeTest {
    @Test
    public void check() {
        Assert.assertTrue(Mod10Code.check("9101123456789000000013"));
        Assert.assertTrue(Mod10Code.check("92750902212248000000409253"));
        Assert.assertTrue(Mod10Code.check("92001902643733000106138567"));
        Assert.assertTrue(Mod10Code.check("92001902643733000106190169"));
        Assert.assertTrue(Mod10Code.check("92001902643733000106211963"));
    }
}