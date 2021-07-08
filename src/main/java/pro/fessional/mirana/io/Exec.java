package pro.fessional.mirana.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 单线程简单的执行 exec (Runtime, ProcessBuilder);
 * 高级操作，请使用`Apache Commons Exec`
 *
 * @author trydofor
 * @since 2020-09-14
 */
public class Exec {

    public enum Std {
        OUT,
        ERR
    }

    /**
     * 同步执行一个命令
     *
     * @param workDir 工作目录
     * @param out     out输出
     * @param cmd     命令
     * @return return code
     */
    public static int run(File workDir, StringBuilder out, List<String> cmd) {
        BiConsumer<Std, String> handler = (std, s) -> {
            if (std == Std.OUT) {
                if (out.length() > 0) out.append('\n');
                out.append(s);
            }
        };
        return run(workDir, handler, cmd);
    }

    /**
     * 同步执行一个命令
     *
     * @param workDir 工作目录
     * @param out     out输出
     * @param cmd     命令
     * @return return code
     */
    public static int run(File workDir, StringBuilder out, String... cmd) {
        BiConsumer<Std, String> handler = (std, s) -> {
            if (std == Std.OUT) {
                if (out.length() > 0) out.append('\n');
                out.append(s);
            }
        };
        return run(workDir, handler, cmd);
    }

    /**
     * 同步执行一个命令
     *
     * @param workDir 工作目录
     * @param out     out输出
     * @param cmd     命令
     * @return return code
     */
    public static int run(File workDir, List<String> out, List<String> cmd) {
        BiConsumer<Std, String> handler = (std, s) -> {
            if (std == Std.OUT) {
                out.add(s);
            }
        };
        return run(workDir, handler, cmd);
    }

    /**
     * 同步执行一个命令
     *
     * @param workDir 工作目录
     * @param out     out输出
     * @param cmd     命令
     * @return return code
     */
    public static int run(File workDir, List<String> out, String... cmd) {
        BiConsumer<Std, String> handler = (std, s) -> {
            if (std == Std.OUT) {
                out.add(s);
            }
        };
        return run(workDir, handler, cmd);
    }

    /**
     * 同步执行一个命令
     *
     * @param workDir 工作目录
     * @param handler out或err输出
     * @param cmd     命令
     * @return return code
     */
    public static int run(File workDir, BiConsumer<Std, String> handler, List<String> cmd) {
        ProcessBuilder builder = new ProcessBuilder(cmd).directory(workDir);
        return run(builder, handler);
    }

    /**
     * 同步执行一个命令
     *
     * @param workDir 工作目录
     * @param handler out或err输出
     * @param cmd     命令
     * @return return code
     */
    public static int run(File workDir, BiConsumer<Std, String> handler, String... cmd) {
        ProcessBuilder builder = new ProcessBuilder(cmd).directory(workDir);
        return run(builder, handler);
    }

    /**
     * 同步执行一个命令
     *
     * @param builder ProcessBuilder
     * @param handler out或err输出
     * @return return code
     */
    public static int run(ProcessBuilder builder, BiConsumer<Std, String> handler) {
        Process p = null;
        try {
            p = builder.start();

            if (handler != null) {
                final InputStream out = p.getInputStream();
                final InputStream err = p.getErrorStream();
                BufferedReader ord = new BufferedReader(new InputStreamReader(out, UTF_8));
                BufferedReader erd = new BufferedReader(new InputStreamReader(err, UTF_8));

                String ln;
                while (p.isAlive()) {
                    if (err.available() > 0 && (ln = erd.readLine()) != null) {
                        handler.accept(Std.ERR, ln);
                    }
                    if (out.available() > 0 && (ln = ord.readLine()) != null) {
                        handler.accept(Std.OUT, ln);
                    }
                }

                // last
                while ((ln = erd.readLine()) != null) {
                    handler.accept(Std.ERR, ln);
                }
                while ((ln = ord.readLine()) != null) {
                    handler.accept(Std.OUT, ln);
                }
            }

            return p.waitFor();
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
        finally {
            if (p != null) p.destroy();
        }
    }

    /**
     * 按空白解析命令行，支持引号块和转义 "one\" arg"
     *
     * @param line 参数行
     * @return 解析后命令行
     */
    public static List<String> arg(String line) {
        if (line == null || line.isEmpty()) return Collections.emptyList();
        List<String> args = new ArrayList<>();
        int len = line.length();
        StringBuilder buf = new StringBuilder(len);
        char qto = 0;
        boolean esc = false;
        for (int i = 0; i < len; i++) {
            char c = line.charAt(i);
            if (c == '\\') {
                if (esc) {
                    buf.append(c);
                    esc = false;
                }
                else {
                    esc = true;
                }
            }
            else if (c == '"' || c == '\'') {
                if (esc) {
                    buf.append(c);
                    esc = false;
                }
                else {
                    if (qto == 0) {
                        qto = c;
                    }
                    else {
                        if (qto == c) {
                            args.add(buf.toString());
                            buf.setLength(0);
                            qto = 0;
                        }
                        else {
                            buf.append(c);
                        }
                    }
                }
            }
            else if (c == ' ' || c == '\t') {
                if (qto > 0) {
                    buf.append(c);
                }
                else {
                    if (buf.length() > 0) {
                        args.add(buf.toString());
                        buf.setLength(0);
                    }
                }
            }
            else {
                if (esc) {
                    buf.append('\\');
                    esc = false;
                }
                buf.append(c);
            }
        }

        if (buf.length() > 0) {
            if (esc) {
                buf.append('\\');
            }
            args.add(buf.toString());
        }

        return args;
    }
}
