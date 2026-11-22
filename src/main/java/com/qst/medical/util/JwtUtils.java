package com.qst.medical.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Jwt 工具类：生成 token、校验 token、解析 token
 */
public class JwtUtils {

    /** 过期时间（毫秒），6 小时 */
    public static final long EXPIRE = 1000 * 60 * 60 * 6;

    /** 密钥 */
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成 JwtToken
     */
    public static String getJwtToken(Long id, String uname, String role) {
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("medical-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("uname", uname)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return jwtToken;
    }

    /**
     * 判断 token 是否有效
     */
    public static boolean checkToken(String jwtToken) {
        if (!StringUtils.hasLength(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断请求头中的 token 是否有效
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("Authorization");
            if (!StringUtils.hasLength(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取 token 中的 Claims
     */
    public static Claims getClaims(String jwtToken) {
        if (!StringUtils.hasLength(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        return claimsJws.getBody();
    }

    /**
     * 根据请求头 token 获取用户 id
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if (!StringUtils.hasLength(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }
}
