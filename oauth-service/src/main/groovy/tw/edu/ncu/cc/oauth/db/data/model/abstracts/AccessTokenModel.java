package tw.edu.ncu.cc.oauth.db.data.model.abstracts;

import tw.edu.ncu.cc.oauth.db.data.AccessToken;

public interface AccessTokenModel {
    public void persistAccessToken( AccessToken... accessToken );
    public AccessToken getAccessToken( String token ) ;
}
