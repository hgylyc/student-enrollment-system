package com.kaifa.project.studentenrollmentsysytem.pojo;
import com.kaifa.project.studentenrollmentsysytem.pojo.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    private static final String SECRET_KEY = "your_secret_key";
    public static String createToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getId())
                .claim("role", account.getIdentity())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
