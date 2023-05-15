package ch.uzh.ifi.hase.soprafs23;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardServlet implements Servlet {
    private final String forwardUrl;

    public ForwardServlet(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // No initialization required
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl + request.getServletPath());
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        // No cleanup required
    }
}
