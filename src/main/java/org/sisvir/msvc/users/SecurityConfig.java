package org.sisvir.msvc.users;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
//@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
////                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize ->
//                        authorize
//                                .requestMatchers("/authorized").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/getAll", "/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
//                                .requestMatchers(HttpMethod.POST, "/create").hasAuthority("SCOPE_write")
//                                .requestMatchers(HttpMethod.PUT, "/update/{id}").hasAuthority("SCOPE_write")
//                                .requestMatchers(HttpMethod.DELETE, "/delete/{id}").hasAuthority("SCOPE_write")
//                                .anyRequest().authenticated()
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2Login(oauth2Login ->
//                        oauth2Login.loginPage("/oauth2/authorization/msvc-users-client"))
//                .oauth2Client(withDefaults())
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
////                .oauth2ResourceServer().jwt();
////                .logout(logout ->
////                        logout.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)));
//        return http.build();
//    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/authorized", "/users/login", "/users/by-username/{username}").permitAll()
                .antMatchers(HttpMethod.GET, "/users/getAll", "/users/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
                .antMatchers(HttpMethod.POST, "/users/create").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.PUT, "/users/update/{id}").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.DELETE, "/users/delete/{id}").hasAuthority("SCOPE_write")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/users/oauth2/authorization/msvc-users-client"))
                .oauth2Client(withDefaults())
                .oauth2ResourceServer().jwt();

        return http.build();
    }
}
