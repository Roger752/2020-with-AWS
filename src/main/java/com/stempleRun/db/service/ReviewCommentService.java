package com.stempleRun.db.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stempleRun.db.dto.CommentVO;
import com.stempleRun.db.mapper.ReviewCommentMapper;

@Service
public class ReviewCommentService {

	@Autowired
	ReviewCommentMapper reviewcommentmapper;
	
    public ArrayList<CommentVO> commentListService(int bno) throws Exception{
        
        return reviewcommentmapper.commentList(bno);
    }
    
    public int commentInsertService(CommentVO comment) throws Exception{
        
        return reviewcommentmapper.commentInsert(comment);
    }
    
    public int commentUpdateService(CommentVO comment) throws Exception{
        
        return reviewcommentmapper.commentUpdate(comment);
    }
    
    public int commentDeleteService(int cno) throws Exception{
        
        return reviewcommentmapper.commentDelete(cno);
    }
}
