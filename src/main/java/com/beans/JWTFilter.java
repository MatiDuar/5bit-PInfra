package com.beans;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@WebFilter("/index.xhtml")
public class JWTFilter implements Filter {

	String secretKey = "^=e'Q!GHv_=HMEkpx4k$EUH!{[F9s?0M"; // Clave secreta para firmar el token, esta clave

	@Inject
	LoginBeanJWT jwt;
	@Inject
	GestionPersona persona;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		System.out.println(httpRequest.getContextPath() + "/index.xhtml");

		String jwtToken = persona.getToken();
		if (jwtToken != null) {

			// Valida el token JWT
			Claims claims = new DefaultClaims();
			try {
				claims = Jwts.parserBuilder()
						.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
						.build()
						.parseClaimsJws(jwtToken)
						.getBody();
				// El token es válido

				String username = claims.getSubject();
				Boolean admin=(Boolean) claims.get("esAdmin");
				
				// Aca se podria obtener los roles y otras informaciones adicionales del token si se incluyen en el
				System.out.println("Usuario: " + username);
				System.out.println("esAdmin: "+admin);
				// Permite el acceso al recurso protegido
				chain.doFilter(request, response);
			} catch (Exception e) {
				// El token no es válido, responde con un error 401 o redirige a la página de login
//				HttpServletResponse httpResponse = (HttpServletResponse) response;
//				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				httpResponse.sendRedirect("login.xhtml");
			}
		} else {
			// Token no proporcionado, responde con un error 401 o redirige a la página de login
//			HttpServletResponse httpResponse = (HttpServletResponse) response;
//			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			httpResponse.sendRedirect("login.xhtml");
		}

	}

}
