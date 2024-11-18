package com.example.apigateway.service;

import com.example.apigateway.dto.ApiResponse;
import com.example.apigateway.dto.request.IntrospectRequest;
import com.example.apigateway.dto.response.IntrospectResponse;
import com.example.apigateway.repository.KpiClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KpiService {

    KpiClient kpiClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {

        return kpiClient.introspect(IntrospectRequest.builder()
                        .token(token)
                .build());
    }
}
