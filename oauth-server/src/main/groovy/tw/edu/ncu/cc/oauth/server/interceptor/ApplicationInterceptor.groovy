package tw.edu.ncu.cc.oauth.server.interceptor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import tw.edu.ncu.cc.oauth.server.concepts.log.LogService

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
public class ApplicationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    def LogService logService

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        logService.info(
                request.method,
                request.remoteAddr,
                getRequestPath( request )
        )
        return true;
    }

    private static String getRequestPath( HttpServletRequest request ) {
        return request.getRequestURI().substring( request.getContextPath().length() );
    }

}
