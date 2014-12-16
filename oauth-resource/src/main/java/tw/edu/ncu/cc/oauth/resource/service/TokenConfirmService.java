package tw.edu.ncu.cc.oauth.resource.service;


import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessToken;

public interface TokenConfirmService {
    public AccessToken readScope( String accessToken );
}
