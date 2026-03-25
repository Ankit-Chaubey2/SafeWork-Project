package com.cts.SafeWork.config;

import com.cts.SafeWork.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;


    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Public endpoints

                                .requestMatchers("/employees/login", "/employees/register", "/users/login", "/users/register").permitAll()

                     // 2. Compliance Officer and Admin specific paths
                                .requestMatchers("/programs/**").hasAuthority("ADMIN")
                                .requestMatchers("/trainings/**").hasAuthority("ADMIN")
                                .requestMatchers("/audit/**").hasAnyAuthority("COMPLIANCE_OFFICER", "ADMIN")
                                .requestMatchers("/complianceRecord/**").hasAnyAuthority("COMPLIANCE_OFFICER", "ADMIN")
                                .requestMatchers("/hazard/postHazard/**").hasAnyAuthority("EMPLOYEE","ADMIN")
                                .requestMatchers("/hazard/update/**").hasAnyAuthority("EMPLOYEE","ADMIN")
                                .requestMatchers("/hazard/getAllHazard/**").hasAnyAuthority("HAZARD_OFFICER","ADMIN")
                                .requestMatchers("/hazard/getById/**").hasAnyAuthority("HAZARD_OFFICER","ADMIN")
                                .requestMatchers("/hazard/delete/**").hasAnyAuthority("HAZARD_OFFICER","ADMIN")

                                .requestMatchers("/incident/**").hasAnyAuthority("HAZARD_OFFICER","ADMIN")
                                .requestMatchers("/inspections/**").hasAnyAuthority("SAFETY_OFFICER","ADMIN")
                                .requestMatchers("/compliance-checks/**").hasAnyAuthority("SAFETY_OFFICER","ADMIN")
                                .requestMatchers("/employees/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
                                .requestMatchers("/documents/**").hasAnyAuthority("EMPLOYEE", "ADMIN")




                        // 3. General security
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}