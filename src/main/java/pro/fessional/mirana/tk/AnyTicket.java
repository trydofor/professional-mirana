package pro.fessional.mirana.tk;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.util.Objects;

/**
 * String parser only
 *
 * @author trydofor
 * @since 2021-01-25
 */
public class AnyTicket implements Ticket.Mutable {

    private static final long serialVersionUID = 19791023L;

    protected String pubMod = Null.Str;
    protected long pubExp = 0;
    protected int pubSeq = 0;
    protected String bizPart = Null.Str;
    protected String sigPart = Null.Str;

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
    @NotNull
    public String serialize(char sep1, char sep2) {
        StringBuilder sb = new StringBuilder(pubMod.length() + bizPart.length() + sigPart.length() + 40);
        sb.append(pubMod);
        sb.append(sep1).append(pubExp);
        sb.append(sep1).append(pubSeq);
        if (!bizPart.isEmpty()) {
            sb.append(sep2).append(bizPart);
        }
        sb.append(sep2).append(sigPart);
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
