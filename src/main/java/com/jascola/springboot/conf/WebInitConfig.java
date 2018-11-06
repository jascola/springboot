package com.jascola.springboot.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Registration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

@Configuration
public class WebInitConfig implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext  context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        context.setServletContext(servletContext);
        Dynamic servlet = servletContext.addServlet("dispatcher",new DispatcherServlet(context));
        ((ServletRegistration.Dynamic) servlet).addMapping("/");
        ((ServletRegistration.Dynamic) servlet).setLoadOnStartup(1);
    }
}
