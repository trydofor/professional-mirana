package pro.fessional.mirana.tk;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * <pre>
 * A short, expireable, kickable, verifiable, with some business meaning, instead of meaningless random token.
 * Where `Data` suffix is business semantics, `Part` suffix is transfer semantics, and the layout of different perspectives is.
 *
 * Business layout: SigData + `.` + SigPart
 * - SigData = PubPart + (`.` + BizData)?
 * - PubPart = `mod` + `-` + `due` + `-` + `seq`
 * - BizData: business data, e.g. plaintext Json
 *
 * Transfer layout: PubPart + `.` + SecPart
 * - SecPart = (BizPart + `.`)? + SigPart
 * - BizPart: encrypted BizData
 * - SigPart: Signature data, sign the SigData data.
 *
 * `mod`: convention mode, encryption and signature, BizPart type, etc. for deserialization. English and number
 * `due`: expiration date, number of seconds since 1970-01-01, used to determine time expiration. Positive integers
 * `seq`: serial number, used to determine old or new, business expiration, positive integer
 * `salt`: encryption or signing secret key, such as symmetric secret key, asymmetric private key.
 *
 * When parsing, the easier to understand steps are,
 * (1) Split the Ticket with the 1st `.` into 2 segments: PubData and SecData.
 * (2) Split the 1st segment into 3 parts with 2 `-`: PubMod, PubDue, PubSeq.
 * (3) Split the 2nd segment into 2 parts with 1 `.`: BizPart, SigPart
 * (4) Decrypt BizPart and verify SigData signature with PubMod convention.
 *
 * </pre>
 *
 * @author trydofor
 * @since 2021-01-24
 */
public interface Ticket extends Serializable {
    /**
     * The convention schema, including encryption algorithm, signature method,
     * is a convention of the BizPart structure, supported [az09].
     */
    @NotNull
    String getPubMod();

    /**
     * Expiration, in seconds from 1970-01-01, not negative
     */
    long getPubDue();

    /**
     * Serial number, non-negative, incremental and non-consecutive.
     * generally less than 10 with special definitions.
     */
    int getPubSeq();

    /**
     * Biz-data part, optional(empty means no biz-data), less than 1k.
     * Format defined by PubMod. base64 is url-safe, NoPad format, supports [az09-_]
     */
    @NotNull
    String getBizPart();

    /**
     * Signature part, usually within 50 characters, to ensure that the Ticket has not been tampered with.
     * Schema defined by PubMod. base64 is url-safe, NoPad format, supports [az09-_]
     */
    @NotNull
    String getSigPart();

    /**
     * Get the signature data, i.e. `pub-part` + (`. ` + `biz-part`)?
     * When parsing the ticket string, cache the biz-data of the original ticket.
     *
     * @return the signature part
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
     * serialize the Ticket
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
         * Copy the contents of part in tk, excluding sigData.
         *
         * @param tk source ticket
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
