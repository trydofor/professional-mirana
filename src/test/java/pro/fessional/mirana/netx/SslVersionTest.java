package pro.fessional.mirana.netx;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

/**
 * @author trydofor
 * @since 2021-01-31
 */
class SslVersionTest {

    @Test
    void supportV12() {
        SystemOut.println(SslVersion.supportV12());
    }
}
