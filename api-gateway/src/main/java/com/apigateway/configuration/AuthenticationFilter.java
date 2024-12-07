package com.apigateway.configuration;


import com.apigateway.dto.ApiResponse;
import com.apigateway.service.KPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

    KPIService KPIService;
    ObjectMapper objectMapper;


    // Tạo biến chung cho phần tiền tố
    String BASE_PATH_KPI_CV = "/kpi-cong-viec";

    String[] publicEndpoints = {BASE_PATH_KPI_CV +"/auth/.*", BASE_PATH_KPI_CV+"/quoctich/list",

            BASE_PATH_KPI_CV + "/taikhoan/.*", BASE_PATH_KPI_CV + "/quyen",BASE_PATH_KPI_CV + "/trangthai",

    };


    @Value("${app.api-prefix}")
    @NonFinal
    private String apiPrefixl;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        if(isPublicEndpoint(exchange.getRequest()))
            return chain.filter(exchange);

        // get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return uauthentication(exchange.getResponse());

        String token = authHeader.get(0).replace("Bearer ", "");

        return  KPIService.introspect(token).flatMap(introspectResponseApiResponse -> {
            if (introspectResponseApiResponse.getResult().isValid())
                return chain.filter(exchange);
            else {
                return uauthentication(exchange.getResponse());
            }
        }).onErrorResume(throwable -> uauthentication(exchange.getResponse()));

    }

    @Override
    public int getOrder() {
        return -1;
    }


    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints)
                .anyMatch(s -> request.getURI().getPath().matches(apiPrefixl + s));
    }


    Mono<Void> uauthentication(ServerHttpResponse response){
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Uauthentication")
                .build();
        String body = null;

        try{
            body = objectMapper.writeValueAsString(apiResponse);
        }
        catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }
}
