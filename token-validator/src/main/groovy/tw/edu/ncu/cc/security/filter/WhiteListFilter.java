package tw.edu.ncu.cc.security.filter;


import javax.servlet.*;
import java.io.IOException;

public class WhiteListFilter implements Filter {

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException {

    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
        if( request.getRemoteAddr().equals( "140.115.0.97" ) ) {
            chain.doFilter( request, response );
        }
    }

    @Override
    public void destroy() {

    }

}
