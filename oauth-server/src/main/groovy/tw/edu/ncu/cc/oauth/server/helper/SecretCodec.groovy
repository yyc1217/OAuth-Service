package tw.edu.ncu.cc.oauth.server.helper

import org.apache.commons.codec.binary.Base64
import tw.edu.ncu.cc.oauth.server.helper.data.SerialSecret

public class SecretCodec {

    private static final String SPLIT_TOKEN = ':::'

    public static String encode( SerialSecret secret ) {
        return new String( Base64.encodeBase64URLSafe(
                ( secret.getId() + SPLIT_TOKEN + secret.getSecret() ).getBytes()
        ) );
    }

    public static SerialSecret decode( String data ) {
        if( Base64.isBase64( data.getBytes() ) ) {
            String originCode = new String( Base64.decodeBase64( data.getBytes() ) );
            if( originCode.contains( SPLIT_TOKEN ) ) {
                String[] pair = originCode.split( SPLIT_TOKEN );
                if( pair[0].isInteger() ) {
                    return new SerialSecret( Integer.parseInt( pair[0] ), pair[1] );
                }
            }
        }
        return new SerialSecret( 0, "" );
    }

}
