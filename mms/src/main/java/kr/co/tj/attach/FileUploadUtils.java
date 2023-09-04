package kr.co.tj.attach;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class FileUploadUtils {
	
	public static ResponseEntity<byte[]> download(String filename) {
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		InputStream in = null;
		
		try {
			in = new FileInputStream("c:"+File.separator+"upload"+filename);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			String orgFilename = FileUploadUtils.getOrgFilename(filename);
			
			orgFilename = new String(orgFilename.getBytes("UTF-8"), "ISO-8859-1");
			
			headers.add("content-Disposition", "attachment;filename=\""+orgFilename+"\"");
			
			byte[] arr = IOUtils.toByteArray(in);
			
			entity = new ResponseEntity<byte[]>(arr, headers, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return entity;
	}
	
	public static String getOrgFilename(String filename) {
		int idx = filename.indexOf("_", 12) +1;
		
		return filename.substring(idx);
	}
	
	public static ResponseEntity<byte[]> showImage(String uploadPath, String filename) {
		ResponseEntity<byte[]> entity = null;
		
		MediaType mType = getMediaType(filename);
		HttpHeaders headers = new HttpHeaders();
		InputStream in = null;
		try {
			in = new FileInputStream(uploadPath + filename);
			headers.setContentType(mType);
			byte[] arr = IOUtils.toByteArray(in);
			
			entity = new ResponseEntity<byte[]>(arr, headers, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return entity;
	}
	
	public static MediaType getMediaType(String filename) {
		int idx = filename.lastIndexOf(".")+1;
		String formatName = filename.substring(idx);
		// 대소문자 구분하지 않게 하기위해 모두 소문자로 만드는 코드
		formatName = formatName.toLowerCase();
		
		Map<String, MediaType> map = new HashMap<>();
		map.put("png", MediaType.IMAGE_PNG);
		map.put("gif", MediaType.IMAGE_GIF);
		map.put("jpg", MediaType.IMAGE_JPEG);
		map.put("jpeg", MediaType.IMAGE_JPEG);
		
		
		
		return map.get(formatName);
	}
	
	
	public static String makeFilename(String orgFilename) {
		
		String uid = UUID.randomUUID().toString();
		String savedName = uid + "_" + orgFilename;
		
		return savedName;
	}
	
	
	public static void makeDir(String uploadPath, String yearPath, String monthPath, String datePath) {
		// TODO Auto-generated method stub
		
		File yearFile = new File(uploadPath, yearPath);
		if(!yearFile.exists()) {
			yearFile.mkdir();
		}
		
		File monthFile = new File(uploadPath, monthPath);
		if(!monthFile.exists()) {
			monthFile.mkdir();
		}
		
		File dateFile = new File(uploadPath, datePath);
		if(!dateFile.exists()) {
			dateFile.mkdir();
		}
		
	}

	
	public static String makePath(String uploadPath) {
		
		int[] calendarInfo = getCalendarInfo();
		
		int year = calendarInfo[0];
		String yearPath = File.separator + year;
		
		int month = calendarInfo[1];
		// 폴더명이 5가 아닌 05 형식으로 생성되게 함
		String sMonth = month < 10 ? "0" + month : month + "";
		
		String monthPath = yearPath + File.separator + sMonth;
		
		int date = calendarInfo[2];
		String sDate = date < 10 ? "0" + date : date + "";
		String datePath = monthPath + File.separator + sDate ;
		
		makeDir(uploadPath , yearPath, monthPath, datePath);
		
		return datePath;		
	}
	
	
	public static int[] getCalendarInfo() {
		Calendar c = Calendar.getInstance();
		
		 int year = c.get(Calendar.YEAR);
		 int month = c.get(Calendar.MONTH)+1;
		 int date = c.get(Calendar.DATE);
		 
		 int[] calendarInfo = {year, month,date};
		
		return calendarInfo;
	}

}
