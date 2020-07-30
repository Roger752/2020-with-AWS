package com.stempleRun.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stempleRun.db.dto.CommentVO;
import com.stempleRun.db.dto.ReviewBoardVO;
import com.stempleRun.db.service.ReviewCommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	ReviewCommentService mCommentService;
	
    @RequestMapping("/list") //댓글 리스트
    @ResponseBody
    private ArrayList<CommentVO> mCommentServiceList(Model model, int bno) throws Exception{
//    	ArrayList<CommentVO> list = mCommentService.commentListService();
//    	
//    	for(int i=0;i<list.size();i++) {
//    		System.out.println(list.get(i).getContent());
//    		System.out.println(list.get(i).getBno());
//    		System.out.println(list.get(i).getCno());
//    	}
//        
        return mCommentService.commentListService(bno);
    }
    
    @RequestMapping("/insert") //댓글 작성 
    @ResponseBody
    private int mCommentServiceInsert(Principal principal, @RequestParam int bno, @RequestParam String content) throws Exception{
    	ReviewBoardVO reviewboard = new ReviewBoardVO();
        CommentVO comment = new CommentVO();
		String username = principal.getName();
		
        comment.setBno(bno);
        comment.setContent(content);
        //로그인 기능을 구현했거나 따로 댓글 작성자를 입력받는 폼이 있다면 입력 받아온 값으로 사용하면 됩니다. 저는 따로 폼을 구현하지 않았기때문에 임시로 "test"라는 값을 입력해놨습니다.
        comment.setWriter(username);  
        
        return mCommentService.commentInsertService(comment);
    }
    
    @RequestMapping("/update") //댓글 수정  
    @ResponseBody
    private int mCommentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception{
        CommentVO comment = new CommentVO();
        comment.setCno(cno);
        comment.setContent(content);
        
        return mCommentService.commentUpdateService(comment);
    }
    
    @RequestMapping("/delete/{cno}") //댓글 삭제  
    @ResponseBody
    private int mCommentServiceDelete(@PathVariable int cno) throws Exception{
        
        return mCommentService.commentDeleteService(cno);
    }
}
