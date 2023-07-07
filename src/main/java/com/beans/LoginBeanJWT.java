package com.beans;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
	            Date expirationDate = new Date(System.currentTimeMillis() + 86400000); // 1 día de expiración

	            String token = Jwts.builder()
	                    .setSubject(nombreUsuario)
	                    .setExpiration(expirationDate)
	                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
	                    .compact();

	            // Almacenar el token en la sesión
	            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("token", token);
            
	    }

}
