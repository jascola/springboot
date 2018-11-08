package com.jascola.springboot;

import com.jascola.demo.DataService;
import com.jascola.springboot.elasticsearch.EmployeeRepository;
import com.jascola.jms.Msg;
import com.jascola.kafka.KafkaSender;
import com.jascola.model.dao.UserDao;
import com.jascola.model.entity.Employee;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@SpringBootApplication
@Controller
public class SpringbootApplication {
    private Logger logger = LoggerFactory.getLogger(SpringApplication.class);

    @Autowired
    JmsTemplate jmsTemplate;

    ActiveMQTopic activeMQTopic;
    @Resource
    public void setActiveMQTopic(ActiveMQTopic activeMQTopic) {
        this.activeMQTopic = activeMQTopic;
    }

    ActiveMQQueue activeMQQueue;
    @Resource
    public void setActiveMQQueue(ActiveMQQueue activeMQQueue) {
        this.activeMQQueue = activeMQQueue;
    }

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importJob;

    @Autowired
    UserDao dao;

    //DataService自动装配，不需要配置bean
    DataService service;

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    EmployeeRepository er;

    @Resource
    public void setService(DataService service) {
        this.service = service;
    }

    @RequestMapping(value = "/admin")
    public String getAdmin(Model model) {
        System.out.println(service.sayHello());
        model.addAttribute("hah", dao.selectAll());
        return "index";
    }

    @RequestMapping(value = "/ex")
    @ResponseBody
    public String ex() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importJob, jobParameters);
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return "hah";
    }

    @RequestMapping(value = "/jms")
    @ResponseBody
    public String jms() {
        try {
            jmsTemplate.send(activeMQQueue,new Msg());
            jmsTemplate.send(activeMQTopic,new Msg());
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return "hah";
    }
    @RequestMapping(value = "/kafka")
    @ResponseBody
    public String kafka() {
        try {
            kafkaSender.sendTest();
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return "hah";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(){

        Employee employee=new Employee();
        employee.setId("1");
        employee.setFirstName("xuxu");
        employee.setLastName("zh");
        employee.setAge(26);
        employee.setAbout("i am in peking");
        er.save(employee);

        System.err.println("add a obj");

        return "success";
    }
    //删除
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(){

        er.deleteById("1");

        return "success";
    }

    //局部更新
    @RequestMapping("/update")
    @ResponseBody
    public String update(){

        Employee employee=er.queryEmployeeById("1");
        employee.setFirstName("哈哈");
        er.save(employee);

        System.err.println("update a obj");

        return "success";
    }

    //查询
    @RequestMapping("/query")
    @ResponseBody
    public Employee query(){

        Employee accountInfo=er.queryEmployeeById("1");
        return accountInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
