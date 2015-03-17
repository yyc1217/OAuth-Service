package tw.edu.ncu.cc.oauth.resource.service;


import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject;

public interface TokenConfirmService {
    public AccessTokenObject readToken( String accessToken );
}
