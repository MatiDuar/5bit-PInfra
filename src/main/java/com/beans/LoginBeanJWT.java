package com.beans;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Serializer;

import javax.faces.context.FacesContext;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.logicaNegocio.GestionPersonaService;
import com.persistencia.entities.Persona;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

@Named("loginBeanJWT")
@SessionScoped
public class LoginBeanJWT implements Serializable {

	String secretKey = "^=e'Q!GHv_=HMEkpx4k$EUH!{[F9s?0M"; // Clave secreta para firmar el token, esta clave deberia ser
															// mas robusta y deberia estar mas oculta.
	@Inject
	GestionPersonaService persistenciaBean;

	private static final long serialVersionUID = 1L;

	public String generarToken(String nombreUsuario, String contrasena) throws Exception {
		// a esta altura ya se valido el usuario, solo hay que generar el Token
		Persona persona= persistenciaBean.verificarUsuario(nombreUsuario, contrasena);
		String token = null;
		try{
			if(persona==null) {
				throw new Exception("No se encontro usuario");
			}
			Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 día de expiración

			Map<String, Object> claims = new HashMap<>();
			claims.put("id", persona.getId());
			claims.put("esAdmin", persona.getAdmin());
			claims.put("activo", persona.getActivo());

			token = Jwts.builder().setSubject(persona.getNombreUsuario()).addClaims(claims)
					.setExpiration(expirationDate)
					.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256).compact();

			// Almacenar el token en la sesión
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("token", token);

			return token;
		} catch (Exception e) {
			// El token no se genero
			throw new Exception("Error al generar el Token: " + e);
			
		}

	}

	public Claims obtenerClaim(String token) {
		// Obtener el token de la sesión
//		String token = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token");

		if (token != null) {

			// esto se usa para saber si el token es valido
			Claims claims = new DefaultClaims();
			try {
				claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
						.parseClaimsJws(token).getBody();

				// El token es válido
			} catch (Exception e) {
				// La firma del token no es válida
			}

			System.out.println("Token JWT almacenado en la sesión: " + token);
			return claims;
		} else {
			// El token no está en la sesión o es nulo
			System.out.println("No se encontró ningún token JWT en la sesión.");
			return null;
		}
	}

}
