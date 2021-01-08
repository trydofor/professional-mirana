package pro.fessional.mirana.io;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.fessional.mirana.io.Git.STAT_WEEK_YEAR;

/**
 * @author trydofor
 * @since 2020-09-16
 */
@Disabled("手动执行")
public class GitTest {

    @Test
    public void mysql() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("test");
        dataSource.setUser("trydofor");
        dataSource.setPassword("moilioncircle");
        dataSource.setServerName("127.0.0.1");

        File workDir = new File("/Users/trydofor/Workspace/github.com/pro.fessional.wings");
        List<Git.S> infos = Git.logAll(workDir, "2 weeks ago", "2020-10-01");
        Git.saveMysql(infos, "git_log_wings", dataSource);
    }


    @Test
    public void stat(){
        File workDir = new File("/Users/trydofor/Workspace/捷特/jetplus-src/jetplus-backend");
        List<Git.S> infos = Git.logAll(workDir, null);
        Map<String,String> alias = new HashMap<>();
        alias.put("KangKang","小李飞刀");
        alias.put("小T","胡一刀");
        alias.put("叫我胖虎","胡一刀");
        alias.put("牧冬","王老五");
        alias.put("mortal","王老五");
        alias.put("morph","程咬金");
        alias.put("chengxiaojun","程咬金");
        alias.put("xuyongjie","徐州重工业");
        // 中文会出现汉字对齐问题，需要调整字体，保证1中文=2英文
        Git.stat(infos, STAT_WEEK_YEAR, alias, false);
    }

    @Test
    public void test() {
        byte[] c = "Tst\344\270\255\346\226\207\344\271\237\345\210\206\350\241\250Record.java".getBytes(StandardCharsets.ISO_8859_1);
        String x = new String(c, StandardCharsets.UTF_8);
        System.out.println(x);

        String s = Git.trimFile("Tst\\344\\270\\255\\346\\226\\207\\344\\271\\237\\345\\210\\206\\350\\241\\250Record.java");
        System.out.println(s);
        assertEquals(x, s);
    }
}