package com.example.fooddelivery.config;

import com.example.fooddelivery.config.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/auth").permitAll()
                .antMatchers("/getU/").permitAll()
                .antMatchers("/admin/get").hasRole("ADMIN")
                .antMatchers("/user/get").hasRole("USER")
                .antMatchers("/del/get").hasRole("DELIVERY")
                .antMatchers("/favourites/*/*").permitAll()
                .antMatchers("/restman/get").hasRole("RESTMAN")
                .antMatchers("/restaurant").hasRole("ADMIN")
                .antMatchers("/restaurant/editAddress/*/*").hasRole("RESTMAN")
                .antMatchers("/restaurant/editName/*/*").hasRole("RESTMAN")
                .antMatchers("/user/editPass/*").permitAll()
                .antMatchers("/user/editLoc/*").permitAll()
                .antMatchers("/menu/add").hasRole("ADMIN")
                .antMatchers("/menu/editPrice/*/*").hasRole("ADMIN")
                .antMatchers("/menu/editProductList/*").hasRole("ADMIN")
                .antMatchers("/menu/deleteMenu").hasRole("ADMIN")
                .antMatchers("/menu/getMenus/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/product").hasRole("ADMIN")
                .antMatchers("/product/*").hasRole("ADMIN")
                .antMatchers("/delivery/*").hasRole("ADMIN")
                .antMatchers("/delivery/*/*/*").hasRole("ADMIN")
                .antMatchers("/delivery/*/*/*/*").hasRole("ADMIN")
                .antMatchers("/order/edit/*").hasRole("DELIVERY")
                .antMatchers("/order/getAll").hasRole("ADMIN")
                .antMatchers("/order/getOne/*").hasRole("USER")
                .antMatchers("/order/money/*").hasRole("ADMIN")
                .antMatchers("/order/add/").hasRole("USER")
                .antMatchers("/order/forDelivery/").hasRole("DELIVERY")
                .antMatchers("/order/allUser/").hasRole("USER")
                .antMatchers("/order/allUserCurrent/").hasRole("USER")
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
