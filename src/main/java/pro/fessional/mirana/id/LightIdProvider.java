package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.pain.TimeoutRuntimeException;

import java.util.List;
import java.util.NoSuchElementException;

import static pro.fessional.mirana.id.LightIdUtil.valid;

/**
 * @author trydofor
 * @since 2019-05-26
 */
public interface LightIdProvider {

    /**
     * 根据区块和名字获得 LightId，默认5秒超时。
     *
     * @param name  Id名字
     * @param block 区块编号，生产中心，分库关键
     * @return LightId
     * @throws NoSuchElementException   name不存在。
     * @throws IllegalArgumentException block超出范围。
     * @throws IllegalStateException    sequence超出范围，或者内部状态错误。
     * @throws TimeoutRuntimeException  超时异常,默认1秒
     */
    default long next(@NotNull String name, int block) {
        return next(name, block, 1000);
    }

    /**
     * 根据区块和名字获得 LightId
     *
     * @param name    Id名字
     * @param block   区块编号，生产中心，分库关键
     * @param timeout 超时毫秒数，建议不要设置(0：无限等),尽量符合业务性能要求。
     * @return LightId
     * @throws NoSuchElementException   name不存在。
     * @throws IllegalArgumentException block超出范围
     * @throws IllegalStateException    sequence超出范围，或者内部状态错误。
     * @throws TimeoutRuntimeException  超时异常
     */
    long next(@NotNull String name, int block, long timeout);


    /**
     * LightId 加载器，通过Segment实现
     */
    interface Loader {
        /**
         * 返回总数量，不少于请求数量(可以多)的 sequence。
         * 如果count频繁大于数据库默认值，建议更新默认step。
         *
         * @param name  名称
         * @param block 区块编号，生产中心，分库关键
         * @param count 请求的数量，返回值不少于该数量
         * @return 可用的序号
         * @throws NoSuchElementException name不存在。
         */
        @NotNull
        Segment require(@NotNull String name, int block, int count);

        /**
         * 预加载当前block下所有LightId，提供id的数量，生产者决定
         *
         * @param block 区块编号，生产中心，分库关键，不存在时，可以load全部或报错。
         * @return 所有可用序号
         */
        @NotNull
        List<Segment> preload(int block);
    }

    /**
     * LightId 片段
     */
    class Segment {
        private final String name;
        private final int block;
        private final long head;
        private final long foot;

        /**
         * 不可变的片段
         *
         * @param name  名字
         * @param block 区块
         * @param head  起点（包含）
         * @param foot  终点（包含）
         */
        public Segment(String name, int block, long head, long foot) {
            if (name == null) throw new NullPointerException("name is null");
            if (head > foot) throw new IllegalArgumentException("head=" + head + " is bigger than foot=" + foot);
            if (!valid(block, head)) throw new IllegalArgumentException("block=" + block + ", head=" + head + " is out of range");
            if (!valid(block, foot)) throw new IllegalArgumentException("block=" + block + ", foot=" + foot + " is out of range");

            this.name = name;
            this.block = block;
            this.head = head;
            this.foot = foot;
        }

        /**
         * 名字
         *
         * @return 名字
         */
        public String getName() {
            return name;
        }

        /**
         * 区块
         *
         * @return 区别
         */
        public int getBlock() {
            return block;
        }

        /**
         * 起点（包含）
         *
         * @return 起点（包含）
         */
        public long getHead() {
            return head;
        }

        /**
         * 终点（包含）
         *
         * @return 终点（包含）
         */
        public long getFoot() {
            return foot;
        }
    }
}
