package tw.edu.ncu.cc.oauth.resource.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import tw.edu.ncu.cc.oauth.data.v1.management.token.AccessTokenObject;
import tw.edu.ncu.cc.oauth.resource.service.TokenConfirmService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenAccessDecisionFilter extends AbstractFilter {

    private TokenConfirmService tokenConfirmService;

    public void setTokenConfirmService( TokenConfirmService tokenConfirmService ) {
        this.tokenConfirmService = tokenConfirmService;
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
        checkAuthentication( ( HttpServletRequest ) request );
        chain.doFilter( request, response );
    }

    private void checkAuthentication( HttpServletRequest request ) {
        if ( isOAuthRequest( request ) ) {
            AccessTokenObject token = tokenConfirmService.readToken( readAccessToken( request ) );
            if ( token != null ) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                token.getUser(), "", AuthorityUtils.createAuthorityList( token.getScope() )
                        )
                );
            }
        }
    }

    private boolean isOAuthRequest( HttpServletRequest request ) {
        String authorization = request.getHeader( "Authorization" );
        return authorization != null && authorization.startsWith( "Bearer" );
    }

    private String readAccessToken( HttpServletRequest request ) {
        String authorization = request.getHeader( "Authorization" );
        return authorization.trim().substring( "Bearer".length() ).trim();
    }

}
