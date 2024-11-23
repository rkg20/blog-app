package com.blogapp.config;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blogapp.security.CustomUserDetailService;
import com.blogapp.security.JwtAuthenticationEntryPoint;
import com.blogapp.security.JwtAuthenticationFilter;

import jakarta.annotation.security.PermitAll;
import jakarta.websocket.Session;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                // .antMatchers("/api/v1/auth/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                
            // http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // protected void configure(HttpSecurity http)throws Exception{
    //     http
    //         .csrf()
    //         .disable()
    //         .authorizeHttpRequests()
    //         .anyRequest()
    //         .authenticated()
    //         .and()
    //         .httpBasic();
    // }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user=User
            .username("userEmail")
            .password("userPassword")
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user);
    }


    // protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    //     auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    // }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public AuthenticationManager authenticationManagerBean() throws Exception{
    //     return configuration.getAuthenticationManager();
        
    // }





    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

    //     http
    //         .csrf()
    //         .disable()
    //         .authorizeHttpRequests()
    //         .antMatchers(PUBLIC_URLS)
    //         .permitAll()
    //         .antMatchers(HttpMethod.GET)
    //         .permitAll()
    //         .anyRequests()
    //         .authenticated()
    //         .and().exceptionHandling()
    //         .authenticationEntryPoint(this.jwtAuthenticationPoint)
    //         .and()
    //         .sessionManagement()
    //         .sessionCreationPloicy(SessionCreationPolicy.STATELESS);

    //     DefaultSecurityFilterChain build = http.build();
    //     return build;
    // }


    // @Bean
    // public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception{
    //     return configuration.getAuthenticationManager();
    
    // }

    // public DaoAuthenticationProvider daoAuthenticationProvider(){
        
    //     DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(null);
    //     return provider;
    // }
    

}