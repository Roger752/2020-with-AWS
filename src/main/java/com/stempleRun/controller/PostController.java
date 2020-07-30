package com.stempleRun.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stempleRun.db.dto.PictureVO;
import com.stempleRun.db.dto.PostVO;
import com.stempleRun.db.dto.RecommendVO;
import com.stempleRun.db.service.MemberService;
import com.stempleRun.db.service.PostService;

@Controller
@RequestMapping("/gallery")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private MemberService memberSerivce;

	// 목록
	@RequestMapping("/list")
	public String getList(Model model) throws Exception {
		ArrayList<PostVO> list = postService.getList();

		for (PostVO a : list) {
			String c = memberSerivce.getM_name(a.getM_num());
			a.setM_name(c);
		}

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPic_file(postService.pictureDetail(list.get(i).getP_num()));
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setP_recommend(postService.countReco(list.get(i).getP_num()));
		}
		model.addAttribute("list", list);
		return "gallery/list";
	}

	// 인기순
	@RequestMapping("/list/popular")
	public String getPopular(Model model) throws Exception {
		ArrayList<PostVO> list = postService.getPopular();
		for (PostVO a : list) {
			String c = memberSerivce.getM_name(a.getM_num());
			a.setM_name(c);
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPic_file(postService.pictureDetail(list.get(i).getP_num()));
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setP_recommend(postService.countReco(list.get(i).getP_num()));
		}
		model.addAttribute("list", list);

		return "gallery/list";
	}

	// 최신순
	@RequestMapping("/list/lately")
	public String getLately(Model model) throws Exception {
		ArrayList<PostVO> list = postService.getLately();
		for (PostVO a : list) {
			String c = memberSerivce.getM_name(a.getM_num());
			a.setM_name(c);
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPic_file(postService.pictureDetail(list.get(i).getP_num()));
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setP_recommend(postService.countReco(list.get(i).getP_num()));
		}
		model.addAttribute("list", list);
		return "gallery/list";
	}

	// 게시글 작성
	@RequestMapping("/insert/{pic_num}")
	private String postInsert(@PathVariable int pic_num, Model model) throws Exception {
		model.addAttribute("picture", postService.getPicture(pic_num));
		return "gallery/insert";
	}

	// 게시글 작성 폼
	@RequestMapping(value = "/insertProc", method = RequestMethod.POST)
	private String postInsertProc(int pic_num, HttpServletRequest request) throws Exception {

		PostVO post = new PostVO();
		PictureVO picture = new PictureVO();
		Date ldt = new Date();

		post.setP_title(request.getParameter("title"));
		post.setP_content(request.getParameter("content"));
		post.setP_day(ldt);
		post.setM_num(Integer.parseInt(request.getParameter("m_num")));
		post.setPic_num(Integer.parseInt(request.getParameter("pic_num")));
		post.setP_area(request.getParameter("area"));

		picture.setPic_num(pic_num);
		picture.setPic_latitude(request.getParameter("pic_latitude"));
		picture.setPic_longitude(request.getParameter("pic_longitude"));

		postService.locationUpdate(picture);
		postService.postInsert(post);

		return "redirect:/gallery/list";
	}

	// 게시글 상세
	@RequestMapping("/detail/{p_num}")
	private String postDetail(@AuthenticationPrincipal UserDetails m_id, HttpSession http, @PathVariable int p_num,
			Model model) throws Exception {

		http.setAttribute("userName", m_id.getUsername());
		model.addAttribute("detail", postService.postDetail(p_num));
		model.addAttribute("writer", postService.postWriter(p_num));
		model.addAttribute("m_id", m_id.getUsername());
		model.addAttribute("count", postService.countReco(p_num));
		String name;
		name = postService.pictureDetail(p_num);
		model.addAttribute("picture", name);

		return "gallery/detail";
	}

	// 추천하기 폼
	@ResponseBody
	@RequestMapping(value = "/recoProc", method = RequestMethod.POST)
	private String getRecoProc(HttpServletRequest request, RecommendVO reco, Model model) throws Exception {

		reco.setP_num(Integer.parseInt(request.getParameter("p_num")));
		reco.setM_id(request.getParameter("m_id"));

		String a;

		RecommendVO recommend = postService.compareReco(reco);
		if (recommend == null) {
			postService.getReco(reco);
			postService.getRecommend(Integer.parseInt(request.getParameter("p_num")));
			a = "추천 되었습니다.";

		} else {
			a = "이미 추천하였습니다.";
		}

		return a;
	}

	// 위치 정보
	@RequestMapping("/locationDetail/{p_num}")
	@ResponseBody
	private PictureVO locationDetail(@PathVariable int p_num) throws Exception {
		return postService.locationDetail(p_num);
	}

	// 지도 정보
	@RequestMapping("/detail/map/{p_num}")
	private String mapDetail(@PathVariable int p_num, Model model) throws Exception {
		model.addAttribute("p_num", p_num);
		return "gallery/map";
	}

	// 게시글 삭제
	@RequestMapping("/delete/{p_num}")
	private String postDelete(@PathVariable int p_num) throws Exception {
		postService.postDelete(p_num);
		return "redirect:/gallery/list";
	}

	// 게시글 수정
	@RequestMapping("/update/{p_num}")
	private String postUpdate(@PathVariable int p_num, PictureVO picture, Model model) throws Exception {
		model.addAttribute("update", postService.postDetail(p_num));
		model.addAttribute("pic_num", postService.getPicnum(p_num));
		model.addAttribute("pic_tag", postService.getPictag(p_num));
		model.addAttribute("pic_latitude", postService.getLatitude(p_num));
		model.addAttribute("pic_longitude", postService.getLongitude(p_num));

		String name;
		name = postService.pictureDetail(p_num);
		name = name;
		model.addAttribute("picture", name);

		return "gallery/update";
	}

	// 수정 폼
	@RequestMapping(value = "/updateProc", method = RequestMethod.POST)
	private String postUpdateProc(PostVO post, PictureVO picture, HttpServletRequest request, Model model)
			throws Exception {

		post.setP_title(request.getParameter("p_title"));
		post.setP_content(request.getParameter("p_content"));
		post.setP_num(Integer.parseInt(request.getParameter("p_num")));

		picture.setPic_latitude(request.getParameter("p_latitude"));
		picture.setPic_longitude(request.getParameter("p_longitude"));
		picture.setPic_num(Integer.parseInt(request.getParameter("pic_num")));
		picture.setPic_tag(request.getParameter("pic_tag"));

		postService.pictagUpdate(picture);
		postService.locationUpdate(picture);
		postService.postUpdate(post);

		return "redirect:/gallery/detail/" + request.getParameter("p_num");
	}

	// 핫스팟
	@RequestMapping("/testpage/{page}")
	private String Test(@PathVariable String page, Model model) throws Exception {
		if (page.equals("day")) {
			List<PostVO> list = postService.getDailyPost();
			System.out.println(list.size()); //
			model.addAttribute("info", list);
		} else if (page.equals("week")) {
			model.addAttribute("info", postService.getWeeklyPost());
		} else if (page.equals("month")) {
			model.addAttribute("info", postService.getMonthlyPost());
		}

		model.addAttribute("page", page);
		return "gallery/test";
	}

	// 일간
	@RequestMapping("/test/day")
	@ResponseBody
	public List<PostVO> getDailyPost() {
		return postService.getDailyPost();
	}

	// 주간
	@RequestMapping("/test/week")
	@ResponseBody
	public List<PostVO> getWeeklyPost() {
		return postService.getWeeklyPost();
	}

	// 월간
	@RequestMapping("/test/month")
	@ResponseBody
	public List<PostVO> getMonthlyPost() {
		return postService.getMonthlyPost();
	}
}