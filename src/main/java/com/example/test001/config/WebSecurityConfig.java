package com.example.test001.config;

import com.example.test001.Service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean//注入PasswordEncoder
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login","/insert","/insertMessage").permitAll()// 这些页面不需要身份认证,其他请求需要认证
                .antMatchers("/DeliAll","/deli/**").hasRole("DELI")
                .antMatchers("/ROOTAll","/root/**").hasRole("ROOT")
                .antMatchers("/USERAll","/user/**").hasRole("USER")
                .and().csrf().disable();
        http.formLogin()
                .loginPage("/login")// 自定义登录页面
                .defaultSuccessUrl("/All")//登录成功
                .failureForwardUrl("/loginFail");//登录失败
    }
}
