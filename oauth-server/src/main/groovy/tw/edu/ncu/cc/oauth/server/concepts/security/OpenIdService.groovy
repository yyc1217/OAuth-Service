package tw.edu.ncu.cc.oauth.server.concepts.security

import javax.security.auth.login.LoginException
import javax.servlet.http.HttpServletRequest


interface OpenIdService {

    String getLoginPath();

    void login( HttpServletRequest request ) throws LoginException;
}