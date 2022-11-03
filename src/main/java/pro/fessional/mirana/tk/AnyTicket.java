package pro.fessional.mirana.tk;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.util.Objects;

/**
 * 仅对字符串做对应的解析
 *
 * @author trydofor
 * @since 2021-01-25
 */
public class AnyTicket implements Ticket.Mutable {

    private static final long serialVersionUID = 19791023L;

    private String pubMod = Null.Str;
    private long pubExp = 0;
    private int pubSeq = 0;
    private String bizPart = Null.Str;
    private String sigPart = Null.Str;

    private String sigData = Null.Str;

    public AnyTicket() {
    }

    public AnyTicket(String pubMod) {
        this.pubMod = Null.notNull(pubMod);
    }

    public AnyTicket(String pubMod, long pubExp, int pubSeq, String bizPart, String sigPart) {
        this.pubMod = Null.notNull(pubMod);
        this.pubExp = Math.max(0, pubExp);
        this.pubSeq = Math.max(0, pubSeq);
        this.bizPart = Null.notNull(bizPart);
        this.sigPart = Null.notNull(sigPart);
    }

    @NotNull
    @Override
    public String getPubMod() {
        return pubMod;
    }

    @Override
    public void setPubMod(String pubMod) {
        this.pubMod = Null.notNull(pubMod);
    }

    @Override
    public long getPubDue() {
        return pubExp;
    }

    @Override
    public void setPubDue(long pubExp) {
        this.pubExp = Math.max(0, pubExp);
    }

    @Override
    public int getPubSeq() {
        return pubSeq;
    }

    @Override
    public void setPubSeq(int pubSeq) {
        this.pubSeq = Math.max(0, pubSeq);
    }

    @NotNull
    @Override
    public String getBizPart() {
        return bizPart;
    }

    @Override
    public void setBizPart(String bizPart) {
        this.bizPart = Null.notNull(bizPart);
    }

    @NotNull
    @Override
    public String getSigPart() {
        return sigPart;
    }

    @Override
    public void setSigPart(String sigPart) {
        this.sigPart = Null.notNull(sigPart);
    }

    @Override
    public @NotNull String getSigData(boolean build) {
        if (build || sigData.isEmpty()) {
            return Ticket.Mutable.super.getSigData(false);
        }
        else {
            return sigData;
        }
    }

    @Override
    public void setSigData(String sig) {
        sigData = Null.notNull(sig);
    }

    @Override
    @NotNull
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(pubMod);
        sb.append('-').append(pubExp);
        sb.append('-').append(pubSeq);
        if (!bizPart.isEmpty()) {
            sb.append('.').append(bizPart);
        }
        sb.append('.').append(sigPart);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyTicket anyTicket = (AnyTicket) o;
        return pubExp == anyTicket.pubExp &&
               pubSeq == anyTicket.pubSeq &&
               pubMod.equals(anyTicket.pubMod) &&
               bizPart.equals(anyTicket.bizPart) &&
               sigPart.equals(anyTicket.sigPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pubMod, pubExp, pubSeq, bizPart, sigPart);
    }


    @Override
    public String toString() {
        return "AnyTicket{" +
               "pubMod='" + pubMod + '\'' +
               ", pubExp=" + pubExp +
               ", pubSeq=" + pubSeq +
               ", bizPart='" + bizPart + '\'' +
               ", sigPart='" + sigPart + '\'' +
               '}';
    }
}
