package pro.fessional.mirana.netx;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author trydofor
 * @since 2018-05-21
 */
public class SslTrustAll {

    public static final SSLSocketFactory SSL_SOCKET_FACTORY;
    public static final X509TrustManager X509_TRUST_MANAGER;
    public static final SSLContext SSL_CONTEXT;

    public static final HostnameVerifier HOSTNAME_VERIFIER;

    private static final SSLSocketFactory DEFAULT_SOCKET_FACTORY;
    private static final HostnameVerifier DEFAULT_HOSTNAME_VERIFIER;
    private static final HashSet<String> HOSTNAME_TRUSTED = new HashSet<>();

    static {
        try {
            final X509Certificate[] certs = {};
            final X509TrustManager trust = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return certs;
                }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new X509TrustManager[]{ trust }, new java.security.SecureRandom());

            SSL_CONTEXT = sslContext;
            SSL_SOCKET_FACTORY = sslContext.getSocketFactory();
            X509_TRUST_MANAGER = trust;

            DEFAULT_SOCKET_FACTORY = HttpsURLConnection.getDefaultSSLSocketFactory();
            DEFAULT_HOSTNAME_VERIFIER = HttpsURLConnection.getDefaultHostnameVerifier();
            HOSTNAME_VERIFIER = (hostname, sslSession) -> {
                if (HOSTNAME_TRUSTED.isEmpty() || HOSTNAME_TRUSTED.contains(hostname)) return true;
                return DEFAULT_HOSTNAME_VERIFIER.verify(hostname, sslSession);
            };
        }
        catch (Exception e) {
            throw new IllegalStateException("unable to init ssl manager", e);
        }
    }

    /**
     * reset HttpsURLConnection.setDefaultSSLSocketFactory to system default
     */
    public static void resetSocketFactory() {
        HttpsURLConnection.setDefaultSSLSocketFactory(DEFAULT_SOCKET_FACTORY);
    }

    /**
     * set HttpsURLConnection.setDefaultSSLSocketFactory to SSL_SOCKET_FACTORY
     */
    public static void trustSocketFactory() {
        HttpsURLConnection.setDefaultSSLSocketFactory(SSL_SOCKET_FACTORY);
    }

    /**
     * reset HttpsURLConnection.setDefaultHostnameVerifier to system default
     */
    public static void resetHostnameVerifier() {
        HttpsURLConnection.setDefaultHostnameVerifier(DEFAULT_HOSTNAME_VERIFIER);
    }

    /**
     * set HttpsURLConnection.setDefaultHostnameVerifier to HOSTNAME_VERIFIER
     *
     * @param hostname trusted hostname, eg. www.moilioncircle.com
     */
    public static void trustHostnameVerifier(String... hostname) {
        HOSTNAME_TRUSTED.addAll(Arrays.asList(hostname));
        HttpsURLConnection.setDefaultHostnameVerifier(HOSTNAME_VERIFIER);
    }
}
