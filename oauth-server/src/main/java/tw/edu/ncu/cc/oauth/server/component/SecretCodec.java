package tw.edu.ncu.cc.oauth.server.component;

import tw.edu.ncu.cc.oauth.server.data.SerialSecret;

public interface SecretCodec {
    public String encode( SerialSecret secret );
    public SerialSecret decode( String data );
}
