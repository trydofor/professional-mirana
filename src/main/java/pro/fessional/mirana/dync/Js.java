package pro.fessional.mirana.dync;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * 通过java内置的script engine执行es6，返回js的最后一个求值结果
 * <pre>
 * Map&lt;String, Object&gt; args = new HashMap&lt;&gt;();
 * args.put("name","mirana");
 * String rst = Js.run("var msg = 'hello ' + name; msg;", args);
 * assertEquals("hello mirana", rst);
 * </pre>
 *
 * @author trydofor
 * @since 2020-10-14
 */
public class Js {

    private static final ScriptEngineManager MANAGER = new ScriptEngineManager();
    private static final ThreadLocal<ScriptEngine> ENGINES = ThreadLocal.withInitial(() -> MANAGER.getEngineByName("JavaScript"));

    public static ScriptEngine getEngine() {
        return ENGINES.get();
    }

    public static ScriptEngine newEngine() {
        return MANAGER.getEngineByName("JavaScript");
    }

    /**
     * 每次以新的引擎执行
     *
     * @param js   js脚本
     * @param vars 出事的变量
     * @param <T>  返回值类型
     * @return 脚本的最后一个语句的求值
     * @see #run(ScriptEngine, String, Map)
     */
    public static <T> T run(String js, Map<String, Object> vars) {
        return run(newEngine(), js, vars);
    }

    /**
     * 每次以新的引擎执行
     *
     * @param js  js脚本
     * @param <T> 返回值类型
     * @return 脚本的最后一个语句的求值
     * @see #run(ScriptEngine, String, Map)
     */
    public static <T> T run(String js) {
        return run(newEngine(), js, null);
    }

    /**
     * 是否以使用ThreadLocal的引擎执行
     *
     * @param js     js脚本
     * @param thread 是否缓存引擎
     * @param <T>    返回值类型
     * @return 脚本的最后一个语句的求值
     * @see #run(ScriptEngine, String, Map)
     */
    public static <T> T run(boolean thread, String js) {
        ScriptEngine engine = thread ? getEngine() : newEngine();
        return run(engine, js, null);
    }

    /**
     * 是否以使用ThreadLocal的引擎执行
     *
     * @param js     js脚本
     * @param vars   出事的变量
     * @param thread 是否thread缓存
     * @param <T>    返回值类型
     * @return 脚本的最后一个语句的求值
     * @see #run(ScriptEngine, String, Map)
     */
    public static <T> T run(boolean thread, String js, Map<String, Object> vars) {
        ScriptEngine engine = thread ? getEngine() : newEngine();
        return run(engine, js, vars);
    }

    /**
     * @param engine js引擎
     * @param js     js脚本
     * @param <T>    返回值类型
     * @return 脚本的最后一个语句的求值
     * @see #run(ScriptEngine, String, Map)
     */
    public static <T> T run(ScriptEngine engine, String js) {
        return run(engine, js, null);
    }

    /**
     * <pre>
     * Map&lt;String, Object&gt; args = new HashMap&lt;&gt;();
     * args.put("name","mirana");
     * String rst = Js.run("var msg = 'hello ' + name; msg;", args);
     * assertEquals("hello mirana", rst);
     * </pre>
     *
     * @param engine js引擎
     * @param js     js脚本
     * @param <T>    返回值类型
     * @param vars   出事的变量
     * @return 脚本的最后一个语句的求值
     */
    @SuppressWarnings("unchecked")
    public static <T> T run(ScriptEngine engine, String js, Map<String, Object> vars) {
        try {
            if (vars != null) {
                for (Map.Entry<String, Object> e : vars.entrySet()) {
                    engine.put(e.getKey(), e.getValue());
                }
            }
            return (T) engine.eval(js);
        } catch (ScriptException e) {
            throw new IllegalStateException(e);
        }
    }
}
