package pro.fessional.mirana.netx;

import org.junit.jupiter.api.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author trydofor
 * @since 2021-01-31
 */
class SslTrustAllTest {

    @Test
    void trustSocketFactory() {
        SSLSocketFactory df1 = HttpsURLConnection.getDefaultSSLSocketFactory();
        SslTrustAll.trustSocketFactory();
        SSLSocketFactory df2 = HttpsURLConnection.getDefaultSSLSocketFactory();
        SslTrustAll.resetSocketFactory();
        SSLSocketFactory df3 = HttpsURLConnection.getDefaultSSLSocketFactory();

        assertSame(df1, df3);
        assertSame(df2, SslTrustAll.SSL_SOCKET_FACTORY);
    }

    @Test
    void resetHostnameVerifier() {
        HostnameVerifier dh1 = HttpsURLConnection.getDefaultHostnameVerifier();
        SslTrustAll.trustHostnameVerifier("moilioncircle.com");
        HostnameVerifier dh2 = HttpsURLConnection.getDefaultHostnameVerifier();
        SslTrustAll.resetHostnameVerifier();
        HostnameVerifier dh3 = HttpsURLConnection.getDefaultHostnameVerifier();

        assertSame(dh1, dh3);
        assertSame(dh2, SslTrustAll.HOSTNAME_VERIFIER);
    }

}
