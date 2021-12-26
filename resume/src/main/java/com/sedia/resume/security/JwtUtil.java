package com.sedia.resume.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sedia.resume.domain.LoginUser;
import com.sedia.resume.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
public class JwtUtil {
    public static final long EXPIRATION_TIME = 86_400_000 * 5; // 5天
    public static final String TOKEN_PREFIX = "Bearer"; // Token前缀
    public static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
    public static final Key key = MacProvider.generateKey(); // 給定一組密鑰，用來解密以及加密使用
    public static final ObjectMapper MAPPER = new ObjectMapper();

    // JWT產生方法
    public static void addAuthentication(HttpServletResponse response, Authentication auth) {
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        // 生成JWT
        String jwt = Jwts.builder().setSubject(auth.getName()).setExpiration(expiration).signWith(key).compact();
        // 把JWT傳回response
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            UserEntity user = (UserEntity) auth.getPrincipal();
            LoginUser loginUser = LoginUser.builder().id(user.getId()).account(user.getAccount()).jwt(jwt)
                    .expiration(expiration).build();
            response.getOutputStream().println(MAPPER.writeValueAsString(loginUser));
            log.info("登入成功");
        } catch (Exception e) {
            log.error("產生 JWT 時發生錯誤", e);
        }
    }

    // JWT驗證方法
    public static Authentication getAuthentication(HttpServletRequest request) {
        // 從request的header拿回token
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // 解析 Token
            try {
                Claims claims = Jwts.parser()
                        // 驗證
                        .setSigningKey(key)
                        // 去掉 Bearer
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

                // 拿用户名
                String account = claims.getSubject();
                // 得到權限
                List<GrantedAuthority> authorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList((String) claims.get("authorize"));
                return StringUtils.hasText(account)
                        ? new UsernamePasswordAuthenticationToken(account, null, authorities) : null;
            } catch (JwtException e) {
                log.error("JWT 驗證發生錯誤", e);
            }
        }
        return null;
    }
}