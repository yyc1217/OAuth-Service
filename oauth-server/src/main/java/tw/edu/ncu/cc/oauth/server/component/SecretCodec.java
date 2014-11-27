package tw.edu.ncu.cc.oauth.server.component;

import tw.edu.ncu.cc.oauth.server.data.Secret;

public interface SecretCodec {
    public String encode( Secret secret );
    public Secret decode( String data );
}
