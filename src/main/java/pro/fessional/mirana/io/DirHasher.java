package pro.fessional.mirana.io;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ext3有32000最大目录限制，ext4取消了这一限制
 * ext3、ext4系统下单个目录的最大文件数无特别的限制，是受限于所在文件系统的inode数
 *
 * @author trydofor
 * @since 2020-12-25
 */
public class DirHasher {

    public static final int MAX_FILE = 30000;
    private static final AtomicLong SEQ = new AtomicLong(System.currentTimeMillis());

    /**
     * 从系统启动毫秒数为起点计数，作为fileId分隔子目录。注意多个jvm同root目录可能重复
     *
     * @param root 根目录
     * @return 建立好的子目录
     */
    public static File mkdirs(File root) {
        long filedId = SEQ.incrementAndGet();
        return mkdirs(root, filedId);
    }

    /**
     * 按fileId分隔子目录
     *
     * @param root   根目录
     * @param fileId 文件id，如序号，毫秒数等
     * @return 建立好的子目录
     */
    public static File mkdirs(File root, long fileId) {
        String sub = mkdirs(fileId);
        File dir = new File(root, sub);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 按fileId分隔子目录
     *
     * @param fileId 文件id，如序号，毫秒数等
     * @return 子目录
     */
    public static String mkdirs(long fileId) {
        StringBuilder sb = new StringBuilder("/");
        while (fileId > MAX_FILE) {
            sb.append(fileId % MAX_FILE).append("/");
            fileId = fileId / MAX_FILE;
        }
        return sb.toString();
    }

}
