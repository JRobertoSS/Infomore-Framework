package br.com.infomore.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	  public boolean preHandle(HttpServletRequest request,
	      HttpServletResponse response,
	      Object controller) throws Exception {
		
		String uri = request.getRequestURI();
	      if(uri.endsWith("login") || 
	          uri.endsWith("efetuaLogin") ||
	          uri.endsWith("cadastro") ||
	          uri.endsWith("cadastrar") || 
	              uri.contains("resources")){
	        return true;
	      }
	      
		if (request.getSession().getAttribute("usuario") != null) {
			return true;
		}
	      
	    response.sendRedirect("login");
	    return false;  
	}
}
