package tw.edu.ncu.cc.oauth.resource.filter

import javax.servlet.*

public abstract class AbstractFilter implements Filter {

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException {

    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }

}
