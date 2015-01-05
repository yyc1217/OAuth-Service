package tw.edu.ncu.cc.oauth.server.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessLoggerInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {

        String requestPath = request.getRequestURI().substring( request.getContextPath().length() );
        String requestIP   = request.getRemoteAddr();
        logger.info( String.format( "REQUEST [%s] from [%s]", requestPath, requestIP ) );
        return true;
    }
}
