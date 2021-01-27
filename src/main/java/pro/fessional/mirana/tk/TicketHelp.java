package pro.fessional.mirana.tk;

import pro.fessional.mirana.bits.Aes128;
import pro.fessional.mirana.bits.Base64;
import pro.fessional.mirana.bits.HmacHelp;
import pro.fessional.mirana.bits.MdHelp;
import pro.fessional.mirana.data.Null;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author trydofor
 * @since 2021-01-26
 */
public class TicketHelp {

    public static SigMds sig(MdHelp help) {
        return new SigMds(help, null);
    }

    public static SigMds sig(MdHelp help, byte[] salt) {
        return new SigMds(help, salt);
    }

    public static SigHmac sig(HmacHelp help) {
        return new SigHmac(help);
    }

    public static class SigMds implements Function<String, String> {

        private final Supplier<MessageDigest> supplier;
        private final byte[] salt;

        public SigMds(Supplier<MessageDigest> help, byte[] salt) {
            this.supplier = help;
            this.salt = Null.notNull(salt);
        }

        public SigMds(MdHelp help, byte[] salt) {
            this.supplier = help::inside;
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
            this.help = help::inside;
        }

        @Override
        public String apply(String s) {
            final Mac md = help.get();
            md.update(s.getBytes());
            final byte[] digest = md.doFinal();
            return Base64.encode(digest);
        }
    }

    public static Am0Help am0(byte[] salt) {
        return new Am0Help(salt);
    }

    public static Am0Help am0(String salt) {
        return new Am0Help(salt);
    }

    public static Am1Help am1(byte[] salt) {
        return new Am1Help(salt);
    }

    public static Am1Help am1(String salt) {
        return new Am1Help(salt);
    }

    public static Ah1Help ah1(byte[] salt) {
        return new Ah1Help(salt);
    }

    public static Ah1Help ah1(String salt) {
        return new Ah1Help(salt);
    }

    // //
    public static class Am0Help implements Decoder {
        public final String mod = "am0";
        public final Aes128 aes128;
        public final SigMds sigVerify = sig(MdHelp.md5);
        public final Function<String, String> bizString;
        public final Function<String, byte[]> bizBytes;

        public Am0Help(byte[] salt) {
            aes128 = Aes128.of(salt);
            bizString = aes128::decode64;
            bizBytes = s -> {
                byte[] bs = Base64.decode(s);
                return aes128.decode(bs);
            };
        }

