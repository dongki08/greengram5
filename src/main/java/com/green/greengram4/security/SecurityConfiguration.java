package com.green.greengram4.security;

import com.green.greengram4.oauth2.CustomeOAuth2UserService;
import com.green.greengram4.oauth2.OAuth2AuthenticationFailureHandler;
import com.green.greengram4.oauth2.OAuth2AuthenticationRequestBasedOnCookieRepository;
import com.green.greengram4.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Security;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationRequestBasedOnCookieRepository oAuth2AuthenticationRequestBasedOnCookieRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomeOAuth2UserService customeOAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable())
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                          "/api/feed"
                        , "/api/feed/comment"
                        , "/api/dm"
                        , "/api/dm/msg"
                        ).authenticated() // 위 주소로 들어오는건 다 로그인되어야한다
                        .requestMatchers(HttpMethod.POST, "/api/user/signout"
                                                        , "/api/user/follow"
                        ).authenticated() // post를 제외한 위 주소로 들어오면 허용
                        .requestMatchers(HttpMethod.GET, "/api/user").authenticated() //이 get 주소로 들어오는 건 로그인 처리가 되어야한다
                        .requestMatchers(HttpMethod.PATCH, "/api/user/pic").authenticated() // 이 patch 주소로 들어오는 것도 로그인 처리가 되어야한다.
                        .requestMatchers(HttpMethod.GET, "/api/feed/fav").hasAnyRole("USER")
                        .anyRequest().permitAll() //
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(execpt -> {
                    execpt.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .oauth2Login(oauth2 -> oauth2.authorizationEndpoint(auth ->
                        auth.baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(oAuth2AuthenticationRequestBasedOnCookieRepository))
                .redirectionEndpoint(redirecttion -> redirecttion.baseUri("/*/oauth2/code/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customeOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
