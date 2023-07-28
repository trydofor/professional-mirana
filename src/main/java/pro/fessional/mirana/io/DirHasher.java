package pro.fessional.mirana.io;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ext3 has a limit of 32000 directories, ext4 removes this limit.
 * The max number of files in a single directory on ext3 and ext4 is not limited to the specified number
 * but to the number of inode in the file system.
 *
 * @author trydofor
 * @since 2020-12-25
 */
public class DirHasher {

    public static final int MAX_FILE = 30000;
    private static final AtomicLong SEQ = new AtomicLong(System.currentTimeMillis());

    /**
     * use the serial started from milliseconds as fileId to separate subdirectories.
     * Note that multiple jvm may create the same root directory.
     */
    @NotNull
    public static File mkdirs(File root) {
        long fileId = SEQ.incrementAndGet();
        return mkdirs(root, fileId);
    }

    /**
     * Separate subdirectories by fileId (serial, mills)
     */
    @NotNull
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
     * Separate subdirectories by fileId (serial, mills)
     */
    @NotNull
    public static String mkdirs(long fileId) {
        StringBuilder sb = new StringBuilder("/");
        while (fileId > MAX_FILE) {
            sb.append(fileId % MAX_FILE).append("/");
            fileId = fileId / MAX_FILE;
        }
        return sb.toString();
    }

}
