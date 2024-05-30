package pro.fessional.mirana.text;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.bits.Base64;

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
        final String j1 = JsonTemplate.obj(1000, obj -> {});
        Assertions.assertEquals("{}", j1);
        final String j2 = JsonTemplate.arr(arr -> {});
        Assertions.assertEquals("[]", j2);
    }

    @Test
    void testArray() {
        byte[] bytes = "ab".getBytes();
        final String j1 = JsonTemplate.obj(1000, obj -> {
            obj.putVal("charArray", "ab".toCharArray());
            obj.putVal("byteArray", bytes);
            obj.putVal("boolArray", new boolean[]{true, false});
            obj.putVal("shortArray", new short[]{1, 2});
            obj.putVal("intArray", new int[]{3, 4});
            obj.putVal("longArray", new long[]{3, 4});
            obj.putVal("floatArray", new float[]{5.0F, 6.0F});
            obj.putVal("doubleArray", new double[]{7.0D, 8.0D});
            obj.putArr("objArr", new Object[]{1, 2});
        });
        Assertions.assertEquals("{"
                                + "\"charArray\":\"ab\","
                                + "\"byteArray\":\"" + Base64.encode(bytes) + "\","
                                + "\"boolArray\":[true,false],"
                                + "\"shortArray\":[1,2],"
                                + "\"intArray\":[3,4],"
                                + "\"longArray\":[3,4],"
                                + "\"floatArray\":[5.0,6.0],"
                                + "\"doubleArray\":[7.0,8.0],"
                                + "\"objArr\":[1,2]"
                                + "}", j1);
    }

    @Test
    void testSimple() {
        final String j1 = JsonTemplate.obj(1000, obj -> {
            obj.putVal("msgtype", "mar\"kd\\own");
            obj.putVal("success", true);
            obj.putVal("code", null);
        });
        Assertions.assertEquals("{\"msgtype\":\"mar\\\"kd\\\\own\",\"success\":true}", j1);
        final String j2 = JsonTemplate.arr(1000, arr -> {
            arr.addVal(true);
            arr.addVal(new Object[]{1, 2})
               .addVal("mar\"kd\\own");
        });
        Assertions.assertEquals("[true,1,2,\"mar\\\"kd\\\\own\"]", j2);
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
                                              .putVal("msgtype", "text"))
                                      .replaceAll("[ \n]+", "");
        Assertions.assertEquals(r1, j1);

        final String f1 = new JSONObject()
                .fluentPut("at", new JSONObject()
                        .fluentPut("atMobiles", new JSONArray()
                                .fluentAdd("180xxxxxx"))
                        .fluentPut("isAtAll", false))
                .fluentPut("text", new JSONObject()
                        .fluentPut("content", "Look how the prey scatters before us."))
                .fluentPut("msgtype", "text")
                .toString()
                .replaceAll("[ \n]+", "");
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
        m1.put("arr", new String[]{"1", "2", "3"});
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
