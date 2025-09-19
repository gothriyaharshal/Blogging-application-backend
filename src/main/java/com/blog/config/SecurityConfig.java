package com.blog.config;

import com.blog.Security.*;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



    import com.blog.Security.JwtAuthenticateFilter;
import com.blog.Security.JwtAuthenticationEntryPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        private static final String[] PUBLIC_URLS = {
                "/api/v1/auth/**",   // login/register endpoints
                 "/v3/api-docs",
                 "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/webjars/**"
                // openapi docs*/
        };

        @Autowired
        private JwtAuthenticationEntryPoints jwtAuthenticationEntryPoint;
        @Autowired
        private JwtAuthenticateFilter jwtAuthenticationFilter;
        @Autowired
        private CustomUserDetails customUserDetails;
        @Autowired
        private UserDetailsService userDetailsService;

        public SecurityConfig(JwtAuthenticationEntryPoints jwtAuthenticationEntryPoint,
                              JwtAuthenticateFilter jwtAuthenticationFilter) {
            this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
            this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }


        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ cors enabled
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(PUBLIC_URLS).permitAll()
                            .requestMatchers(HttpMethod.GET).permitAll()
                            .anyRequest().authenticated()
                    )

                    .exceptionHandling(ex -> ex
                            .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                    )

                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    );

            // JWT filter add karna
            http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            //authentication k lie ye use hoga

            http.authenticationProvider(daoAuthenticationProvider());

            DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

            return defaultSecurityFilterChain;


        }

        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider()
        {
            DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
            daoAuthenticationProvider.setUserDetailsService(this.customUserDetails);
            daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
            return daoAuthenticationProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }


    // ✅ Centralized CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*")); // React/Angular sab allowed
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }


}
