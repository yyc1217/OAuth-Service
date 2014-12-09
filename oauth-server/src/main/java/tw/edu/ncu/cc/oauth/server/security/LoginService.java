package tw.edu.ncu.cc.oauth.server.security;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    public String getLoginPath();
    public void authenticate( HttpServletRequest request ) throws LoginException;

}
