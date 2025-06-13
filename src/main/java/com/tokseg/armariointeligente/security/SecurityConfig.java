package com.tokseg.armariointeligente.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
// CorsConfigurationSource e UrlBasedCorsConfigurationSource não são diretamente usados nesta abordagem de lambda, mas Arrays é.
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections; // Adicionar para allowedOrigins se usar Collections.singletonList

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> { // <<< CONFIGURAÇÃO DE CORS ADICIONADA AQUI
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("*")); // Permite todas as origens para teste. Em produção, especifique suas origens.
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("*")); // Permite todos os cabeçalhos.
                    // configuration.setAllowCredentials(true); // Defina como true se você precisar de cookies/autenticação básica via CORS (e não use allowedOrigins("*"))
                    return configuration;
                }))
                .csrf(csrf -> csrf.disable()) // Desabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Endpoints de usuários - apenas ADMIN pode acessar
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")

                        // Novos Endpoints de Entrega (Exemplos - ajuste conforme suas necessidades)
                        .requestMatchers(HttpMethod.POST, "/api/entregas/solicitar-deposito").hasRole("ENTREGADOR")
                        .requestMatchers(HttpMethod.POST, "/api/entregas/*/confirmar-deposito").hasRole("ENTREGADOR")
                        .requestMatchers(HttpMethod.POST, "/api/entregas/*/regenerar-pin").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/entregas/usuario/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/entregas/*").authenticated()

                        // Novos Endpoints de Compartimento (Exemplos - ajuste conforme suas necessidades)
                        .requestMatchers(HttpMethod.POST, "/api/compartimentos/abrir-com-pin").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/compartimentos/armario/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/compartimentos/*/status").hasRole("ADMIN")

                        // Demais endpoints requerem autenticação
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}