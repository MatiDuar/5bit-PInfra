package com.beans;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;


import javax.faces.context.FacesContext;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginBeanJWT")
@SessionScoped
public class LoginBeanJWT implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String nombreUsuario;
	    private String contrasena;

	    public String getUsername() {
	        return nombreUsuario;
	    }

	    public void setUsername(String nombreUsuario) {
	        this.nombreUsuario = nombreUsuario;
	    }

	    public String getPassword() {
	        return contrasena;
	    }

	    public void setPassword(String contrasena) {
	        this.contrasena = contrasena;
	    }

	    public void generarToken() {
	        // a esta altura ya se valido el usuario, solo hay que generar el Token
	    	
	    	// Generar el token JWT
            String secretKey = "clave_secreta"; // Clave secreta para firmar el token, estas clave deberia ser mas robusta y deberia estar mas oculta.
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
	            
	    	}catch (Exception e){
	    		// El token no se genero
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

}
