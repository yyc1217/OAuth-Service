package tw.edu.ncu.cc.oauth.resource.service;


public interface TokenConfirmService {
    public String[] readScope( String accessToken );
}
