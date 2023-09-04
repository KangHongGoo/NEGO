package kr.co.tj.sec;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.tj.member.MemberEntity;

@Component
public class TokenProvider {
	
	private static final String SECRET_KEY = "123qwe123qe";
	
	public String validateAndGetUserId(String token) {
		
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	public String validateAndAuthority(String token) {
		
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		return (String)claims.get("authority");

	}

	public String create(MemberEntity memberEntity) {
		String[] arr = {"ROLE_USER", "ROLE_ADMIN"};
		Map<String, Object> map = new HashMap<>();
		map.put("authority", arr[memberEntity.getRole()]);
		
		Claims claims = Jwts.claims(map);
		
		Date expire = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.setSubject(memberEntity.getId())
				.setIssuer("rmsapi")
				.setIssuedAt(new Date())
				.setExpiration(expire)
				.setClaims(claims)
				.compact();
			
				
				
				
	}
}

