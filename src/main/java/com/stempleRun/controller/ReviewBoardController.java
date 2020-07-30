package com.stempleRun.controller;

import java.io.PrintWriter;
import java.security.Principal;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stempleRun.db.dto.ReviewBoardVO;
import com.stempleRun.db.service.ReviewBoard;
import com.stempleRun.db.service.StorageService;

@Controller
@RequestMapping("/review")
public class ReviewBoardController {
	
	@Autowired
	ReviewBoard reviewBoard;
	
	@Autowired
	StorageService storageService;
	
//	// 게시글 일반추천
//	@RequestMapping("/recommendProc/{bno}")
//	private String getRecommend(Principal principal, @PathVariable int bno, Model model, HttpServletResponse response) throws Exception {
//		String username = principal.getName();
//		System.out.println("user 이름:" + username);
////		sec:authentication="principal.username"
//		if(username.equals("성춘향")) {
//		model.addAttribute("recommend", reviewBoard.getRecommend(bno));
//		}
////		else if(username.equals("홍길동")) {
////			model.addAttribute("recommend", reviewBoard.getRecommend(bno));
////			response.setContentType("text/html; charset=UTF-8");
////			PrintWriter out = response.getWriter();
////			out.println("<script>alert('관리자가아닙니다.'); history.go(-1);</script>");
////			out.flush();
////		}
//		return "redirect:/review/reviewboarddetail/" + bno;
//	}
	
	// 게시글 추천
	@RequestMapping("/adminrecommendProc/{bno}")
	private String normalgetRecommend(@PathVariable int bno, Model model) throws Exception {
		//조회수 증가
    	reviewBoard.reviewboardCount(bno);
		return "redirect:/review/reviewboarddetail/" + bno;
	}
	
	
	
	@RequestMapping(value= {"","/"})
	public String getList(Model model) throws Exception{
		try {
			model.addAttribute("a",reviewBoard.getList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/reviewboard/review";
	}
	
	@RequestMapping("reviewboarddetail/{bno}")
	private String reviewboardDetail(@PathVariable int bno, Model model) throws Exception{
		try {
			model.addAttribute("reviewboarddetail",reviewBoard.reviewboardDetail(bno));
			//조회수 증가
	    	reviewBoard.getRecommend(bno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/reviewboard/reviewboarddetail";
	}
	
	@RequestMapping("reviewboardinsert") //게시글 작성 폼 호출
	private String reviewboardInsert() {
		return "/reviewboard/reviewboardinsert";
	}
	
	
    @RequestMapping(value="/insertProc", method=RequestMethod.POST) //게시글 작성 폼 호출 예외? 이해가필요함
    private String boardInsertProc(Principal principal, HttpServletRequest request, MultipartFile[] file) throws Exception{

        ReviewBoardVO reviewboard = new ReviewBoardVO();
//        reviewboard.setBno(Integer.parseInt(request.getParameter("bno")));
        
        //이건 파일 몇갠지 검사 => 근데 내가 input type:file에 multiple을 안줘서 여러개 업로드가 안될꺼임
        System.out.println(file.length);
        for(MultipartFile multipartfile : file) {
        	// 이건 파일이름 출력해봄
        	System.out.println("asdfasefasdfasd" + multipartfile.getOriginalFilename());
        	
        	// ./src/main/resources/static/file 폴더로 저장
        	storageService.store(multipartfile);
        	
        	//db에 저장하려고 값 세팅중
			String username = principal.getName();
        	reviewboard.setTitle(request.getParameter("title"));
            reviewboard.setWriter(username);
        	reviewboard.setType(request.getParameter("type"));
            reviewboard.setMemo(request.getParameter("memo"));
            
            //요건 파일원본 이름
            reviewboard.setFilename(multipartfile.getOriginalFilename());
            
            // 요건 db에 게시글 저장
            reviewBoard.reviewboardInsert(reviewboard);
        }  
        return "redirect:/review";
    }
    
	@RequestMapping("reviewboardupdate/{bno}") //게시글 수정 폼 호출
	private String reviewboardUpdate(@PathVariable int bno, Model model) throws Exception{
		try {
			model.addAttribute("reviewboardupdate",reviewBoard.reviewboardDetail(bno));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/reviewboard/reviewboardupdate";
	}
	
    @RequestMapping(value="/updateProc", method=RequestMethod.POST) //게시글 수정 폼 호출 예외? 이해가필요함
    private String boardUpdateProc(HttpServletRequest request) throws Exception{
        
        ReviewBoardVO reviewboard = new ReviewBoardVO();
        reviewboard.setBno(Integer.parseInt(request.getParameter("bno")));
        System.out.println(Integer.parseInt(request.getParameter("bno")));
        reviewboard.setTitle(request.getParameter("title"));
        System.out.println(request.getParameter("title"));
        reviewboard.setType(request.getParameter("type"));
        System.out.println(request.getParameter("type"));
        reviewboard.setMemo(request.getParameter("memo"));
        System.out.println(request.getParameter("memo"));
        reviewBoard.reviewboardUpdate(reviewboard);
        
        return "redirect:/review/reviewboarddetail/"+request.getParameter("bno");
    }
	
    
    @RequestMapping("reviewboarddelete/{bno}")
    private String reviewboardDelete(@PathVariable int bno) throws Exception{
        
        ReviewBoardVO reviewboard = new ReviewBoardVO();
    	reviewBoard.reviewboardDelete(bno);
    	
        return "redirect:/review";
    }
    
    
    @RequestMapping(value = "reviewboarddetail/{bno}/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<Resource> asdf(@PathVariable String fileName) {
		System.out.println(fileName);
		Resource file = storageService.loadAsResource(fileName);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	 @RequestMapping(value = "/{filename:.+}", method = RequestMethod.GET) 
	 public ResponseEntity<Resource> serveFile(@PathVariable String filename) { Resource
	  file = storageService.loadAsResource(filename); return ResponseEntity.ok()
	  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	  file.getFilename() + "\"") .body(file); }
	 
}
