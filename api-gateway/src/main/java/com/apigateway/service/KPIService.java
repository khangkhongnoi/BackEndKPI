package com.apigateway.service;

import com.apigateway.dto.ApiResponse;
import com.apigateway.dto.request.IntrospectRequest;
import com.apigateway.dto.response.IntrospectResponse;
import com.apigateway.repository.KPIClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KPIService {

    KPIClient KPIClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return KPIClient.introspect(IntrospectRequest.builder()
                        .token(token)
                .build());
    }
}
