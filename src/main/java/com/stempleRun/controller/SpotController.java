package com.stempleRun.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stempleRun.db.dto.Picture;
import com.stempleRun.db.service.Bingo_CallService;
import com.stempleRun.db.service.MemberService;
import com.stempleRun.db.service.PostService;
import com.stempleRun.db.service.SpotService;
import com.stempleRun.db.service.StoryService;



@Controller
@RequestMapping("/hotspot")
public class SpotController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private SpotService spotService;
	
	@Autowired
	private StoryService storyService;
	
	@Autowired
	private Bingo_CallService bingo_callservice;
	
	@RequestMapping(value = "/mypage")
	public String postList(Model model) {
		try {
			model.addAttribute("memberList", service.getMembers());
			model.addAttribute("pictureList", spotService.getPictures());
			
			//리스트 ~VO = 서비스.get랭크 ___> 반환되는값 = 사진,랭크,이름
			
			/*
			 * ArrayList<SpotVO> rank = spotService.getRank();
			 * model.addAttribute("ranking",rank);
			 */
			 		
//			model.addAttribute("postmonth", postService.getMonthlyPost());
//			model.addAttribute("postweek", postService.getWeeklyPost());
			model.addAttribute("bingo", bingo_callservice.getbingoList());
			model.addAttribute("story", storyService.getStorys());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/hotspot/mypage";
	}
	
//	@GetMapping(value="/test")
//	@ResponseBody
//	public ArrayList<Picture> getMap(){
//		return spotService.getPictures();	
//	}
//	@GetMapping(value="/test/week")
//	@ResponseBody
//	public ArrayList<Picture> getMap2(){
//		return spotService.getWeekPictures();	
//	}
//	@GetMapping(value="/test/month")
//	@ResponseBody
//	public ArrayList<Picture> getMap3(){
//		return spotService.getMonthPictures();	
//	}
//	
//	@GetMapping(value="/test/day")
//	@ResponseBody
//	public ArrayList<Picture> getMap1(){
//		return spotService.getDayPictures();	
//	}
	
	
	
	@RequestMapping(value="/hotspot_locate")
	public String LocateHotspot(Model model) {
		try {
			model.addAttribute("postList", postService.getList());
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/hotspot/hotspot_locate";
	}
	
	@RequestMapping(value="/hotspot_main")
	public String MainHotspot(Model model) {
		
		try {
			model.addAttribute("postList", postService.getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/hotspot/hotspot_main";
	}
	@GetMapping(value="/hotspot_locate")
	public String SearchHotspot(@RequestParam(value="keyword") String keyword, Model model) throws Exception {
		
		 model.addAttribute("postList", spotService.getSearchList(keyword));
	
		return "/hotspot/hotspot_locate";
	} 


}
