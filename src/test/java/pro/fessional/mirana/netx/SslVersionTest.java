package pro.fessional.mirana.netx;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

/**
 * @author trydofor
 * @since 2021-01-31
 */
class SslVersionTest {

    @Test
    void supportV12() {
        Testing.println(SslVersion.supportV12());
    }
}
