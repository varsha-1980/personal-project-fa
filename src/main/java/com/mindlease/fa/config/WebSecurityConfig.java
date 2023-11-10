package com.mindlease.fa.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private UserDetailsService customUserDetailsService;
 
    @Autowired
    private DataSource dataSource;

    @Autowired
    LocaleSettingAuthenticationSuccessHandler localeSettingAuthenticationSuccessHandler;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth
         .userDetailsService(customUserDetailsService)
         .passwordEncoder(passwordEncoder());
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	// http.headers().frameOptions().sameOrigin().and()
    	http.cors().and().csrf().disable();
        http.headers().disable()
            .authorizeRequests()
            
            .antMatchers("/api/**").permitAll()
            .antMatchers( "/login/**").permitAll()
            .antMatchers( "/assets/**").permitAll()
            .antMatchers( "/static/**").permitAll()
            .antMatchers( "/images/**").permitAll()
            .antMatchers( "/webjars/**").permitAll()
            .antMatchers("/resources/**").permitAll()
            .antMatchers("/resources/**", "/webjars/**","/assets/**").permitAll()
                .antMatchers("/").permitAll()
                //.antMatchers("/registration/**").hasAnyAuthority("SUPER-ADMIN")
//                .antMatchers("/h2-console").hasAnyAuthority("SUPER-ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")                
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(localeSettingAuthenticationSuccessHandler).failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .deleteCookies("my-remember-me-cookie")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403").and()
                .rememberMe()
                //.key("my-secure-key")
                .rememberMeCookieName("my-remember-me-cookie")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(24 * 60 * 60)
                .and()
                .exceptionHandling();
            
    }
	
    PersistentTokenRepository persistentTokenRepository(){
     JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
     tokenRepositoryImpl.setDataSource(dataSource);
     return tokenRepositoryImpl;
    }
}