        public Am0Help(String salt) {
            this(salt.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T decodeBiz(Ticket tk, Class<T> clz) {
            if (String.class.equals(clz)) {
                return (T) tk.decodeBiz(bizString);
            } else if (byte[].class.equals(clz)) {
                return (T) tk.decodeBiz(bizBytes);
            }
            return null;
        }

        @Override
        public boolean verifySig(Ticket tk) {
            if (tk == null) return false;
            return tk.verifySig(sigVerify);
        }
    }

    public static class Am1Help implements Decoder {
        public final String mod = "am1";
        public final Aes128 aes128;
        public final SigMds sigVerify;
        public final Function<String, String> bizString;
        public final Function<String, byte[]> bizBytes;

        public Am1Help(byte[] salt) {
            aes128 = Aes128.of(salt);
            sigVerify = sig(MdHelp.md5, salt);
            bizString = aes128::decode64;
            bizBytes = s -> {
                byte[] bs = Base64.decode(s);
                return aes128.decode(bs);
            };
        }

        public Am1Help(String salt) {
            this(salt.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T decodeBiz(Ticket tk, Class<T> clz) {
            if (String.class.equals(clz)) {
                return (T) tk.decodeBiz(bizString);
            } else if (byte[].class.equals(clz)) {
                return (T) tk.decodeBiz(bizBytes);
            }
            return null;
        }

        @Override
        public boolean verifySig(Ticket tk) {
            if (tk == null) return false;
            return tk.verifySig(sigVerify);
        }
    }

    public static class Ah1Help implements Decoder {
        public final String mod = "ah1";
        public final Aes128 aes128;
        public final SigHmac sigVerify;
        public final Function<String, String> bizString;
        public final Function<String, byte[]> bizBytes;

        public Ah1Help(byte[] salt) {
            aes128 = Aes128.of(salt);
            sigVerify = sig(HmacHelp.md5(salt));
            bizString = aes128::decode64;
            bizBytes = s -> {
                byte[] bs = Base64.decode(s);
                return aes128.decode(bs);
            };
        }

        public Ah1Help(String salt) {
            this(salt.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T decodeBiz(Ticket tk, Class<T> clz) {
            if (String.class.equals(clz)) {
                return (T) tk.decodeBiz(bizString);
            } else if (byte[].class.equals(clz)) {
                return (T) tk.decodeBiz(bizBytes);
            }
            return null;
        }

        @Override
        public boolean verifySig(Ticket tk) {
            if (tk == null) return false;
            return tk.verifySig(sigVerify);
        }
    }

    // ////

    public static <T extends Ticket.Mutable> Builder<T> builder(T tk) {
        return new Builder<>(tk);
    }

    public static class Builder<T extends Ticket.Mutable> {
        private final T ticket;

        public Builder(T ticket) {
            this.ticket = ticket;
        }

        public BuildExp<T> mod(String mod) {
            ticket.setPubMod(mod);
            return new BuildExp<>(ticket);
        }
    }

    public static class BuildExp<T extends Ticket.Mutable> {
        private final T ticket;

        public BuildExp(T ticket) {
            this.ticket = ticket;
        }

        public BuildSeq<T> exp(long exp) {
            ticket.setPubExp(exp);
            return new BuildSeq<>(ticket);
        }

        public BuildSeq<T> expAfterNow(long num, TimeUnit unit) {
            ticket.setPubExp(unit.toSeconds(num) + System.currentTimeMillis() / 1000);
            return new BuildSeq<>(ticket);
        }

        public BuildSeq<T> expAfter(long num, TimeUnit unit) {
            ticket.setPubExp(unit.toSeconds(num) + ticket.getPubExp());
            return new BuildSeq<>(ticket);
        }
    }

    public static class BuildSeq<T extends Ticket.Mutable> {
        private final T ticket;

        public BuildSeq(T ticket) {
            this.ticket = ticket;
        }

        public BuildBiz<T> seq(int seq) {
            ticket.setPubSeq(seq);
            return new BuildBiz<>(ticket);
        }

        public BuildBiz<T> seqIncrease() {
            ticket.setPubSeq(ticket.getPubSeq() + 1);
            return new BuildBiz<>(ticket);
        }

        public BuildBiz<T> seqAdd(int step) {
            ticket.setPubSeq(ticket.getPubSeq() + step);
            return new BuildBiz<>(ticket);
        }
    }

    public static class BuildBiz<T extends Ticket.Mutable> {
        private final T ticket;

        public BuildBiz(T ticket) {
            this.ticket = ticket;
        }

        public BuildSig<T> biz(String biz) {
            ticket.setBizPart(biz);
            return new BuildSig<>(ticket);
        }

        public BuildSig<T> bizEmpty() {
            ticket.setBizPart(Null.Str);
            return new BuildSig<>(ticket);
        }

        public BuildSig<T> bizB64(byte[] biz) {
            ticket.setBizPart(Base64.encode(biz));
            return new BuildSig<>(ticket);
        }

        public BuildSig<T> bizB64(String biz) {
            ticket.setBizPart(Base64.encode(biz));
            return new BuildSig<>(ticket);
        }

        public BuildSig<T> bizAes(String biz, byte[] key) {
            return bizAes(biz, Aes128.of(key));
        }

        public BuildSig<T> bizAes(String biz, Aes128 aes) {
            ticket.setBizPart(aes.encode64(biz));
            return new BuildSig<>(ticket);
        }
    }

    public static class BuildSig<T extends Ticket.Mutable> {
        private final T ticket;

        public BuildSig(T ticket) {
            this.ticket = ticket;
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
                ticket.setSigPart(sig.apply(ticket.getSigData(true)));
            }

            if (ticket.getSigPart().isEmpty()) {
                throw new IllegalArgumentException("sig is null");
            }

            return ticket;
        }
    }

    public static <T extends Ticket.Mutable> Parser<T> parser(Function<String, T> factory) {
        return new Parser<>(factory);
    }

    public static class Parser<T extends Ticket.Mutable> {

        private final Function<String, T> factory;
        private int order = Integer.MAX_VALUE;

        public Parser(Function<String, T> factory) {
            this.factory = factory;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public T parse(String tk) {
            if (tk == null) return null;
            final T ticket = factory.apply(tk);
            if (ticket == null) return null;

            int[] pos = {-1, -1, -1, -1};
            int idx = 0;
            for (int i = 0, len = tk.length(); i < len && idx < pos.length; i++) {
                char c = tk.charAt(i);
                if (c == '-' && idx < 2) {
                    pos[idx++] = i;
                } else if (c == '.') {
                    pos[idx++] = i;
                }
            }

            try {
                int off = 0;
                if (pos[0] < off) return null;
                ticket.setPubMod(tk.substring(off, pos[0]));

                off = pos[0] + 1;
                if (pos[1] < off) return null;
                ticket.setPubExp(Long.parseLong(tk.substring(off, pos[1])));

                off = pos[1] + 1;
                if (pos[2] < off) return null;
                ticket.setPubSeq(Integer.parseInt(tk.substring(off, pos[2])));

                off = pos[2] + 1;
                if (pos[3] > off) {
                    ticket.setBizPart(tk.substring(off, pos[3]));
                    ticket.setSigPart(tk.substring(pos[3] + 1));
                    ticket.setSigData(tk.substring(0, pos[3]));
                } else {
                    if (pos[3] > 0) {
                        return null;
                    } else {
                        ticket.setBizPart(Null.Str);
                        ticket.setSigPart(tk.substring(off));
                        ticket.setSigData(tk.substring(0, off));
                    }
                }

                return ticket;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public interface Decoder {
        boolean verifySig(Ticket tk);

        <T> T decodeBiz(Ticket tk, Class<T> clz);
    }
}
