package pro.fessional.mirana.stat;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.fessional.mirana.stat.GitStat.STAT_WEEK_YEAR;

/**
 * @author trydofor
 * @since 2020-09-16
 */
@Disabled("Manual")
public class GitStatTest {

    @Test
    public void mysql() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("test");
        dataSource.setUser("trydofor");
        dataSource.setPassword("moilioncircle");
        dataSource.setServerName("127.0.0.1");

        File workDir = new File("/Users/trydofor/Workspace/github.com/pro.fessional.wings");
        List<GitStat.S> infos = GitStat.logAll(workDir, "2 weeks ago", "2020-10-01");
        GitStat.saveMysql(infos, "git_log_wings", dataSource);
    }


    @Test
    public void stat() {
        File workDir = new File("/Users/trydofor/Workspace/wings-backend");
        List<GitStat.S> infos = GitStat.logAll(workDir, null);
        Map<String, String> alias = new HashMap<>();
        alias.put("trydofor@gmail.com", "trydofor");
        alias.put("trydofor@github.com", "trydofor");
        GitStat.stat(infos, STAT_WEEK_YEAR, alias, false);
    }

    @Test
    public void test() {
        byte[] c = "Tst\344\270\255\346\226\207\344\271\237\345\210\206\350\241\250Record.java".getBytes(StandardCharsets.ISO_8859_1);
        String x = new String(c, StandardCharsets.UTF_8);
        SystemOut.println(x);

        String s = GitStat.trimFile("Tst\\344\\270\\255\\346\\226\\207\\344\\271\\237\\345\\210\\206\\350\\241\\250Record.java");
        SystemOut.println(s);
        assertEquals(x, s);
    }
}
