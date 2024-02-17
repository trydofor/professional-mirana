package pro.fessional.mirana.dync;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author trydofor
 * @since 2024-02-17
 */
class OrderedSpiTest {

    @Test
    void orderNull() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(2);
        arr.add(1);
        arr.sort(null);
        Assertions.assertEquals(Arrays.asList(1,2), arr);
    }
}