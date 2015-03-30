package tw.edu.ncu.cc.oauth.resource.service;


import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject
import tw.edu.ncu.cc.oauth.data.v1.management.token.ApiTokenObject;

public interface TokenConfirmService {
    public AccessTokenObject readAccessToken( String accessToken );
    public ApiTokenObject readApiToken( String apiToken );
}
