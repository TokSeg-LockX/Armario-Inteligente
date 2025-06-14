package com.tokseg.armariointeligente.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokseg.armariointeligente.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        
        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),
                HttpStatus.UNAUTHORIZED.value(),
                "Não autorizado",
                "Autenticação necessária para acessar este recurso",
                request.getRequestURI()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
