package com.stempleRun.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.jni.File;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.stempleRun.db.dto.EntityMember;
import com.stempleRun.db.dto.Picture;
import com.stempleRun.db.dto.PostVO;
import com.stempleRun.db.repository.MemberJpaRepository;
import com.stempleRun.db.repository.PictrueRepository;
import com.stempleRun.db.service.photoService;

@Controller
@RequestMapping("/photoscloud")
public class photoController {

	@Autowired
	photoService photoService;

	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Autowired
	PictrueRepository pictrueRepository;

	/* 클라우드 목록 */
	@RequestMapping("/list") // -> /photoscloud/list
	private String photoList(@AuthenticationPrincipal UserDetails userDetails, Model model) throws Exception {

		/* 작성자별 클라우드 */
		EntityMember user = memberJpaRepository.findByUserId(userDetails.getUsername()); // 지금 로그인 한 정보
		List<Picture> photolist = pictrueRepository.findAll(); // 리스트
		List<Picture> picturelist = new ArrayList<Picture>();

		for (int i = 0; i < photolist.size(); i++) {
			if (user.getM_num() == photolist.get(i).getM_num()) {
				picturelist.add(photolist.get(i));
			}
		}

		// 사진 담아오기
		model.addAttribute("photolist", picturelist);

		return "photoscloud/list";
	}

	/* 사진 등록 */
	@RequestMapping("/insert") // /photoscloud/insert
	private String photoInsertForm() {

		return "photoscloud/insert";
	}

