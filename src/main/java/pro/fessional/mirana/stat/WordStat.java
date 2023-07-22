package pro.fessional.mirana.stat;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Statistics of word frequency in the project to solve the problem of code naming problem.
 *
 * @author trydofor
 * @since 2022-09-27
 */
public class WordStat {


    public void printJavax(@NotNull Path path) {
        final Map<String, Integer> map = statJavaxUtf8(path);
        printWord(map, new PrintWriter(System.out), en -> en.getKey().length() <= 10 || en.getValue() >= 2);
    }

    public void printWord(Map<String, Integer> stats, Writer writer, Predicate<Map.Entry<String, Integer>> filter) {
        List<Map.Entry<Integer, String>> list = new ArrayList<>(stats.size());
        int maxLen = 0;

        for (Map.Entry<String, Integer> en : stats.entrySet()) {
            if (!filter.test(en)) continue;

            final String wd = en.getKey();
            if (wd.length() > maxLen) {
                maxLen = wd.length();
            }
            list.add(new AbstractMap.SimpleEntry<>(en.getValue(), wd));
        }

        //
        list.sort((o1, o2) -> {
            final int cmp = o1.getKey().compareTo(o2.getKey());
            return cmp != 0 ? -cmp : o2.getValue().compareTo(o1.getValue());
        });

        try {
            for (Map.Entry<Integer, String> en : list) {
                final String wd = en.getValue();
                final int len = wd.length();
                writer.write(wd);
                for (int i = len - 2; i < maxLen; i++) {
                    writer.write(' ');
                }
                writer.write(String.valueOf(en.getKey()));
                writer.write('\n');
                writer.flush();
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public Map<String, Integer> statJavaxUtf8(@NotNull Path path) {
        final Pattern ptn = Pattern.compile("\\.java$|\\.kt$|\\.js$|\\.ts$", Pattern.CASE_INSENSITIVE);
        return statPath(path, StandardCharsets.UTF_8, ptn, this::statWord);
    }

    /**
     * Recursive statistics including English words within 2-20 characters
     *
     * @param path     starting path
     * @param encoding StandardCharsets.UTF_8
     * @param regexp   matched files
     * @param counter  counter method
     * @return word frequency
     */
    public Map<String, Integer> statPath(@NotNull Path path, Charset encoding, @NotNull Pattern regexp, @NotNull Function<BufferedReader, Map<String, Integer>> counter) {
        final Map<String, Integer> map = new HashMap<>();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    final String fp = file.toString();
                    if (regexp.matcher(fp).find()) {
                        try (BufferedReader br = Files.newBufferedReader(file, encoding)) {
                            final Map<String, Integer> wd = counter.apply(br);
                            for (Map.Entry<String, Integer> en : wd.entrySet()) {
                                map.compute(en.getKey(), (k, v) -> v == null ? en.getValue() : v + en.getValue());
                            }
                            wd.clear();
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return map;
    }

    public Map<String, Integer> statWord(@NotNull BufferedReader br) {
        final Map<String, Integer> map = new HashMap<>();
        try {
            String ln = br.readLine();
            while (ln != null) {
                statWord(map, ln);
                ln = br.readLine();
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return map;
    }


    public void statWord(Map<String, Integer> map, String line) {
        final StringBuilder sb = new StringBuilder();
        boolean upper = false;
        for (int i = 0, ln = line.length(); i < ln; i++) {
            char c = line.charAt(i);
            if (c >= 'a' && c <= 'z') {
                sb.append(c);
                upper = false;
            }
            else if (c >= 'A' && c <= 'Z') {
                if (!upper) {
                    capture(map, sb);
                }
                char cl = (char) (c + 32);
                sb.append(cl);
                upper = true;
            }
            else {
                capture(map, sb);
                upper = false;
            }
        }
    }

    private void capture(Map<String, Integer> map, StringBuilder sb) {
        String k = sb.toString().trim();
        final int len = k.length();
        if (len > 1 && len <= 20) {
            map.compute(k, (s, v) -> v == null ? 1 : v + 1);
        }
        sb.setLength(0);
    }

    public static void main(String[] args) {
        WordStat ws = new WordStat();
        if (args.length > 0) {
            ws.printJavax(Paths.get(args[0]));
        }
        else {
            ws.printJavax(Paths.get("."));
        }
    }
}
