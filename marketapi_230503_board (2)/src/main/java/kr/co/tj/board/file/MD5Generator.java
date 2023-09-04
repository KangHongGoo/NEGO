package kr.co.tj.board.file;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
	private String result;
	
	
	// MessageDigest(MD) 클래스는 다양한 암호화 해시 함수를 지원하는 클래스
	// 이 클래스는 시큐리티 패키지에 속해있대요
	// 해시 함수는 쉽게 말해 더 똑똑한 암호화인데
	// 입력 데이터가 같으면 항상 같은 출력값을 반환하고,
	// 조금이라도 달라지면 완전히 다른 값을 출력한답니다.
	
	
	// 입력받은 문자열 'input'을 MD5 해시값으로 변환하는 생성자
	public MD5Generator(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		// MD5 알고리즘을 사용하는 'MessageDigest' 객체를 생성
		MessageDigest mdMD5 = MessageDigest.getInstance("MD5");
		
		// 'mdMD5' 객체에 'input' 문자열이 UTF-8로 인코딩된 바이트 배열을 전달하여 업데이트
		mdMD5.update(input.getBytes("UTF-8"));
		
		// mdMD5 객체로부터 MD5 해시값을 계산하고, byte[] 형태로 가져옵니다.
		byte[] md5Hash = mdMD5.digest();
		
		// MD5 해시값을 16진수 문자열 형태로 변환하기 위한 객체 생성
		StringBuilder hexMd5hash = new StringBuilder();
		
		// 바이트배열의 각 요소에 대해 16진수 문자열로 변환하여 'hexMd5hash'에 추가
		for(byte b : md5Hash) {
			
		// 현재 바이트 값을 16진수 문자열로 변환 , '%02x'는 2자리 16진수로 표현됨
		String hexString = String.format("%02x", b); 
		hexMd5hash.append(hexString);
		}
		
		result = hexMd5hash.toString();
	}
	public String toString() {
		return result;
	}

}
