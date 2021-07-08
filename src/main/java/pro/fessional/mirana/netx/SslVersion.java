package pro.fessional.mirana.netx;

/**
 * javax.net.ssl.SSLException: Received fatal alert: protocol_version
 * at sun.security.ssl.Alerts.getSSLException(Alerts.java:208)
 * https://blogs.oracle.com/java-platform-group/jdk-8-will-use-tls-12-as-default
 *
 * @author trydofor
 * @since 2018-09-26
 */
public class SslVersion {

    /**
     * append TLSv1.2 to https.protocols if not exist
     *
     * @return getProperty(https.protocols)
     */
    public static String supportV12() {
        String version = System.getProperty("https.protocols");
        String v12 = "TLSv1.2";
        if (version == null || version.isEmpty()) {
            System.setProperty("https.protocols", v12);
        }
        else if (version.contains(v12)) {
            v12 = version;
        }
        else {
            v12 = version + ",TLSv1.2";
            System.setProperty("https.protocols", v12);
        }
        return v12;
    }
}
