package com.jascola.springboot;

import com.jascola.demo.DataService;
import com.jascola.model.dao.UserDao;
import com.jascola.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
@Controller
public class SpringbootApplication  {
	Logger logger = LoggerFactory.getLogger(SpringApplication.class);
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job importJob;
	static int i=0;
	@Autowired
	UserDao dao;
	//DataService自动装配，不需要配置bean
	DataService service;
	@Resource
	public void setService(DataService service)	{
		 this.service = service;
	}

	@RequestMapping(value = "/admin")
	public String getAdmin(Model model){
		System.out.println(service.sayHello());
		model.addAttribute("hah",dao.selectAll());
		return "index";
	}
	@RequestMapping(value = "/login")
	public String login(Model model){
		i++;
		System.out.println("login"+i);
		return "check/login";
	}
	@RequestMapping(value = "/ex")
	@ResponseBody
	public String  ex(){
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
			jobLauncher.run(importJob, jobParameters);
		}catch (Exception e){
			logger.info(e.getLocalizedMessage(),e);
		}
		return "hah";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
