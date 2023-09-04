package kr.co.tj;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.tj.member.Member;
import kr.co.tj.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsServiceImpl implements UserDetailsService{
	
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Member> optional = memberRepository.findByNickName(username);
		
		if(optional.isEmpty()) {
			throw new UsernameNotFoundException("사용자가 잘못 되었습니다.");
		}
		
		
		return new PrincipalDetailsImpl(optional.get());
	}

}
