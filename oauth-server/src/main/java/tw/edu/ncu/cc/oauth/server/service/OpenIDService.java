package tw.edu.ncu.cc.oauth.server.service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

public interface OpenIDService {

    public String getLoginPath();

    public void login( HttpServletRequest request ) throws LoginException;

}
