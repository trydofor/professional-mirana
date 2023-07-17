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
     * get the next LightId by its block and name, default timeout is 1 second.
     *
     * @param name  Id name
     * @param block block aka. data center in db cluster
     * @return LightId
     * @throws NoSuchElementException   if name not exist.
     * @throws IllegalArgumentException block is out of range.
     * @throws IllegalStateException    sequence is out of range, or the bad internal state.
     * @throws TimeoutRuntimeException  default timeout is 1 second.
     */
    default long next(@NotNull String name, int block) {
        return next(name, block, 1000);
    }

    /**
     * get the next LightId by its block and name ,within the specify timeout(ms)
     *
     * @param name    Id name
     * @param block   block aka. data center in db cluster
     * @param timeout timeout in ms, 0(unlimited) is NOT recommended, as much as possible to meet the business performance requirements.
     * @return LightId
     * @throws NoSuchElementException   if name not exist.
     * @throws IllegalArgumentException block is out of range.
     * @throws IllegalStateException    sequence is out of range, or the bad internal state.
     * @throws TimeoutRuntimeException  timeout.
     */
    long next(@NotNull String name, int block, long timeout);


    /**
     * the Loader to load LightId via Segment
     */
    interface Loader {
        /**
         * Returns the count of sequence, not less than the required count (can be more) sequence.
         * If the count is often greater than the default, it is recommended to update the default.
         *
         * @param name  Id name
         * @param block block aka. data center in db cluster
         * @param count the count to require, should return not less than the count.
         * @return valid segment
         * @throws NoSuchElementException if name not exist.
         */
        @NotNull
        Segment require(@NotNull String name, int block, int count);

        /**
         * preload all LightId of current block. and the producer decides how much id to provide.
         *
         * @param block load all or throw errors if not exist.
         * @return all LightId
         */
        @NotNull
        List<Segment> preload(int block);
    }

    /**
     * Immutable LightId Segment
     */
    class Segment {
        private final String name;
        private final int block;
        private final long head;
        private final long foot;

        /**
         * Immutable Fragment
         *
         * @param name  name
         * @param block block id
         * @param head  start point (include)
         * @param foot  end point (include)
         */
        public Segment(String name, int block, long head, long foot) {
            if (name == null) throw new NullPointerException("name is null");
            if (head > foot) throw new IllegalArgumentException("head=" + head + " is bigger than foot=" + foot);
            if (!valid(block, head))
                throw new IllegalArgumentException("block=" + block + ", head=" + head + " is out of range");
            if (!valid(block, foot))
                throw new IllegalArgumentException("block=" + block + ", foot=" + foot + " is out of range");

            this.name = name;
            this.block = block;
            this.head = head;
            this.foot = foot;
        }

        /**
         * Id's name
         */
        public String getName() {
            return name;
        }

        /**
         * Id's block
         */
        public int getBlock() {
            return block;
        }

        /**
         * start point (include)
         */
        public long getHead() {
            return head;
        }

        /**
         * end point (include)
         */
        public long getFoot() {
            return foot;
        }
    }
}
