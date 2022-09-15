package com.abab.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CrossOriginControlFilter implements Filter {

	private boolean isCross = false;
    @Override
    public void destroy() {
        isCross = false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		isCross = true;
		if(isCross){
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            HttpServletResponse httpServletResponse = (HttpServletResponse)response;
            httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));  
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
            httpServletResponse.setHeader("Access-Control-Max-Age", "0");  
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");  
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");  
            httpServletResponse.setHeader("XDomainRequestAllowed","1");  
        }
        chain.doFilter(request, response);
	}

}
