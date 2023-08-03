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
import java.io.Serializable;

@Named("loginBeanJWT")
@SessionScoped
public class LoginBeanJWT implements Serializable {

	String secretKey = "^=e'Q!GHv_=HMEkpx4k$EUH!{[F9s?0M"; // Clave secreta para firmar el token, esta clave deberia ser mas robusta y deberia estar mas oculta.

	
	private static final long serialVersionUID = 1L;

	    public String generarToken(String nombreUsuario) {
	        // a esta altura ya se valido el usuario, solo hay que generar el Token
	    	           
            String token = null;
	    	try {
	    		
	            Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 día de expiración
	            
	            token = Jwts.builder()
	                    .setSubject(nombreUsuario)
	                    .setExpiration(expirationDate)
	                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
	                    .compact();
	            
	            // Almacenar el token en la sesión
	            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("token", token);
//	            request.getSession().setAttribute("token", jwtToken); // Almacenar el token en la sesión
	            	
	            return token;
	    	}catch (Exception e){
	    		// El token no se genero
	    		 System.out.println("Error al generar el Token: " + e);
	    		 return"Error";
	    	}
	            
	    	
	    	
	    	// ****************************************  Esto capaz no lo usamos  **************************************** 
	    	
	    	/**
	    	 
	    	  	 //esto se usa para saber si el token es valido
	    	Claims claims = new DefaultClaims();
	    	try {
	    	       claims = Jwts.parserBuilder()
	    	            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
	    	            .build()
	    	            .parseClaimsJws(token)
	    	            .getBody();

	    	    // El token es válido
	    	} catch (Exception e) {
	    	    // La firma del token no es válida
	    	}
	    	
	    	// esto es para saber si el token expiro
	    	Date expirationDate = claims.getExpiration();
	    	Date currentDate = new Date();

	    	if (expirationDate.before(currentDate)) {
	    	    // El token ha expirado
	    	} else {
	    	    // El token está dentro del período de validez
	    	} 
	    	  	
	    	 */
	    	
	    	// ****************************************  Esto capaz no lo usamos  ****************************************
            
	    }
	    
	    
	    public String obtenerToken() {
	         // Obtener el token de la sesión
            String token = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token");

            if (token != null) {
              
            	//esto se usa para saber si el token es valido
    	    	Claims claims = new DefaultClaims();
    	    	try {
    	    	       claims = Jwts.parserBuilder()
    	    	            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
    	    	            .build()
    	    	            .parseClaimsJws(token)
    	    	            .getBody();

    	    	    // El token es válido
    	    	} catch (Exception e) {
    	    	    // La firma del token no es válida
    	    	}
            	
            	
                System.out.println("Token JWT almacenado en la sesión: " + token);
                return token;
            } else {
                // El token no está en la sesión o es nulo
                System.out.println("No se encontró ningún token JWT en la sesión.");
                return"";
            }
	    }

}
