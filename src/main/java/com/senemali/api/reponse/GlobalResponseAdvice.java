package com.senemali.api.reponse;

import com.senemali.api.exception.ErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body == null || body instanceof ApiResponse<?>) {
            return body;
        }

        int status = response.getStatusCode() == null ? 200 : response.getStatusCode().value();
        boolean success = status < 400;
        String message = success ? "Requête traitée avec succès" : getErrorMessage(body);

        return new ApiResponse<>(success, status, message, body, LocalDateTime.now());
    }

    private String getErrorMessage(Object body) {
        if (body instanceof ErrorResponse errorResponse) {
            return errorResponse.getMessage();
        }
        if (body instanceof Map<?, ?> bodyMap) {
            Object message = bodyMap.get("message");
            if (message == null) {
                message = bodyMap.get("error");
            }
            if (message != null) {
                return message.toString();
            }
        }
        return "Une erreur est survenue";
    }
}