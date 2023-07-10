package market.config;

import market.enums.Role;
import market.http.handler.ControllerExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Set;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration   {

    @Autowired
    private ControllerExceptionHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/registration", "/market",
                                "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/admin/menu/**").hasAnyAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .csrf(CsrfConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                            if (roles.contains(Role.ADMIN.getAuthority())) {
                                response.sendRedirect("/admin/menu");
                            } else {
                                response.sendRedirect("/users");
                            }
                        })
                        .permitAll())

                .logout(logout -> logout
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(config -> config.accessDeniedHandler(accessDeniedHandler));


        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
