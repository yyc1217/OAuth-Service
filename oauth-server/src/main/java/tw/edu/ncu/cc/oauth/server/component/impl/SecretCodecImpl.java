package tw.edu.ncu.cc.oauth.server.component.impl;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import tw.edu.ncu.cc.oauth.server.component.SecretCodec;
import tw.edu.ncu.cc.oauth.server.data.Secret;

@Component
public class SecretCodecImpl implements SecretCodec {

    @Override
    public String encode( Secret secret ) {
        return new String( Base64.encode(
                ( secret.getId() + ":::" + secret.getSecret() ).getBytes()
        ) );
    }

    @Override
    public Secret decode( String data ) {
        String[] s = new String( Base64.decode( data.getBytes() ) ).split( ":::" );
        return new Secret( Integer.parseInt( s[0] ), s[1] );
    }

}
