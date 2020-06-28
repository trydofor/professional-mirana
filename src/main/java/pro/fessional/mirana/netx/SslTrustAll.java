package pro.fessional.mirana.netx;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @author trydofor
 * @since 2018-05-21
 */
public class SslTrustAll {

    public static final SSLSocketFactory SSL_SOCKET_FACTORY;
    public static final X509TrustManager X509_TRUST_MANAGER;
    public static final HostnameVerifier HOSTNAME_VERIFIER = (hostname, session) -> true;

    static {
        try {

            final X509TrustManager[] mngrs = new X509TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, mngrs, new java.security.SecureRandom());

            SSL_SOCKET_FACTORY = sslContext.getSocketFactory();
            X509_TRUST_MANAGER = mngrs[0];
        } catch (Exception e) {
            throw new IllegalStateException("unable to init ssl manager", e);
        }
    }
}
