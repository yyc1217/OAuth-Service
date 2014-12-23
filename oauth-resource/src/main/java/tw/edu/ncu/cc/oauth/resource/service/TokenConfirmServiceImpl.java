package tw.edu.ncu.cc.oauth.resource.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessToken;
import tw.edu.ncu.cc.oauth.resource.config.RemoteConfig;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class TokenConfirmServiceImpl implements TokenConfirmService {

    private RemoteConfig config;
    private RestTemplate restTemplate = new RestTemplate();

    public TokenConfirmServiceImpl() {
        turnOffSSLChecking();
    }

    public void setConfig( RemoteConfig config ) {
        this.config = config;
    }

    @Override
    public AccessToken readToken( String accessToken ) {
        String remoteAddr = config.getAddrPrefix() + accessToken + config.getAddrSuffix();
        try {
            return restTemplate.getForEntity( remoteAddr, AccessToken.class ).getBody();
        } catch ( HttpClientErrorException e ) {
            if( e.getStatusCode().equals( HttpStatus.NOT_FOUND ) ) {
                return null;
            } else {
                throw e;
            }
        }
    }

    private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted( X509Certificate[] certs, String authType ) {
                }
                public void checkServerTrusted( X509Certificate[] certs, String authType ) {
                }
            }
    };

    public static void turnOffSSLChecking() {
        final SSLContext sc;
        try {
            sc = SSLContext.getInstance( "SSL" );
            sc.init( null, UNQUESTIONING_TRUST_MANAGER, null );
            HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );
        } catch ( NoSuchAlgorithmException | KeyManagementException e ) {
            throw new RuntimeException( e );
        }
    }

}
