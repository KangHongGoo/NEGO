package kr.co.tj.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.co.tj.se.TokenProvider;


//OncePerRequestFilter를 상속받음
//한번만 실행되도록 하여 중복된 요청을 방지
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = parseBearerToken(request);
			if(token != null && !token.equalsIgnoreCase("null")) {
				
				// validateAndGetUserId, validateAndAuthority메서드를 사용해서
				// 토큰의 정보를 검증
				String userId = tokenProvider.validateAndGetUserId(token);
				String authority = tokenProvider.validateAndAuthority(token);
				
				// 생성된 인증 객체에 대한 세부정보를 설정
				AbstractAuthenticationToken aat = new UsernamePasswordAuthenticationToken(userId, null,
														AuthorityUtils.createAuthorityList(authority));
				aat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// 검증된 정보를 사용하여 스프링시큐리티 인증객체 생성
				SecurityContext secCtx = SecurityContextHolder.createEmptyContext();
				secCtx.setAuthentication(aat);
				SecurityContextHolder.setContext(secCtx);
			}
		} catch (Exception e) {
			//실패할경우 Exception 날림
			e.printStackTrace();
			System.out.println("==========인증에 실패했습니다===========");
		}
		
		// 인증이 "Bearer "로 시작하는경우 나머지 문자열만 반환
		filterChain.doFilter(request, response);
		
		
		
		
	}

	
	private String parseBearerToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			return null;
		}
		return bearerToken.substring(7);
	}
}
