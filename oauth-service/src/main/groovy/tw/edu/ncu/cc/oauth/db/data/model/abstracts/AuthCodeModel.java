package tw.edu.ncu.cc.oauth.db.data.model.abstracts;

import tw.edu.ncu.cc.oauth.db.data.AuthCode;

public interface AuthCodeModel {
    public void persistAuthCode( AuthCode... authCodes );
    public AuthCode getAuthCode( String code );
}
