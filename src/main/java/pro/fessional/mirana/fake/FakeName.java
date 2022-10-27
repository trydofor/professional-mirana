package pro.fessional.mirana.fake;


import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.code.RandCode;

import java.util.concurrent.ThreadLocalRandom;


/**
 * 生成随机人名
 *
 * @author trydofor
 * @since 2021-03-13.
 */
public abstract class FakeName {

    /**
     * 生成2-4字的中文名字
     *
     * @return 名字
     */
    @NotNull
    public static String chinese() {
        @SuppressWarnings("RandomModInteger")
        final int s = Math.abs(ThreadLocalRandom.current().nextInt() % 3);
        return RandCode.sur(1) + RandCode.cjk(s + 1);
    }

    /**
     * 生成len字的中文名字
     *
     * @param len 姓名总长
     * @return 名字
     */
    @NotNull
    public static String chinese(int len) {
        return RandCode.sur(1) + RandCode.cjk(Math.max(1, len - 1));
    }
}
