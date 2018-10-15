package com.jascola.springboot;

import com.jascola.demo.DataService;
import com.jascola.model.dao.UserDao;
import com.jascola.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
@Controller
public class SpringbootApplication  {
	@Autowired
	UserDao dao;
	//DataService自动装配，不需要配置bean
	DataService service;
	@Resource
	public void setService(DataService service)	{
		 this.service = service;
	}

	@RequestMapping(value = "/")
	public String fff(Model model){
		System.out.println(service.sayHello());
		model.addAttribute("hah",dao.selectAll());
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
