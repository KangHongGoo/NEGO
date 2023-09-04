package kr.co.tj.se;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.tj.member.MemberEntity;

@Component
public class TokenProvider {

	private static final String SECRET_KEY="dsdssdsdsdsdsdwqevceqv";
	// =================================================================//
	// 아래 두개 메서드를 통합할수는 있지만 합치게되면								//
	// 가독성이 떨어지며 두개의 메서드는 반환하는 정보가 다르기 때문에 유지보수가 어려움		//
	// =================================================================//
	
	//JWT를 파싱(parsing)하고, 검증하여 사용자 권한 정보를 반환
	public String validateAndAuthority(String token) {
		
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		return (String)claims.get("authority");
	}
	
	//JWT를 파싱(parsing)하고, 검증하여 사용자 아이디를 반환
	public String validateAndGetUserId (String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	// =================================================================
	
	public String create(MemberEntity Entity) {
		String[] arr = {"ROLE_USER", "ROLE_ADMIN", "ROLE_BLACKLIST"};
		Map<String, Object> map = new HashMap<>();
		map.put("authority", arr[Entity.getRole()]);
		
		Claims claims = Jwts.claims(map);
		claims.put("username", Entity.getUsername());
		
		// Jwts만료시간설정 1시간
		Date expire = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 비밀키 설정
				.setSubject(Entity.getId())						// id를 주체로함
				.setIssuer("marketapi")							// jwt를 발행한 객체
				.setIssuedAt(new Date())						// jwt 발행시간 설정
				.setExpiration(expire)							// jwt 발행만료시간 설정
				.setClaims(claims)								// jwt 내부 클레임설정
				.compact();										// jwt를 문자열로만듬
	}
	
	public class JwtDecoder{
		public Claims decode(String token, String secret) {
			SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

	        return Jwts.parser()
	                .setSigningKey(key)
	                .parseClaimsJws(token)
	                .getBody();
	    }
		
	}
	
}
