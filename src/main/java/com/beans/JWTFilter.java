package com.beans;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@WebFilter("/index.xhtml")
public class JWTFilter implements Filter {

	String secretKey = "^=e'Q!GHv_=HMEkpx4k$EUH!{[F9s?0M"; // Clave secreta para firmar el token, esta clave

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		

		
		if (url.equals(httpRequest.getContextPath()+"/index.xhtml")) {
			String jwtToken = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token");
			if ((jwtToken != null && jwtToken.startsWith("Bearer "))) {
				jwtToken = jwtToken.substring(7); // Elimina el prefijo "Bearer "
				try {
					// Valida el token JWT
					Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

					String username = claims.getSubject();
					// Aquí también puedes obtener los roles y otras informaciones adicionales del
					// token si las incluiste al generarlo
					System.out.println("Usuario: " + username);
					// Realiza cualquier lógica de autorización adicional si es necesario

					// Continúa con la cadena de filtros y permite el acceso al recurso protegido
					chain.doFilter(request, response);
				} catch (Exception e) {
					// El token no es válido, responde con un error 401 o redirige a la página de
					// login
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
			} else {
				// Token no proporcionado, responde con un error 401 o redirige a la página de
				// login
//				HttpServletResponse httpResponse = (HttpServletResponse) response;
//				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				// no se encontro el token se redirije a Login.
				chain.doFilter(request, response);
//				res.sendRedirect(httpRequest.getContextPath()+"/login.xhtml");

			}
		}else {
			chain.doFilter(request, response);
		}

	}

}
