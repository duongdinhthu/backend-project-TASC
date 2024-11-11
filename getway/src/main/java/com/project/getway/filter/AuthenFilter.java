package com.project.getway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import javax.crypto.SecretKey;

@Component
public class AuthenFilter extends AbstractGatewayFilterFactory<AuthenFilter.Config> {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public static class Config {
        // Các thuộc tính cấu hình cho filter (nếu có)
    }

    public AuthenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (ServerWebExchange exchange, GatewayFilterChain chain) -> {
            // Lấy token từ header Authorization
            String token = getTokenFromRequest(exchange);

            // Kiểm tra token hợp lệ
            if (token == null || !isValidToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Lấy thông tin từ JWT và lưu vào attributes
            Claims claims = getClaimsFromToken(token);
            Integer userId = claims.get("userId", Integer.class);  // Email từ JWT
            String email = claims.getSubject();  // userId từ JWT
            String role = claims.get("role", String.class);
            // Lưu userId và email vào attributes của request để sử dụng sau này
            exchange.getAttributes().put("userId", userId);
            exchange.getAttributes().put("sub", email);
            exchange.getAttributes().put("role", role);
            System.out.println("Lưu thông tin : " + userId +" " + email +" " + role);
            return chain.filter(exchange);
        };
    }

    private String getTokenFromRequest(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7); // Loại bỏ phần "Bearer "
        }
        return null;
    }

    private boolean isValidToken(String token) {
        System.out.println("Token nhận vào: " + token);

        try {
            // Lấy secretKey từ cấu hình (hoặc từ nơi bạn đã lưu trữ)
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            // Kiểm tra tính hợp lệ của token
            Jwts.parserBuilder() // Sử dụng parserBuilder thay vì parser
                    .setSigningKey(secretKey) // Sử dụng khóa bí mật từ cấu hình
                    .build()
                    .parseClaimsJws(token);

            System.out.println("Xác thực thành công");
            return true;
        } catch (Exception e) {
            System.out.println("Xác thực không thành công: " + e.getMessage());
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) {
        // Giải mã và lấy thông tin từ token
        return Jwts.parserBuilder() // Sử dụng parserBuilder thay vì parser
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
