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
	private static final String SECRET_KEY = "rwqrwerewqtweqt";
	
	// 토큰에 대한 유효성 검사
	public String validateAndGetUserId(String token) {
		// 클라이언트가 넘겨주면, SECRET_KEY로 파싱을 합니다. 그러면 header + payload가 남습니다.
		// 클라이언트가 넘겨준 header와 payload를 비교해서 같은지 여부를 확인함
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
	
	
	// 토큰 발행
	public String create(MemberEntity memberEntity) {
		String[] arr = {"ROLE_USER", "ROLE_ADMIN"};
		Map<String, Object> map = new HashMap<>();
		map.put("authority", arr[memberEntity.getRole()]);
		
		Claims claims = Jwts.claims(map);
		
		// long now = new Date().getTime();
		//  Data expire = new Date(now+1000*60*60);
		 
		
		Date expire = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); 
		
		return Jwts.builder()
		.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
		.setSubject(memberEntity.getId())
		.setIssuer("bmsapi")
		.setIssuedAt(new Date())
		.setExpiration(expire)
		.setClaims(claims)
		.compact();
		
	}
}
