package kr.co.tj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.tj.member.Member;

public class PrincipalDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Member member;

	public PrincipalDetailsImpl(Member member) {
		// TODO Auto-generated constructor stub
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		String role = null;
		
		if("usr".equals(member.getRole())) {
			role = UserRole.USER.getValue();
			
		}else if("adm".equals(member.getRole())){
			role = UserRole.ADMIN.getValue();
//		}else if("mng".equals(member.getRole())) {
//			role = UserRole.MANAGER.getValue();
		}
		authorities.add(new SimpleGrantedAuthority(role));
		
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getNickName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
