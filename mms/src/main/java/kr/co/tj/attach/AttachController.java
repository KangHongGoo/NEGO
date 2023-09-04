package kr.co.tj.attach;



import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/attach")
public class AttachController {
	
	@GetMapping("/download")
	public ResponseEntity<byte[]> download(String filename) {
		
		return FileUploadUtils.download(filename);
	}
	
	
	@GetMapping("/showImage")
	public ResponseEntity<byte[]> showImage(String filename) {
		
		return FileUploadUtils.showImage("c:" + File.separator + "upload", filename);
	}
	
	@PostMapping("/fileupload")
	public String fileupload(MultipartHttpServletRequest mRequest, Model model) {
		
		MultipartFile file = mRequest.getFile("file");
		
		String orgFilename = file.getOriginalFilename();
		System.out.println(orgFilename);
		
		//c드라이브에 upload폴더를 생성한 후 작업하세요
		File path = new File("c:"+File.separator+"upload");
		
		//해당 폴더가 없으면 생성해줌
		if(!path.exists()) {
			path.mkdir();
		}
		
		String datePath = FileUploadUtils.makePath("c:"+File.separator+"upload");
		
		String savedName = FileUploadUtils.makeFilename(orgFilename);
		
		try {
			file.transferTo(new File(path + datePath, savedName));
			
			String dbsaveFilename = datePath+File.separator+savedName;
			
			//db작업이 생략됨.
			//dbsaveFilename과 이 업로드 파일을 보여주는 곳의 id 그리고 이 파일으 업로드한사람의 정보 등을
			//db에 저장해야함 .
			
			
			dbsaveFilename = dbsaveFilename.replace(File.separatorChar, '/');
			
			
			
			model.addAttribute("dbsaveFilename", dbsaveFilename);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		return "attach/read";
	}
	
	@GetMapping("/test")
	public String test() {
		return "attach/test";
	}

}
