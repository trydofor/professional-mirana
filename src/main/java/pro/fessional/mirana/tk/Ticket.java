package pro.fessional.mirana.tk;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * <pre>
 * 特点是短小，可过期，可踢人，可验签，有一定业务意义，代替无意义的随机token。
 * 其中，Data后缀为业务语义，Part后缀为传输语义，不同视角的布局为，
 * 业务布局：SigData + `.` + SigPart
 * - SigData = PubPart + (`.` + BizData)?
 * - PubPart = `mod` + `-` + `due` + `-` + `seq`
 * - BizData: 业务数据，如明文的Json
 *
 * 传输布局：PubPart + `.` + SecPart
 * - SecPart = (BizPart + `.`)? + SigPart
 * - BizPart: 加密后的BizData
 * - SigPart: 签名数据，对SigData数据签名
 *
 * `mod`: 约定模式，加密及签名，BizPart类型等，用于反序列化。英数
 * `due`: 有效期，从1970-01-01起的秒数，用于判断时间过期。正整数
 * `seq`: 签发序号，用于判定新旧，业务过期，正整数
 * `salt` - 加密或签名秘钥，如`加盐`，对称秘钥，非对称私钥。
 *
 * 解析时，比较容易理解的步骤为
 * ①把Ticket以第1个'.'分割成2段：PubData和SecData
 * ②把第1段以2个'-'分割为3段：PubMod, PubDue, PubSeq
 * ③把第2段以1个'.'分割为2段 BizPart, SigPart
 * ④以PubMod约定，解密BizPart，验证SigData签名
 * </pre>
 *
 * @author trydofor
 * @since 2021-01-24
 */
public interface Ticket extends Serializable {
    /**
     * 约定模式，包括加密算法，签名方式，是BizPart结构的约定，支持[az09]
     */
    @NotNull
    String getPubMod();

    /**
     * 有效期，从1970-01-01起的秒数，不可为负数
     */
    long getPubDue();

    /**
     * 签发序号，递增不连续，一般小于10有特别定义，不可为负数
     */
    int getPubSeq();

    /**
     * 业务数据，可选（空为不存在），不超过1k，
     * 格式由PubMod定义。base64为url-safe，NoPad格式，支持[az09-_]
     */
    @NotNull
    String getBizPart();

    /**
     * 签名部分 ，一般50字符内，保证Ticket不被篡改，
     * 格式由PubMod定义。base64为url-safe，NoPad格式，支持[az09-_]
     */
    @NotNull
    String getSigPart();

    /**
     * 获得参与签名的数据，即ticket中`pub-part` + (`.` + `biz-part`)?。
     * 当parse ticket string时，缓存原始ticket的biz-data部分。
     *
     * @return 签名部分
     */
    @NotNull
    default String getSigData() {
        final String biz = getBizPart();
        if (biz.isEmpty()) {
            return getPubMod() + "-" + getPubDue() + "-" + getPubSeq();
        }
        else {
            return getPubMod() + "-" + getPubDue() + "-" + getPubSeq() + "." + biz;
        }
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
        sb.append('-').append(getPubDue());
        sb.append('-').append(getPubSeq());
        if (!biz.isEmpty()) {
            sb.append('.').append(biz);
        }
        sb.append('.').append(sig);
        return sb.toString();
    }

    interface Mutable extends Ticket {
        void setPubMod(String mod);

        void setPubDue(long exp);

        void setPubSeq(int seq);

        void setBizPart(String biz);

        void setSigPart(String sig);

        /**
         * 复制tk中part的内容，不包括sigData。
         *
         * @param tk 来源ticket
         */
        default void copyPart(Ticket tk) {
            if (tk == null) return;
            this.setPubMod(tk.getPubMod());
            this.setPubDue(tk.getPubDue());
            this.setPubSeq(tk.getPubSeq());
            this.setBizPart(tk.getBizPart());
            this.setSigPart(tk.getSigPart());
        }
    }
}
