package com.mindlease.fa.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

/**
 * @author Rama Rao
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan({ "com.mindlease.fa" })
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private MessageSource messageSource;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/*
		 * registry.addResourceHandler("/webjars/**", "/resources/**", "/static/**",
		 * "/images/**", "/assets/**") .addResourceLocations("/webjars/")
		 * .setCacheControl(CacheControl.maxAge(30L,
		 * TimeUnit.DAYS).cachePublic()).resourceChain(true) .addResolver(new
		 * WebJarsResourceResolver()) .addResolver(new GzipResourceResolver())
		 * .addResolver(new PathResourceResolver());
		 */
		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("/webjars/","classpath:/webjars/")
		.setCacheControl(CacheControl.maxAge(30L, TimeUnit.DAYS).cachePublic()).resourceChain(true)
		.addResolver(new WebJarsResourceResolver());
		
		registry.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/","classpath:/resources/")
		.setCacheControl(CacheControl.maxAge(30L, TimeUnit.DAYS).cachePublic()).resourceChain(true)
		.addResolver(new PathResourceResolver());
		
		registry.addResourceHandler("/static/**")
		.addResourceLocations("/static/","classpath:/static/")
		.setCacheControl(CacheControl.maxAge(30L, TimeUnit.DAYS).cachePublic()).resourceChain(true)
		.addResolver(new PathResourceResolver());
		registry.addResourceHandler("/images/**")
		.addResourceLocations("/images/","classpath:/images/")
		.setCacheControl(CacheControl.maxAge(30L, TimeUnit.DAYS).cachePublic()).resourceChain(true)
		.addResolver(new PathResourceResolver());
		registry.addResourceHandler("/assets/**")
		.addResourceLocations("/assets/","classpath:/assets/")
		.setCacheControl(CacheControl.maxAge(30L, TimeUnit.DAYS).cachePublic()).resourceChain(true)
		.addResolver(new PathResourceResolver());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		// registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/admin/home").setViewName("adminhome");
		registry.addViewController("/403").setViewName("403");
	}

	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setValidationMessageSource(messageSource);
		return factory;
	}

	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}
}