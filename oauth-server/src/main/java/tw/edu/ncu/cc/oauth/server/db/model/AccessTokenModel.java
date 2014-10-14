package tw.edu.ncu.cc.oauth.server.db.model;

import tw.edu.ncu.cc.oauth.server.db.data.AccessTokenEntity;

public interface AccessTokenModel {
    public void persistAccessToken( AccessTokenEntity... accessToken );
    public AccessTokenEntity getAccessToken( String token ) ;
}
