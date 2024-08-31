package pro.fessional.mirana.tk;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.bits.Aes;
import pro.fessional.mirana.bits.Aes256;
import pro.fessional.mirana.bits.Base64;
import pro.fessional.mirana.bits.HmacHelp;
import pro.fessional.mirana.bits.MdHelp;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.time.ThreadNow;

import javax.crypto.Mac;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author trydofor
 * @since 2021-01-26
 */
public class TicketHelp {

    public static SigFun sig(MdHelp help) {
        return new SigFun(help, null);
    }

    public static SigFun sig(MdHelp help, byte[] salt) {
        return new SigFun(help, salt);
    }

    public static SigHmac sig(HmacHelp help) {
        return new SigHmac(help);
    }

    public static class SigFun implements Function<String, String> {

        private final Supplier<MessageDigest> supplier;
        private final byte[] salt;

        public SigFun(Supplier<MessageDigest> help, byte[] salt) {
            this.supplier = help;
            this.salt = Null.notNull(salt);
        }

        public SigFun(MdHelp help, byte[] salt) {
            this.supplier = help::newOne;
            this.salt = Null.notNull(salt);
        }

        @Override
        public String apply(String s) {
            final MessageDigest md = supplier.get();
            md.update(s.getBytes());
            if (salt.length > 0) {
                md.update(salt);
            }
            final byte[] digest = md.digest();
            return Base64.encode(digest);
        }
    }

    public static class SigHmac implements Function<String, String> {

        private final Supplier<Mac> help;

        public SigHmac(Supplier<Mac> help) {
            this.help = help;
        }

        public SigHmac(HmacHelp help) {
            this.help = help::newOne;
        }

        @Override
        public String apply(String s) {
            final Mac md = help.get();
            md.update(s.getBytes());
            final byte[] digest = md.doFinal();
            return Base64.encode(digest);
        }
    }


    /**
     * Parse any accepted Ticket
     *
     * @param tk     ticket string
     * @param accept validate and return Ticket, otherwise return null
     * @return parsed Ticket or null
     */
    @SafeVarargs
    @Nullable
    public static Ticket parse(String tk, BiFunction<String, String, Ticket.Mutable>... accept) {
        return parse(Ticket.Sep1, Ticket.Sep2, tk, accept);
    }

