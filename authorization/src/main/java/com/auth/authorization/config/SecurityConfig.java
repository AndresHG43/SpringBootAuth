package com.auth.authorization.config;

import com.auth.authorization.service.contract.ITokenService;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity //@EnableWebSecurity(debug = true): Enable debug mode for development
@EnableMethodSecurity //Allows use @PreAuthorize, @PostAuthorize in methods
public class SecurityConfig {
    private final ITokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.cors}")
    String[] arrayCors;

    public SecurityConfig(ITokenService tokenService, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public final static String REGISTER_URL_MATCHER = ApiConfig.API_BASE_PATH + "/auth";
    public final static String LOGIN_URL_MATCHER = ApiConfig.API_BASE_PATH + "/auth/login";
    public final static String LOGOUT_URL_MATCHER = ApiConfig.API_BASE_PATH + "/auth/logout";
    public final static String REFRESH_TOKEN_URL_MATCHER = ApiConfig.API_BASE_PATH + "/auth/refresh";
    public final static String GENERATE_PASSWORD_URL_MATCHER = ApiConfig.API_BASE_PATH + "/auth/generate-password";
    final String BASE_URL_MATCHER = ApiConfig.API_BASE_PATH + "/**";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            final Filter jwtFilter = jwtAuthenticationFilter();
            http
                    .formLogin(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .authorizeHttpRequests((requests) -> requests
                                    .requestMatchers(HttpMethod.POST, LOGIN_URL_MATCHER).permitAll()
                                    .requestMatchers(HttpMethod.POST, REGISTER_URL_MATCHER).permitAll()
                                    .requestMatchers(HttpMethod.POST, REFRESH_TOKEN_URL_MATCHER).permitAll()
                                    .requestMatchers(HttpMethod.POST, GENERATE_PASSWORD_URL_MATCHER).permitAll()
                                    .requestMatchers(BASE_URL_MATCHER).authenticated()
                            //.anyRequest().denyAll()
                    )
                    .logout(logout -> {
                        logout
                                .logoutRequestMatcher(request ->
                                        request.getMethod().equalsIgnoreCase("POST") &&
                                                request.getServletPath().equals(LOGOUT_URL_MATCHER)
                                )
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    SecurityContextHolder.clearContext();
                                    response.setStatus(HttpStatus.NO_CONTENT.value());
                                })
                        ;
                    })
                    .addFilterBefore(jwtFilter, LogoutFilter.class) //Custom filter before LogoutFilter
                    .csrf((csrf) -> {
                        try {
                            csrf.disable()
                                    .sessionManagement((sessionManagement) -> sessionManagement
                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Session STATELESS: Stateless API REST
                                    );
                        } catch (Exception e) {
                            throw new AuthenticationException("Spring Security Config Issue", e) {
                            };
                        }
                    })
                    .authenticationManager(authenticationManager())
                    .exceptionHandling(handler -> handler
                            .authenticationEntryPoint((request, response, authException) -> {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            })
                    )
            ;
            return http.build();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(true);

        return providerManager;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Allowed headers
        corsConfiguration.setAllowedHeaders(
                List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With", "X-Frame-Options"));

        // Allowed origins from properties
        corsConfiguration.setAllowedOrigins(List.of(arrayCors));

        // Header exposed for downloads
        corsConfiguration.addExposedHeader("Content-Disposition");

        // Debug: Print configured sources
        List<String> originsList = List.of(arrayCors);
        for (String origin : originsList) {
            System.out.println(origin + " - CORS origin allowed");
        }

        // Allowed HTTP Methods
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        // Apply settings to all routes
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenService, userDetailsService);
    }
}
