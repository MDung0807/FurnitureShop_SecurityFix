package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Set-Cookie", "; Secure; HttpOnly; SameSite=" + "strict");
        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Content-Security-Policy",
//                    "default-src 'self' https: 'unsafe-inline';" +
//                        "script-src 'self' 'unsafe-inline';" +
//                        "font-src 'self' https: data: ; " +
//                        "img-src 'self' https: data: ;");

        filterChain.doFilter(servletRequest, servletResponse);
    }

}

