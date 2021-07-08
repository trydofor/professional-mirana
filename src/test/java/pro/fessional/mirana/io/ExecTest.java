package pro.fessional.mirana.io;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-10-15
 */
public class ExecTest {

    @Test
    public void arg() {
        String line = "echo \"that's is mine\" a\\\\n\nd this is '\\${mirana} leap\\\\'";
        List<String> arg = Exec.arg(line);
        List<String> exp = Arrays.asList("echo", "that's is mine", "a\\n\nd", "this", "is", "\\${mirana} leap\\");
        assertEquals(exp, arg);
    }
}
