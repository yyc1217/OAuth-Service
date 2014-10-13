package tw.edu.ncu.cc.oauth.server.db.model;

import tw.edu.ncu.cc.oauth.server.db.data.AuthCodeEntity;

public interface AuthCodeModel {
    public void persistAuthCodes( AuthCodeEntity... authCodes );
    public AuthCodeEntity getAuthCode( String code );
    public void deleteAuthCodes( AuthCodeEntity... authCodes );
}
