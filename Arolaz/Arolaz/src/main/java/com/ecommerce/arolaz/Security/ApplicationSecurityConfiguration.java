package com.ecommerce.arolaz.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
//@ComponentScan("com.pizza")
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private ShopCustomerDetailsService shopCustomerDetailsService;

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        //entry point
        http
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/register").permitAll()
                .antMatchers("/api/product/**").permitAll()
                .antMatchers("/api/category/**").permitAll()
                .antMatchers("/api/size").permitAll()
                //disallow anything else
                .anyRequest().authenticated();
        http.csrf().disable();
        http.cors();
        //no session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtTokenFilter(shopCustomerDetailsService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

}
