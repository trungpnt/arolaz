package com.ecommerce.arolaz.Utils.Security;


import com.ecommerce.arolaz.Utils.ExceptionHandlers.ExpiredJwtException;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.InvalidTokenException;
import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;
import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Utility Class for common Java Web Token operations
 */
@Component
public class JwtProvider {
    private JwtParser parser;

    private String secretKey;

    private String ROLE_KEYS = "roles";

    private String token;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    public  JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKey){
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Create JWT string given fullname, phone, email,id.
     *
     * @param securityUser  : the principal
     * @return jwt string
     */

    public String createToken(Optional<SecurityUser> securityUser, List<SecurityRole> roles) {

        Claims claims = Jwts.claims().setSubject(securityUser.get().getFullName());

        claims.put(ROLE_KEYS, roles.stream().map(role->new SimpleGrantedAuthority(role.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

        token = Jwts.builder()
                .setClaims(claims)
                .claim("phoneNumber", securityUser.get().getPhoneNumber())
                .claim("fullName",securityUser.get().getFullName())
                .claim("email", securityUser.get().getEmail())
                .claim("id", securityUser.get().getId().toString())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    /**
     * Validate the JWT String
     *
     * @param token JWT string
     *
     * @return true if criteria matched
     */
    public boolean isValidToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (MalformedJwtException ex) {
            throw new InvalidTokenException("Invalid token");
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }
        return true;
    }

    /**
     * Get the username from the token string
     * @param token jwt
     * @return username
     */

    public String getPhone(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("phoneNumber",String.class);
        //Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

    }
    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email",String.class);
    }

    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id",String.class);
        //Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }
    /**
     * Get the roles from the token string
     */
    public List<GrantedAuthority> getRoles(String token){
        List<Map<String,String>> roleClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(ROLE_KEYS,List.class);
        return roleClaims.stream().map(roleClaim -> new SimpleGrantedAuthority(roleClaim.get("authority"))).collect(Collectors.toList());
    }

}
