package tw.edu.ncu.cc.oauth.client.android;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.io.IOException;
import java.util.Arrays;

public class AndroidOauthBuilder {

    private CredentialStore credentialStore;
    private FragmentManager fragmentManager;
    private String clientID;
    private String clientSecret;
    private String callback;
    private String[] scope;

    private static final String CREDENTIAL_FILE_NAME = "credential.file";
    private static final String AUTH_ENDPOINT_PATH  = "https://appstore.cc.ncu.edu.tw/oauth/oauth/authorize";
    private static final String TOKEN_ENDPOINT_PATH = "https://appstore.cc.ncu.edu.tw/oauth/oauth/token";

    private AndroidOauthBuilder( Context context ) {
        this.credentialStore = new SharedPreferencesCredentialStore( context, CREDENTIAL_FILE_NAME, new JacksonFactory() );
    }

    public static AndroidOauthBuilder initContext( Context context ) {
        return new AndroidOauthBuilder( context );
    }

    public AndroidOauthBuilder fragmentManager( FragmentManager fragmentManager ) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public AndroidOauthBuilder clientID( String clientID ) {
        this.clientID = clientID;
        return this;
    }

    public AndroidOauthBuilder clientSecret( String clientSecret ) {
        this.clientSecret = clientSecret;
        return this;
    }

    public AndroidOauthBuilder callback( String callback ) {
        this.callback = callback;
        return this;
    }

    public AndroidOauthBuilder scope( String...scope ) {
        this.scope = scope;
        return this;
    }

    public OAuthManager build() {
        return new OAuthManager( buildAuthorizationFlow(), buildUIController() );
    }

    private AuthorizationFlow buildAuthorizationFlow() {
        return new AuthorizationFlow
                .Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl( TOKEN_ENDPOINT_PATH ),
                new ClientParametersAuthentication( clientID, clientSecret ),
                clientID,
                AUTH_ENDPOINT_PATH )
                .setCredentialStore( credentialStore )
                .setScopes( Arrays.asList( scope ) )
                .build();
    }

    private AuthorizationUIController buildUIController() {
        return new DialogFragmentController( fragmentManager ) {
            @Override
            public boolean isJavascriptEnabledForWebView() {
                return true;
            }
            @Override
            public String getRedirectUri() throws IOException {
                return callback;
            }
        };
    }

}
