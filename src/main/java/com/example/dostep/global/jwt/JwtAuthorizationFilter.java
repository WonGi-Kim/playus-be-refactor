package com.example.dostep.global.jwt;

import com.example.dostep.domain.employee.model.Employee;
import com.example.dostep.domain.employee.EmployeeRepository;
import com.example.dostep.domain.security.UserDetailsImpl;
import com.example.dostep.global.exception.CustomException;
import com.example.dostep.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JwtAuthorizationFilter")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final EmployeeRepository employeeRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = JwtUtil.getJwtTokenFromHeader(request);

        if (StringUtils.hasText(token)) {
            try {
                JwtUtil.validateToken(token);  // JWT 검증
                Claims userInfo = JwtUtil.getUserInfoFromToken(token);
                setAuthentication(userInfo.getSubject());  // username 기반으로 인증 설정
            } catch (CustomException e) {
                handleTokenException(response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        Employee employee = employeeRepository.findByAccountUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.EMPLOYEE_NOT_FOUND)
        );
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(employee);
        return new UsernamePasswordAuthenticationToken(userDetailsImpl, null, userDetailsImpl.getAuthorities());
    }

    private void handleTokenException(HttpServletResponse response, CustomException e) throws IOException {
        response.setStatus(e.getStatusCode().value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(e.getMessage());
    }

    private void updateAccessToken(HttpServletResponse response, String token) throws IOException {
        String username = JwtUtil.getUserInfoFromToken(token).getSubject();
        Employee employee = employeeRepository.findByAccountUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        String refreshToken = employee.getAccount().getChangedPassword();  // 예시 필드명
        if (refreshToken != null && JwtUtil.isTokenValid(refreshToken)) {
            String newAccessToken = JwtUtil.createToken(employee.getAccount().getUsername(), JwtUtil.ACCESS_TOKEN_EXPIRATION);
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, "Bearer " + newAccessToken);
        } else {
            response.setStatus(401);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Refresh 토큰이 유효하지 않거나 만료되었습니다.");
        }
    }
}
