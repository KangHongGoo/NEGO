package kr.co.tj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import kr.co.tj.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	// 아래 메서드 순서랑 쓰는방법은 외우셔야됩니다
	// 그냥 이게 순서에요...
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
		.authorizeRequests()
		.antMatchers("/**", "/api/auth/**").permitAll()		//허용
		//.antMatchers("/api/**").anonymous() //인증되지 않은사람이 사용가능
	//	.antMatchers("/api/**").hasAnyRole("USER", "ADMIN") //USER, ADMIN만 접근가능
	//	.antMatchers("/api/member").hasAnyRole("ADMIN")
		//블랙리스트는 role이 2라서 user,admin에 안들어가기 떄문에 접근가능에 user admin해놓으면 자동적으로 사용못하게됩니다
		//블랙리스트는 role이 2라서 user,admin에 안들어가기 떄문에 접근가능에 user admin해놓으면 자동적으로 사용못하게됩니다
		.anyRequest()
		.authenticated();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
