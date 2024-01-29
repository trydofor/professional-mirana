package pro.fessional.mirana.stat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author trydofor
 * @since 2022-09-28
 */
class WordStatTest {

    @Test
    void statWord() {
        WordStat ws = new WordStat();
        Map<String, Integer> map = new HashMap<>();
        ws.statWord(map, "WordStat wordStat = new WordStat()");
        Map<String, Integer> wtd = new HashMap<>();
        wtd.put("new", 1);
        wtd.put("stat", 3);
        wtd.put("word", 3);
        Assertions.assertEquals(wtd, map);
    }

    @Test
    void infoHere() {
        WordStat ws = new WordStat();
        ws.printJavax(Paths.get("."));
    }
}