	@PostMapping("/insertProc") // -> /photoscloud/insertProc
	public String photoInsertProc(@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam("pic_file") MultipartFile pic_file, String pic_title, String pic_content, String pic_tag,
			String pic_num, /* String pic_sns, */ String pic_place) throws Exception {

		// 사진을 등록한 아이디 정보를 찾기
		EntityMember member = memberJpaRepository.findByUserId(userDetails.getUsername());

		// 사진이 똑같은 이름일 때 뒤에 랜덤으로 이름 붙여서 만들어줌
		UUID uuid = UUID.randomUUID();
		String[] names = pic_file.getOriginalFilename().split("\\.");
		System.out.println("aaaa : " + names[0]);
		if(names.length==0) {
			System.out.println(pic_file.getOriginalFilename() + " null");
			return "redirect:/photoscloud/list";
		} else {
			System.out.println(names[0]);
		}
		String uuidFilename = names[0]+ "_" + uuid + "." + names[names.length-1];
		System.out.println(uuidFilename);
		// 저장할 경로 지정
		String fileRealPath = "./src/main/resources/static/img/";
		String uploads = "/img/";
		// 파일을 경로에 저장
		Path filePath = Paths.get(fileRealPath + uuidFilename);
		// 파일 경로 (문자열) 를 DB에 저장
		Path dbfilepath = Paths.get(uploads + uuidFilename);

		try {
			// file이 비어 있지 않다면
			if (!pic_file.getOriginalFilename().isEmpty()) {
				Files.write(filePath, pic_file.getBytes()); // 하드디스크 기록
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 새로운 객체( 사진 객체 ) 생성
		Picture picture = new Picture();

		// 사진번호가 널이 아니면
		if (pic_num != null) {
			Optional<Picture> uniqPicO = pictrueRepository.findById(Integer.parseInt(pic_num));
			Picture uniqPic = uniqPicO.get();
			// 사진이 없으면 번호 생성
			picture.setPic_num(Integer.parseInt(pic_num));
			// 수정할때는 새로 업데이트 해준다.
			picture.setPic_date(LocalDate.now());

			// 사진이 비어있다면
			if (pic_file.getOriginalFilename().isEmpty()) {
				// 원래 있던 사진으로 등록
				picture.setPic_file(uniqPic.getPic_file());
			} else {
				// 새로운 사진 등록
				picture.setPic_file(dbfilepath.toString());
				// img/filename+uuid
				Path pat = Paths.get("./src/main/resources/static/" + uniqPic.getPic_file());
				// 사진 변경할 경우 원래 있던 사진을 없애버리자
				Files.deleteIfExists(pat);
			}
		} else {
			// 사진번호가 널이면 새롭게 등록을 한다.
			picture.setPic_file(dbfilepath.toString());
		}

		picture.setPic_title(pic_title);
		picture.setPic_content(pic_content);
		picture.setPic_tag(pic_tag);
		picture.setPic_place(pic_place);
		// picture.setPic_sns(pic_sns);
		picture.setM_num(member.getM_num());

		// 사진 저장
		pictrueRepository.save(picture);

		return "redirect:/photoscloud/list";
	}

	@GetMapping("/view/{id}")
	public @ResponseBody Picture photoview(@PathVariable int id) {

		Optional<Picture> photoO = pictrueRepository.findById(id);
		Picture photo = photoO.get();

		return photo;
	}

	/**
	 * 수정 페이지 이동
	 * 
	 * @param pic_num
	 * @param model
	 * @return /photoscloud/update.jsp
	 */
	@RequestMapping("/update/{pic_num}")
	private String update(@PathVariable int pic_num, Model model) {
		// System.out.println("번호를 들고 왔어요 :: "+ pic_num); // 픽쳐 번호

		// 사진 객체 DB 데이터 취득
		Optional<Picture> picO = pictrueRepository.findById(pic_num); // 하나의 픽쳐가 선택

		// 사진 객체 저장
		Picture picture = picO.get();

		model.addAttribute("picture", picture);

		// 데이터를 들고 update 페이지로 이동
		return "/photoscloud/update";
	}

	@RequestMapping("/delete/{pic_num}")
	private String photoDelete(@PathVariable int pic_num) throws Exception {
		try {
			pictrueRepository.deletepost(pic_num); // 클라우드 지우면 포스트도 같이 날아감
		} catch (Exception e) {

		}

		// System.out.println(" post 삭제");
		Optional<Picture> picO = pictrueRepository.findById(pic_num);

		Picture picture = picO.get();
		String filename = picture.getPic_file();

		Path fileName = Paths.get("./src/main/resources/static/" + filename);
		Files.deleteIfExists(fileName);
		// System.out.println("img 삭제");
		// System.out.println(filename);

		pictrueRepository.deleteById(pic_num); // 삭제
		// System.out.println("picture 삭제");
		return "redirect:/photoscloud/list";
	}

	@RequestMapping("/tag")
	private String phototag() {

		return "photoscloud/tag";
	}

	/* 태그별 사진 */
	@RequestMapping("/tagproc")
	private String phototag(@AuthenticationPrincipal UserDetails userDetails, String tag, Model model) {

		EntityMember member = memberJpaRepository.findByUserId(userDetails.getUsername()); // 지금 로그인 한 정보
		int m_num = member.getM_num();

		List<Picture> taglist = pictrueRepository.findAll(); // 태그리스트   
	
		List<Picture> phototag = new ArrayList<Picture>();

		if (tag != null) {
			for (int i = 0; i < taglist.size(); i++) {
				if (taglist.get(i).getPic_tag().contains(tag) && m_num == taglist.get(i).getM_num()) {
					phototag.add(taglist.get(i));
				}
			}

			/*
			 * if (tag != null) { for (int i = 0; i < taglist.size(); i++) { if
			 * (tag.equals(taglist.get(i).getPic_tag()) && m_num ==
			 * taglist.get(i).getM_num()) { phototag.add(taglist.get(i));
			 * System.out.println(taglist); } }
			 */
			model.addAttribute("phototag", phototag);

		} else {
			for (int i = 0; i < taglist.size(); i++) {
				if (m_num == taglist.get(i).getM_num()) {
					phototag.add(taglist.get(i));
				}
			}
			model.addAttribute("phototag", phototag);
		}

		return "photoscloud/tag";
	}

	@RequestMapping("/place")
	private String photoplace() {

		return "photoscloud/place";
	}

	/* 장소별 사진 */
	@RequestMapping("/placeproc")
	private String photoplace(@AuthenticationPrincipal UserDetails userDetails, String place, Model model) {

		EntityMember member = memberJpaRepository.findByUserId(userDetails.getUsername());
		int m_num = member.getM_num();
		List<Picture> placelist = pictrueRepository.findAll();
		List<Picture> photoplace = new ArrayList<Picture>();

		if (place != null) {
			for (int i = 0; i < placelist.size(); i++) {
				if (place.equals(placelist.get(i).getPic_place()) && m_num == placelist.get(i).getM_num()) {
					photoplace.add(placelist.get(i));
				}
			}

			model.addAttribute("photoplace", photoplace);

		} else {
			for (int i = 0; i < placelist.size(); i++) {
				if (m_num == placelist.get(i).getM_num()) {
					photoplace.add(placelist.get(i));
				}
			}
			model.addAttribute("photoplace", photoplace);
		}

		return "photoscloud/place";
	}

	@RequestMapping("/date")
	private String photodate(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		EntityMember user = memberJpaRepository.findByUserId(userDetails.getUsername()); // 지금 로그인 한 정보
		List<Picture> photolist = pictrueRepository.findAll(); // 리스트
		List<Picture> photodate = new ArrayList<Picture>();

		for (int i = 0; i < photolist.size(); i++) {
			if (user.getM_num() == photolist.get(i).getM_num()) {
				photodate.add(photolist.get(i));
			}
		}

		model.addAttribute("photodate", photodate);

		return "photoscloud/date";
	}

	@RequestMapping("/dateproc")
	public String phtodateproc(@AuthenticationPrincipal UserDetails userDetails, String year, String month,
			Model model) {
		EntityMember user = memberJpaRepository.findByUserId(userDetails.getUsername()); // 로그인 정보

		year = year.split("년")[0];
		month = month.split("월")[0];
		int mon = Integer.parseInt(month);
		mon = mon + 1;
		String startDate = year + "-" + month + "-01"; // 선택한 월
		String endDate = year + "-" + mon + "-01"; // 선택한 월+1 -- 한달치검색

		List<Picture> photolist = pictrueRepository.getDate(startDate, endDate);

		List<Picture> photodate = new ArrayList<Picture>();

		for (int i = 0; i < photolist.size(); i++) {
			if (user.getM_num() == photolist.get(i).getM_num()) {
				photodate.add(photolist.get(i));
			}
		}

		model.addAttribute("photodate", photodate);
		model.addAttribute("year", year);
		model.addAttribute("month", month);

		return "photoscloud/date";
	}

}
