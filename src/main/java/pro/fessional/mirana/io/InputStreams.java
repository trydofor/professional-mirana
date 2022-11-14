package pro.fessional.mirana.io;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 更多参考 apache IOUtils 和
 * guava CharStreams
 *
 * @author trydofor
 * @since 2020-06-02
 */
public class InputStreams {

    // 2K chars (4K bytes)
    private static final int DEFAULT_BUF_SIZE = 0x800;

    /**
     * 读取全部Byte，并关闭
     *
     * @param is 输入流
     * @return byte内容
     */
    public static byte @NotNull [] readBytes(InputStream is) {
        if (is == null) return Null.Bytes;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream(is.available());
            readBytes(os, is);
            return os.toByteArray();
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to read text", e);
        }
    }

    /**
     * 读取全部Byte，并关闭
     *
     * @param os 输出流
     * @param is 输入流
     */
    public static void readBytes(OutputStream os, InputStream is) {
        if (is == null) return;

        try {
            int len;
            byte[] data = new byte[DEFAULT_BUF_SIZE];
            while ((len = is.read(data)) != -1) {
                os.write(data, 0, len);
            }
            os.flush();
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to read text", e);
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                //ignore
            }
        }
    }


    /**
     * 读取全部文本，并关闭
     *
     * @param is 输入流
     * @param cs 字符集
     * @return 文本内容
     */
    @NotNull
    public static String readText(InputStream is, Charset cs) {
        if (is == null) return Null.Str;
        if (cs == null) cs = StandardCharsets.UTF_8;

        StringBuilder sb = new StringBuilder();
        readText(sb, is, cs);
        return sb.toString();
    }

    /**
     * 读取全部文本，并关闭
     *
     * @param sb 缓冲
     * @param is 输入流
     * @param cs 字符集
     */
    public static void readText(StringBuilder sb, InputStream is, Charset cs) {
        if (is == null) return;
        if (cs == null) cs = StandardCharsets.UTF_8;

        char[] buf = new char[DEFAULT_BUF_SIZE];
        int len;
        try (Reader rd = new InputStreamReader(is, cs)) {
            while ((len = rd.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to read text", e);
        }
    }

    /**
     * 读取全部，并关闭
     */
    @NotNull
    public static String readText(InputStream is) {
        return readText(is, StandardCharsets.UTF_8);
    }


    /**
     * 读取全部文本行，并关闭
     *
     * @param is 输入流
     * @param cs 字符集
     * @return 文本行
     */
    @NotNull
    public static List<String> readLine(InputStream is, Charset cs) {
        if (is == null) return Collections.emptyList();
        if (cs == null) cs = StandardCharsets.UTF_8;

        List<String> rt = new ArrayList<>();
        readLine(rt, is, cs);
        return rt;
    }

    /**
     * 读取全部文本行，并关闭
     *
     * @param out 行接收器
     * @param is  输入流
     * @param cs  字符集
     */
    public static void readLine(List<String> out, InputStream is, Charset cs) {
        if (is == null) return;
        if (cs == null) cs = StandardCharsets.UTF_8;

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, cs))) {
            String line = rd.readLine();
            while (line != null) {
                out.add(line);
                line = rd.readLine();
            }
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to read line", e);
        }
    }

    /**
     * 读取全部，并关闭
     */
    @NotNull
    public static List<String> readLine(InputStream is) {
        return readLine(is, StandardCharsets.UTF_8);
    }

    /**
     * 写入文件，并关闭
     *
     * @param is   输入流
     * @param file 文件
     */
    public static void saveFile(InputStream is, File file) {
        if (is == null || file == null) return;

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buf = new byte[DEFAULT_BUF_SIZE];
            int len;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        }
        catch (IOException e) {
            throw new IllegalStateException("failed to save file " + file, e);
        }
    }

    /**
     * 写入临时文件，并关闭
     *
     * @param is        输入流
     * @param prefix    文件前缀
     * @param delOnExit deleteOnExit
     * @return 文本行
     */
    @NotNull
    public static File saveTemp(InputStream is, String prefix, boolean delOnExit) {
        try {
            File file = Files.createTempFile(prefix, null).toFile();
            saveFile(is, file);
            if (delOnExit) {
                file.deleteOnExit();
            }
            return file;
        }
        catch (IOException e) {
            throw new IllegalStateException("failed to save temp", e);
        }
    }

    /**
     * 读取全部，并关闭
     */
    @NotNull
    public static File saveTemp(InputStream is, String prefix) {
        return saveTemp(is, prefix, false);
    }

    /**
     * 读取全部，并关闭
     */
    @NotNull
    public static File saveTemp(InputStream is) {
        return saveTemp(is, "mirana-", false);
    }
}
