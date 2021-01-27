package pro.fessional.mirana.tk;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 特点是短小，可过期，可踢人，可验签，有一定业务意义，代替无意义的随机token
 *
 * @author trydofor
 * @since 2021-01-24
 */
public interface Ticket extends Serializable {
    /**
     * az09，约定模式，对加密，签名，biz结构的约定
     *
     * @return 约定模式
     */
    @NotNull
    String getPubMod();

    /**
     * 过期时点，从1970-01-01起的秒数
     *
     * @return 秒数，不可为负数
     */
    long getPubExp();

    /**
     * 签发序号，递增不连续，一般小于10有特别定义。
     *
     * @return 序号，不可为负数
     */
    int getPubSeq();

    /**
     * `az09-_`，可选，不超过1k，在mod中定义。
     * base64为url-safe，no pad格式
     *
     * @return 业务部分，空串为不存在此部分
     */
    @NotNull
    String getBizPart();

    /**
     * 签名部分(sig-part) - `az09-_`，可选，一般50字符内，在mod中定义。
     * base64为url-safe，no pad格式
     *
     * @return 签名部分，空串为不存在此部分
     */
    @NotNull
    String getSigPart();

    /**
     * 获得参与签名的数据，即ticket中`pub-part` + (`.` + `biz-part`)?。
     * 当parse ticket string时，缓存原始ticket的biz-data部分。
     *
     * @param build 是否重新构建，否则优先使用设定值，无设定才计算。
     * @return 签名部分
     */
    @NotNull
    default String getSigData(boolean build) {
        final String biz = getBizPart();
        if (biz.isEmpty()) {
            return getPubMod() + "-" + getPubExp() + "-" + getPubSeq();
        } else {
            return getPubMod() + "-" + getPubExp() + "-" + getPubSeq() + "." + biz;
        }
    }

    /**
     * 验证签名是否正确，verify = null, return true.
     * 验签可能加盐，获得盐可能需要解密bizPart。
     * 默认依赖 getSigData(false)，用于验证parse来的tk
     *
     * @param verify 输入 SigData 获得base64签名
     * @return 签名是否正确
     * @see #getSigData(boolean)
     */
    default boolean verifySig(Function<String, String> verify) {
        if (verify == null) return true;
        final String sig = verify.apply(getSigData(false));
        return getSigPart().equals(sig);
    }

    /**
     * 尝试解密bizPart对象
     *
     * @param decoder 目标类型
     * @param <T>     对象类型
     * @return 对象，null为无法解密
     */
    default <T> @Nullable T decodeBiz(Function<String, T> decoder) {
        return decoder == null ? null : decoder.apply(getBizPart());
    }

    /**
     * 序列化Ticket
     *
     * @return Ticket
     */
    @NotNull
    default String serialize() {
        final String mod = getPubMod();
        final String sig = getSigPart();
        if (mod.isEmpty() || sig.isEmpty()) {
            throw new IllegalStateException("sig-part or pub-mod is empty");
        }
        final String biz = getBizPart();
        StringBuilder sb = new StringBuilder(biz.length() + 100);
        sb.append(mod);
        sb.append('-').append(getPubExp());
        sb.append('-').append(getPubSeq());
        if (!biz.isEmpty()) {
            sb.append('.').append(biz);
        }
        sb.append('.').append(sig);
        return sb.toString();
    }

    interface Mutable extends Ticket {
        void setPubMod(String mod);

        void setPubExp(long exp);

        void setPubSeq(int seq);

        void setBizPart(String biz);

        void setSigPart(String sig);

        /**
         * 设置ticket参与签名的内容，即ticket中最后一个`.`以前的内容。
         * 如果是来自ticket解析，则此字段应该最后设置，以感知解析后变更。
         * 此字段为解析加速和缓存用途，验证完毕后应该清除。
         *
         * @param sig ticket参与签名的内容
         */
        default void setSigData(String sig) {}

        /**
         * 复制tk中part的内容，不包括sigData。
         *
         * @param tk 来源ticket
         */
        default void copyPart(Ticket tk) {
            if (tk == null) return;
            this.setPubMod(tk.getPubMod());
            this.setPubExp(tk.getPubExp());
            this.setPubSeq(tk.getPubSeq());
            this.setBizPart(tk.getBizPart());
            this.setSigPart(tk.getSigPart());
        }
    }
}
