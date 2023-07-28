package pro.fessional.mirana.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Native Class deserialization when serialVersionUID is not compatible
 *
 * @author trydofor
 * @since 2022-02-23
 */
public class CompatibleObjectStream extends ObjectInputStream {

    private static final ConcurrentMap<Key, Object> CompatibleClass = new ConcurrentHashMap<>();

    public CompatibleObjectStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        final ObjectStreamClass remote = super.readClassDescriptor();
        final Key key = new Key(remote);
        final ObjectStreamClass local = checkCompatible(key);
        return local == null ? remote : local;
    }

    private static final Object Same = CompatibleObjectStream.class;

    private ObjectStreamClass checkCompatible(Key key) {
        final Object local = CompatibleClass.get(key);
        if (local == Same) {
            return null;
        }
        else if (local != null) {
            return (ObjectStreamClass) local;
        }

        try {
            final Class<?> lc = Class.forName(key.name);
            final ObjectStreamClass ld = ObjectStreamClass.lookup(lc);
            if (ld != null && ld.getSerialVersionUID() != key.suid) {
                CompatibleClass.put(key, ld);
                return ld;
            }
        }
        catch (ClassNotFoundException e) {
            // ignore
        }
        CompatibleClass.put(key, Same);
        return null;
    }

    private static class Key {
        private final String name;
        private final long suid;

        public Key(ObjectStreamClass clz) {
            this.name = clz.getName();
            this.suid = clz.getSerialVersionUID();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return suid == key.suid && name.equals(key.name);
        }

        @Override
        public int hashCode() {
            final int h = name.hashCode();
            return (int) (h ^ (suid >>> 32));
        }
    }
}
