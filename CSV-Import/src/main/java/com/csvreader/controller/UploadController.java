package com.csvreader.controller;

import java.io.File;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.csvreader.dao.UserDAO;
import com.csvreader.dto.User;

@Controller
public class UploadController {
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("csvfile") MultipartFile file) {
		try {
			String destpath = "D:\\TrialEclipseSpace\\CSV-Import\\src\\main\\webapp\\csvs\\";
			File serverfile = new File(destpath,file.getOriginalFilename());
			file.transferTo(serverfile);
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
			UserDAO ud = (UserDAO) context.getBean("udao");
			
			List<User> ul = ud.insertfile(serverfile);
			
			if(ud.insertUser(ul)) return "success";
			else return "error";
			//m.addAttribute("FILENAME",file.getOriginalFilename());
			
		} catch (Exception e) {
			e.printStackTrace();
			//m.addAttribute("Exception",e);
			return "error";
		}
		
	}
}
