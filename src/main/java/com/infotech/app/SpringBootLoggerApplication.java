package com.infotech.app;

import ch.qos.logback.access.ViewStatusMessagesServlet;
import ch.qos.logback.access.tomcat.LogbackValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;


@RestController
@SpringBootApplication
public class SpringBootLoggerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootLoggerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootLoggerApplication.class, args);
		
		LOGGER.error("Message logged at ERROR level");
		LOGGER.warn("Message logged at WARN level");
		LOGGER.info("Message logged at INFO level");
		LOGGER.debug("Message logged at DEBUG level");
	}
	
	@RequestMapping("/")
	public String welcome(){
		String name="kk";

		return "Hello World!!";
	}

    @RequestMapping("/a")
    public ResponseEntity welcome1(){
        String name="kk";

        return ResponseEntity.ok("hi");
    }

    @RequestMapping("/b")
    public ResponseEntity welcome2(){
        String name="kk";

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

		// put logback-access.xml in src/main/resources/conf
		tomcat.addContextValves(new LogbackValve());

		return tomcat;
	}

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new ViewStatusMessagesServlet(),"/lbAccessStatus/*");
    }

    @Bean(name = "TeeFilter")
    public Filter teeFilter() {
        return new ch.qos.logback.access.servlet.TeeFilter();
    }
}
