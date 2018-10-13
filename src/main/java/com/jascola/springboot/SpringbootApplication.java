package com.jascola.springboot;

import com.jascola.model.dao.UserDao;
import com.jascola.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@Controller
public class SpringbootApplication  {
	@Autowired
	UserDao dao;
	@RequestMapping(value = "/")
	public String fff(Model model){
		model.addAttribute("hah",dao.selectAll());
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
