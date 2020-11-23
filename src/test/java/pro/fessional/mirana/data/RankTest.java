package pro.fessional.mirana.data;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-11-18
 */
public class RankTest {

    @Test
    public void lineup() {
        List<String> list = Arrays.asList("1", "2");
        ArrayList<String> lineup = Rank.lineup(list, e -> e.equals("2"), e -> e.equals("1"));
        assertEquals("[2, 1]",lineup.toString());
    }
}