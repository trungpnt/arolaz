package com.ecommerce.arolaz.Utils.Security;

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
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private ShopCustomerDetailsService shopCustomerDetailsService;

//    @Autowired
//    private CustomOAuth2UserService oAuth2UserService;
//
//    @Autowired
//    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/user/signup").permitAll()
                .antMatchers("/api/user/signin").permitAll()
                .antMatchers("/api/products/criteria/v1").permitAll()
                .antMatchers("/api/products").permitAll()
                .antMatchers("/api/category/**").permitAll()
                .antMatchers("/api/categories").permitAll()
                .antMatchers("/api/size").permitAll()
                .antMatchers("/api/product-size").permitAll()
                .antMatchers("/api/order/unregistered").permitAll()
                .antMatchers("/api/inventory/**").permitAll()
                .antMatchers("/api/color").permitAll()
                .antMatchers("/api/brand").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/browser/index.html**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/actuator").permitAll()
                .antMatchers("http://localhost:8080/swagger-ui/favicon-32x32.png?v=3.0.0").permitAll()

                .anyRequest().authenticated();
//                    .and()
//                    .oauth2Login()
//                    .loginPage("/login")
//                    .userInfoEndpoint().userService(oAuth2UserService)
//                    .and()
//                    .successHandler(oAuth2LoginSuccessHandler);

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

//    public CustomOAuth2UserService getoAuth2UserService() {
//        return oAuth2UserService;
//    }
//
//    public void setoAuth2UserService(CustomOAuth2UserService oAuth2UserService) {
//        this.oAuth2UserService = oAuth2UserService;
//    }
}
