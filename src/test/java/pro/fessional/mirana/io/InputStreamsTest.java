package pro.fessional.mirana.io;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author trydofor
 * @since 2020-06-02
 */
public class InputStreamsTest {

    private final String pom = "./pom.xml";

    @Test
    public void readText() throws IOException {
        Testing.println(InputStreams.readText(new FileInputStream(pom)));
    }

    @Test
    public void readLine() throws IOException {
        Testing.println(InputStreams.readLine(new FileInputStream(pom)));
    }

    @Test
    public void saveTemp() {
        ByteArrayInputStream bis = new ByteArrayInputStream("1234567890".getBytes());
        File file = InputStreams.saveTemp(bis);
        Testing.println(file.getAbsolutePath());
    }
}