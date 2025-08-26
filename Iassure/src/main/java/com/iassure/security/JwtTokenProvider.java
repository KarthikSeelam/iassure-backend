package com.iassure.security;

import com.iassure.util.EncryptDecrypt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Naveen Kumar Chintala
 */
@Component
@Log4j2
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Autowired
    EncryptDecrypt edUtil;

    public String generateToken(Authentication authentication) throws NoSuchAlgorithmException, NoSuchProviderException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);


        Map<String, Set<String>> map = new HashMap<>();

        Set<String> roles =   authentication.getAuthorities().stream().map(authority ->
                "ROLE_" + authority.getAuthority().toUpperCase()).collect(Collectors.toSet()) ;
        map.put("roles", roles);

        map.put("organizationId", Collections.singleton(String.valueOf(userPrincipal.getOrganizationDetails().getOrganizationId())));


        return Jwts.builder()
                .id(edUtil.encrypt(Long.toString(userPrincipal.getId())))
                .subject(authentication.getName())
                .issuedAt(now)
                .expiration(expiryDate)
                .claims(map)
                .signWith(getSigninKey())
                .compact();
    }

    public Integer getUserIdFromJWT(String token) throws NoSuchAlgorithmException {


        Claims claims = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

         log.info("User Id is : {}", edUtil.decrypt(claims.getId()));
        //System.out.println("getUserIdFromJWT::"+claims.getSubject());
        return Integer.parseInt(edUtil.decrypt(claims.getId()));
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigninKey()).build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }catch (Exception e){
            logger.error("Exception while validating JWT token : {}", e.getMessage());
        }
        return false;
    }



    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
