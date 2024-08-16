package pro.fessional.mirana.stat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.io.InputStreams;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author trydofor
 * @since 2024-08-08
 */
class LogStatTest {

    @Test
    void stat() throws IOException {
        final String log = "target/test-classes/log-stat.txt";
        final long from = 0;
        final LogStat.Word[] wd;
        wd = new LogStat.Word[2];
        wd[0] = new LogStat.Word();
        wd[0].range2 = 60;
        wd[0].bytes = "WARN".getBytes(StandardCharsets.UTF_8);
        wd[1] = new LogStat.Word();
        wd[1].range2 = 60;
        wd[1].bytes = "ERROR".getBytes(StandardCharsets.UTF_8);

        final long len = new File(log).length();

        final LogStat.Stat stat = LogStat.stat(log, from, 5, 1, wd);
        long byteDone = stat.getByteDone();
        Assertions.assertEquals(len, byteDone, "file=" + len + ", stat=" + byteDone);

        String out = "######### #1 KEYWORD: ERROR #########\n"
                     + "2021-07-11 00:16:22.890 ERROR 24430 --- [XNIO-1 task-5] c.j.bbl.front.controller.UserController  : get user info error !\n"
                     + "a11111111111111111\n"
                     + "  a22222222222222222\n"
                     + ".\n"
                     + "a44444444444444444\n"
                     + "  a55555555555555555\n"
                     + "a66666666666666666\n"
                     + "a77777777777777777\n"
                     + "a88888888888888888\n"
                     + "######### #2 KEYWORD: WARN #########\n"
                     + "2021-07-11 00:35:42.397  WARN 24430 --- [pool-1355-thread-3] c.j.b.c.s.f.w.rate.FedexRateService      : PAYOR_ACCOUNT_PACKAGE:TotalBase=1361.55,TotalSur=126.15,TotalNet=1487.7 \n"
                     + "b11111111111111111\n"
                     + "b22222222222222222\n"
                     + "b33333333333333333\n"
                     + "b44444444444444444\n"
                     + "b55555555555555555\n"
                     + "######### #3 KEYWORD: WARN #########\n"
                     + "2021-07-11 00:42:42.244  WARN 24430 --- [pool-1358-thread-3] c.j.b.c.s.f.w.rate.FedexRateService      : PAYOR_ACCOUNT_SHIPMENT:TotalBase=64.0,TotalSur=5.95,TotalNet=69.95\n"
                     + "c11111111111111111\n"
                     + "c22222222222222222\n"
                     + "2021-07-11 00:42:42.244  ERROR 24430 --- [pool-1358-thread-3] c.j.b.c.s.f.w.rate.FedexRateService      : PAYOR_ACCOUNT_SHIPMENT:TotalBase=64.0,TotalSur=5.95,TotalNet=69.95\n";

        // the last ERROR, in previous WARN's preview
        String out1 = InputStreams.readText(Files.newInputStream(Paths.get(stat.getPathOut())));
        Assertions.assertEquals(out, out1, stat.getPathOut());
    }
}