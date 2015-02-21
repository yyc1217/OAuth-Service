package tw.edu.ncu.cc.oauth.server.helper;

import java.security.SecureRandom;

public class StringGenerator {

    private static final int TOKEN_LENGTH = 32;
    private static final SecureRandom random = new SecureRandom();
    private static final String TOKEN_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz._";

    public static String generateToken() {
        byte[] bytes = new byte[ TOKEN_LENGTH ];
        random.nextBytes( bytes );
        StringBuilder stringBuilder = new StringBuilder( TOKEN_LENGTH );
        for ( byte b : bytes ) {
            stringBuilder.append( TOKEN_CHARS.charAt( b & 0x3F ) );
        }
        return stringBuilder.toString();
    }

}
