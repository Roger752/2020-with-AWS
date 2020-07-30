package com.stempleRun.db.mapper;

import java.util.ArrayList;

import com.stempleRun.db.dto.CommentVO;

public interface ReviewCommentMapper {
    // 댓글 개수
    public int commentCount() throws Exception;
 
    // 댓글 목록
    public ArrayList<CommentVO> commentList(int bno) throws Exception;
 
    // 댓글 작성
    public int commentInsert(CommentVO comment) throws Exception;
    
    // 댓글 수정
    public int commentUpdate(CommentVO comment) throws Exception;
 
    // 댓글 삭제
    public int commentDelete(int cno) throws Exception;
}
