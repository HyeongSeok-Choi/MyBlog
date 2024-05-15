package com.myblog.myblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyOAuth2UserService myOAuth2UserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/main")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .failureUrl("/login/error").permitAll())

                .logout((logoutCoinfig) ->
                        logoutCoinfig
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/main"))


                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/js/**", "/Img/**").permitAll()
                                .requestMatchers("/main", "/join","/join", "/item/**", "/images/**","/login","/auth/kakao/callback","/oauth2/authorization/kakao"
                                ,"/guestBook","/portpolioList","/image-print/**","/portpolio","/guestbookList","/tui-editor/image-print").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()

                )
                .oauth2Login(oauth2 -> oauth2

                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/auth/kakao/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(myOAuth2UserService))
                        .defaultSuccessUrl("/main"))

                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))

        ;

        return http.build();

    }

    //암호화 기능
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}