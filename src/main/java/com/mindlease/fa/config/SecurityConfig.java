package com.mindlease.fa.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Host;
import org.apache.catalina.core.StandardContext;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class SecurityConfig {

/*  @Bean
	 
	    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {
	        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver);
	        templateEngine.addDialect(sec); // Enable use of "sec"
	        return templateEngine;
	    }
    
  */
	@Profile(value = { "dev", "prod"})
	@Bean
	public TomcatServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory() {

			@Override
			protected void prepareContext(Host host, ServletContextInitializer[] initializers) {
				super.prepareContext(host, initializers);
				StandardContext child = new StandardContext();
				child.addLifecycleListener(new org.apache.catalina.startup.Tomcat.FixContextListener());
				child.setPath("");
				ServletContainerInitializer initializer = getServletContextInitializer(getContextPath());
				child.addServletContainerInitializer(initializer, Collections.emptySet());
				child.setCrossContext(true);
				host.addChild(child);
			}
		};
	}

	private ServletContainerInitializer getServletContextInitializer(String contextPath) {
		return (c, context) -> {
			Servlet servlet = new HttpServlet() {
				@Override
				protected void doGet(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
					resp.sendRedirect(contextPath);
				}
			};
			context.addServlet("root", servlet).addMapping("/*");
		};
	}

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}
