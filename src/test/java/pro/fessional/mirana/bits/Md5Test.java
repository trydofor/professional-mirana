package pro.fessional.mirana.bits;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2019-10-12
 */
public class Md5Test {

    @Test
    public void check() throws FileNotFoundException {
        File f = new File("src/test/java/pro/fessional/mirana/bits/Md5Test.java");
        String sum = Md5.sum(new FileInputStream(f));
        boolean check = Md5.check(sum, new FileInputStream(f));
        assertTrue(check);
        System.out.println(sum);
    }
}