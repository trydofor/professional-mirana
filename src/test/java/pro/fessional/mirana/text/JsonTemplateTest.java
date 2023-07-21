package pro.fessional.mirana.text;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * @author trydofor
 * @since 2022-09-29
 */
class JsonTemplateTest {

    @Test
    void testEmpty() {
        final String j1 = JsonTemplate.obj(obj -> {});
        Assertions.assertEquals("{}", j1);
        final String j2 = JsonTemplate.arr(arr -> {});
        Assertions.assertEquals("[]", j2);
    }

    @Test
    void testSimple() {
        final String j1 = JsonTemplate.obj(obj -> {
            obj.putVal("msgtype", "mar\"kd\\own");
            obj.putVal("success", true);
            obj.putVal("code", null);
        });
        Assertions.assertEquals("{\"msgtype\":\"mar\\\"kd\\\\own\",\"success\":true}", j1);
        final String j2 = JsonTemplate.arr(arr -> {
            arr.addVal(true);
            arr.addVal(1).addVal("mar\"kd\\own");
        });
        Assertions.assertEquals("[true,1,\"mar\\\"kd\\\\own\"]", j2);
    }

    @Test
    void testNested() {
        String r1 = ("{\n"
                     + "    \"at\": {\n"
                     + "        \"atMobiles\":[\n"
                     + "            \"180xxxxxx\"\n"
                     + "        ],\n"
                     + "        \"isAtAll\": false\n"
                     + "    },\n"
                     + "    \"text\": {\n"
                     + "        \"content\":\"Look how the prey scatters before us.\"\n"
                     + "    },\n"
                     + "    \"msgtype\":\"text\"\n"
                     + "}"
        ).replaceAll("[ \n]+", "");

        final String j1 = JsonTemplate.obj(top -> top
                .putObj("at", ob -> ob
                        .putArr("atMobiles", ar -> ar
                                .addVal("180xxxxxx"))
                        .putVal("isAtAll", false))
                .putObj("text", ob -> ob
                        .putVal("content", "Look how the prey scatters before us."))
                .putVal("msgtype", "text"));
        Assertions.assertEquals(r1, j1);

        final String f1 = new JSONObject()
                .fluentPut("at", new JSONObject()
                        .fluentPut("atMobiles", new JSONArray()
                                .fluentAdd("180xxxxxx"))
                        .fluentPut("isAtAll", false))
                .fluentPut("text", new JSONObject()
                        .fluentPut("content", "Look how the prey scatters before us."))
                .fluentPut("msgtype", "text")
                .toString();
        Assertions.assertEquals(r1, f1);


        final String r2 = "["
                          + "[{\"name\":\"tom\",\"male\":true}],"
                          + "[\"\",1]"
                          + "]";

        final String j2 = JsonTemplate.arr(top -> top
                .addArr(arr -> arr
                        .addObj(obj -> obj
                                .putVal("name", "tom")
                                .putVal("male", true)
                        )
                )
                .addArr(ar ->
                        ar.addVal("").addVal(1)
                )
        );

        Assertions.assertEquals(r2, j2);

        LinkedHashMap<String, Object> kvs = new LinkedHashMap<>();
        kvs.put("name", "tom");
        kvs.put("male", true);

        final String t2 = JsonTemplate.arr(top -> top
                .addArr(ar -> ar.addObj(kvs))
                .addArr(ar -> ar.addVal("").addVal(1)
                )
        );

        Assertions.assertEquals(r2, t2);

        final String f2 = new JSONArray()
                .fluentAdd(new JSONArray().fluentAdd(new JSONObject()
                        .fluentPut("name", "tom")
                        .fluentPut("male", true)))
                .fluentAdd(new JSONArray().fluentAdd("").fluentAdd(1))
                .toString();
        Assertions.assertEquals(r2, f2);
    }

    @Test
    void testInner() {
        LinkedHashMap<String, Object> m1 = new LinkedHashMap<>();
        m1.put("key1", 1);
        m1.put("arr", Arrays.asList("1", "2", "3"));
        m1.put("map", Collections.singletonMap("k1", "k2"));
        m1.put("obj", Collections.singletonMap("a1",
                Arrays.asList("1", Arrays.asList(true, false), Collections.singletonMap("nk1", "nk2"))));

        final String j1 = JsonTemplate.obj(o -> o.putObj(m1));
        final String r1 = "{\"key1\":1,"
                          + "\"arr\":[\"1\",\"2\",\"3\"],"
                          + "\"map\":{\"k1\":\"k2\"},"
                          + "\"obj\":{\"a1\":[\"1\",[true,false],{\"nk1\":\"nk2\"}]}"
                          + "}";
        Assertions.assertEquals(r1, j1);

        final String j2 = JsonTemplate.arr(o -> o.addObj(m1));
        final String r2 = "[" + r1 + "]";
        Assertions.assertEquals(r2, j2);
    }

    @Test
    void testPrimitive() {
        final String j1 = JsonTemplate.obj(o -> o
                .putVal("ba", new boolean[]{true, false})
                .putVal("ia", new int[]{1, 2}));

        final String r1 = "{\"ba\":[true,false],"
                          + "\"ia\":[1,2]"
                          + "}";
        Assertions.assertEquals(r1, j1);

        final String j2 = JsonTemplate.arr(o -> o
                .addVal(new boolean[]{true, false})
                .addVal(new int[]{1, 2}));

        final String r2 = "[[true,false],"
                          + "[1,2]"
                          + "]";
        Assertions.assertEquals(r2, j2);
    }
}
