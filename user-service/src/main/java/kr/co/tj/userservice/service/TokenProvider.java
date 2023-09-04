package kr.co.tj.userservice.service;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.tj.userservice.dto.UserEntity;

@Component
public class TokenProvider {
	
	//private static final String SECRET_KEY = "mkmkmkmkmkmkmkmk";
	
	private Environment env;
	
	
	@Autowired
	public TokenProvider(Environment env) {
		super();
		this.env = env;
	}

	
    
    

	public String create(UserEntity userEntity) {
		long now = System.currentTimeMillis();
		
		Date today = new Date(now);
		Date exireDate = new Date(now+1000*1*60*60*24);
		
	//	String str = env.getProperty("data.SECRET_KEY");
	//    String encodedStr = Base64.getEncoder().encodeToString(str.getBytes());
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, env.getProperty("data.SECRET_KEY"))
				.setSubject(userEntity.getUsername())
				.setIssuer("user-service")
				.setIssuedAt(today)
				.setExpiration(exireDate)
				.compact();
	}
}
