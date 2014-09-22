package tw.edu.ncu.cc.oauth.db.data.model.abstracts;

import tw.edu.ncu.cc.oauth.db.data.AuthCode;

public interface AuthCodeModel {
    public void persistAuthCodes( AuthCode... authCodes );
    public AuthCode getAuthCode( String code );
    public void deleteAuthCodes( AuthCode... authCodes );
}
