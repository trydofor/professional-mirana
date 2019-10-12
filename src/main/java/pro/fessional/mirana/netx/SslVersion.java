package pro.fessional.mirana.netx;

/**
 * avax.net.ssl.SSLException: Received fatal alert: protocol_version
 * at sun.security.ssl.Alerts.getSSLException(Alerts.java:208)
 * https://blogs.oracle.com/java-platform-group/jdk-8-will-use-tls-12-as-default
 *
 * @author trydofor
 * @since 2018-09-26
 */
public class SslVersion {
    static {
        String version = System.getProperty("https.protocols");
        if (version == null || !version.contains("TLSv1.2")) {
            System.setProperty("https.protocols", "TLSv1.2");
        }
    }

    public static void check(){
        // empty, use static block
    }

    public static void main(String[] args) {

    }
}
