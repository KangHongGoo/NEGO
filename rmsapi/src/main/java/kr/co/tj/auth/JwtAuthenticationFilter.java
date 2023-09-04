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

import kr.co.tj.sec.TokenProvider;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
	throws ServletException, IOException{
		
		try {
			String token = parseBearerToken(request);
			
			
			if(token != null && !token.equalsIgnoreCase("null")) {
				
			String userId = tokenProvider.validateAndGetUserId(token);
			String authority = tokenProvider.validateAndAuthority(token);
			
			AbstractAuthenticationToken aat = new UsernamePasswordAuthenticationToken(userId, null,
					AuthorityUtils.createAuthorityList(authority));
			
			aat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContext secCtx = SecurityContextHolder.createEmptyContext();
			secCtx.setAuthentication(aat);
			SecurityContextHolder.setContext(secCtx);
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("인증실패");
		}
		
		
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
