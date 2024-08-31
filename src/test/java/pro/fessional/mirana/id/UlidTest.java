package pro.fessional.mirana.id;

import de.huxhorn.sulky.ulid.ULID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

/**
 * @author trydofor
 * @since 2022-10-12
 */
class UlidTest {

    @Test
    void next() {
        /*
         01GF53QXEQ5KV895FQMPCWG0RC
         01GF53QXEQP3EHECQ52ZTXJB23
         01GF53QXEQ91S443Z1BAWBX2G9
         01GF53QXEQZWJ5BCFXJM6V4MH1
         01GF53QXEQRXXC2C3NWKWQHAEK
         01GF53QXERPY037QK1J6VCBNJV
         */
        for (int i = 0; i < 10; i++) {
            Testing.println(Ulid.next());
        }

        for (int i = 0; i < 100_000; i++) {
            final String id = Ulid.next();
            final ULID.Value uv = ULID.parseULID(id);
            Assertions.assertEquals(id, uv.toString());
        }
    }
}
