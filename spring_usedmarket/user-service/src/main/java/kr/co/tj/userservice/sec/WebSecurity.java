package kr.co.tj.userservice.sec;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.cors()
		.and()
		.csrf() 
		.disable() //  Cross-Origin Resource Sharing (CORS) 정책을 활성화하고, Cross-Site Request Forgery (CSRF) 공격 방지 기능을 비활성화하는 설정입니다
		.httpBasic()
		.disable() //  HTTP 기본 인증을 비활성화합니다. 기본 인증은 사용자 이름과 암호를 사용해 인증을 수행하는 방식입니다.
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 관리 방식을 설정하며, STATELESS는 상태를 유지하지 않는 세션 관리 방식을 의미합니다. 이는 RESTful API와 같이 상태를 서버에 저장하지 않는 경우에 사용됩니다.
		
		
		
		http.authorizeRequests()
		.antMatchers("/user-service/users/**").permitAll(); // 특정 경로에 대한 접근 권한을 설정하는 부분입니다. "/user-service/users/**" 경로에 대한 접근은 모든 사용자에게 허용됩니다.
		
		http
		.headers().frameOptions().disable(); //  X-Frame-Options 헤더를 비활성화합니다. 이 설정은 웹 페이지가 다른 웹 페이지의 프레임으로 포함되는 것을 방지하는 데 사용됩니다.
		
	}

}