    /**
     * Parse any accepted Ticket with separator
     *
     * @param sep1   separator of pub data
     * @param sep2   separator of parts
     * @param tk     ticket string
     * @param accept validate and return Ticket, otherwise return null
     * @return parsed Ticket or null
     */
    @SafeVarargs
    public static Ticket parse(char sep1, char sep2, String tk, BiFunction<String, String, Ticket.Mutable>... accept) {
        if (tk == null) return null;

        final int[] pos = { -1, -1, -1, -1 };
        final char[] tkn = { sep1, sep1, sep2, sep2 };
        int off = 0;
        for (int i = 0, x = tkn.length - 1; i <= x; i++) {
            final int j = tk.indexOf(tkn[i], off);
            if (j > off) {
                pos[i] = j;
                off = j + 1;
                continue;
            }

            if (i < x) {
                return null;
            }
        }

        //
        if (pos[0] < 0 || pos[1] < pos[0] || pos[2] < pos[1]
            || (pos[3] < pos[2] && pos[3] > 0)) {
            return null;
        }
        final int pl = pos[3] > 0 ? pos[3] : pos[2];
        final String sigData = tk.substring(0, pl);
        final String sigPart = tk.substring(pl + 1);

        Ticket.Mutable ticket = null;
        for (BiFunction<String, String, Ticket.Mutable> vf : accept) {
            ticket = vf.apply(sigData, sigPart);
            if (ticket != null) break;
        }
        if (ticket == null) return null;

        try {
            ticket.setPubMod(tk.substring(0, pos[0]));
            ticket.setPubDue(Long.parseLong(tk.substring(pos[0] + 1, pos[1])));
            ticket.setPubSeq(Integer.parseInt(tk.substring(pos[1] + 1, pos[2])));
            if (pos[3] > 0) {
                ticket.setBizPart(tk.substring(pos[2] + 1, pos[3]));
            }
            ticket.setSigPart(sigPart);
            return ticket;
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * aes256(biz-data, salt) + md5(sig-data) plain Md5
     */
    public static class Am0Help extends AnyHelp {

        public Am0Help(@NotNull String mod, @NotNull String salt) {
            super(mod, Aes256.of(salt), sig(MdHelp.md5));
        }

        public Am0Help(@NotNull String salt) {
            this("am0", salt);
        }
    }

    /**
     * aes256(biz-data, salt) + md5(sig-data + salt) salt Md5
     */
    public static class Am1Help extends AnyHelp {

        public Am1Help(@NotNull String mod, @NotNull String salt) {
            super(mod, Aes256.of(salt), sig(MdHelp.md5, salt.getBytes()));
        }


        public Am1Help(@NotNull String salt) {
            this("am1", salt);
        }

    }

    /**
     * aes256(biz-data, salt) + HmacSHA1(sig-data, salt) Hmac signature
     */
    public static class Ah1Help extends AnyHelp {

        public Ah1Help(@NotNull String mod, @NotNull String salt) {
            super(mod, Aes256.of(salt), sig(HmacHelp.sha1(salt.getBytes())));
        }

        public Ah1Help(@NotNull String salt) {
            this("ah1", salt);
        }
    }

    /**
     * aes256(biz-data, salt) + HmacSHA256(sig-data, salt) Hmac signature
     */
    public static class Ah2Help extends AnyHelp {

        public Ah2Help(@NotNull String mod, @NotNull String salt) {
            super(mod, Aes256.of(salt), sig(HmacHelp.sha256(salt.getBytes())));
        }

        public Ah2Help(@NotNull String salt) {
            this("ah2", salt);
        }
    }

    public static class AnyHelp implements Helper<String> {
        public final String pubMod;
        public final Aes aes256;
        public final Function<String, String> sigFun;

        public AnyHelp(String mod, Aes256 aes, Function<String, String> sig) {
            pubMod = mod;
            aes256 = aes;
            sigFun = sig;
        }

        @Override
        @NotNull
        public Ticket encode(int pubSeq, long pubDue, String bizData) {
            final AnyTicket tk = new AnyTicket();
            tk.setPubMod(pubMod);
            tk.setPubSeq(pubSeq);
            tk.setPubDue(pubDue);
            tk.setBizPart(aes256.encode64(bizData));
            tk.setSigPart(sigFun.apply(tk.getSigData()));
            return tk;
        }

        @Override
        public String decode(Ticket tk) {
            return tk == null ? null : aes256.decode64(tk.getBizPart());
        }

        @Override
        public boolean verify(Ticket tk) {
            if (tk == null) return false;
            final String sig = sigFun.apply(tk.getSigData());
            return Objects.equals(tk.getSigPart(), sig);
        }

        @Override
        public Ticket.Mutable accept(@NotNull String sigData, @NotNull String sigPart) {
            if (!sigData.startsWith(pubMod)) return null;
            final String sig = sigFun.apply(sigData);
            return sig.equals(sigPart) ? new AnyTicket() : null;
        }
    }

    public static class Builder<T extends Ticket.Mutable> {
        private final T ticket;

        public Builder(T ticket) {
            this.ticket = ticket;
        }

        @Contract("_->this")
        public Builder<T> mod(String mod) {
            ticket.setPubMod(mod);
            return this;
        }

        @Contract("_->this")
        public Builder<T> exp(long exp) {
            ticket.setPubDue(exp);
            return this;
        }

        @Contract("_,_->this")
        public Builder<T> expAfterNow(long num, TimeUnit unit) {
            ticket.setPubDue(unit.toSeconds(num) + ThreadNow.millis() / 1000);
            return this;
        }

        @Contract("_,_->this")
        public Builder<T> expAfter(long num, TimeUnit unit) {
            ticket.setPubDue(unit.toSeconds(num) + ticket.getPubDue());
            return this;
        }

        @Contract("_->this")
        public Builder<T> seq(int seq) {
            ticket.setPubSeq(seq);
            return this;
        }

        @Contract("->this")
        public Builder<T> seqIncrease() {
            ticket.setPubSeq(ticket.getPubSeq() + 1);
            return this;
        }

        @Contract("_->this")
        public Builder<T> seqAdd(int step) {
            ticket.setPubSeq(ticket.getPubSeq() + step);
            return this;
        }

        @Contract("_->this")
        public Builder<T> biz(String biz) {
            ticket.setBizPart(biz);
            return this;
        }

        @Contract("->this")
        public Builder<T> bizEmpty() {
            ticket.setBizPart(Null.Str);
            return this;
        }

        @Contract("_->this")
        public Builder<T> bizB64(byte[] biz) {
            ticket.setBizPart(Base64.encode(biz));
            return this;
        }

        @Contract("_->this")
        public Builder<T> bizB64(String biz) {
            ticket.setBizPart(Base64.encode(biz));
            return this;
        }

        public Builder<T> bizAes(String biz, String key) {
            return bizAes(biz, Aes256.of(key));
        }

        @Contract("_,_->this")
        public Builder<T> bizAes(String biz, Aes aes) {
            ticket.setBizPart(aes.encode64(biz));
            return this;
        }

        T sig(String sig) {
            ticket.setSigPart(sig);
            return ticket;
        }

        T sig(Function<String, String> sig) {
            if (ticket.getPubMod().isEmpty()) {
                throw new IllegalArgumentException("mod is null");
            }

            if (ticket.getSigPart().isEmpty() && sig != null) {
                ticket.setSigPart(sig.apply(ticket.getSigData()));
            }

            if (ticket.getSigPart().isEmpty()) {
                throw new IllegalArgumentException("sig is null");
            }

            return ticket;
        }

        T sig(MdHelp help) {
            return sig(TicketHelp.sig(help));
        }

        T sig(MdHelp help, byte[] salt) {
            return sig(TicketHelp.sig(help, salt));
        }

        T sig(HmacHelp help) {
            return sig(TicketHelp.sig(help));
        }
    }


    public interface Helper<T> {

        /**
         * encode the Ticket
         */
        @NotNull
        Ticket encode(int pubSeq, long pubDue, T bizData);

        /**
         * decode the biz object, null if can not decode
         */
        T decode(Ticket tk);

        /**
         * whether a string can be parsed as a Ticket
         */
        Ticket.Mutable accept(@NotNull String sigData, @NotNull String sigPart);

        /**
         * verify the signature
         */
        boolean verify(Ticket tk);
    }
}
