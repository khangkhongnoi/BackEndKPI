package com.apigateway.configuration;

import com.apigateway.repository.KPIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Configuration
public class WebClienConfiguration {

    @Value("${kpi_cong_viec_url}")
    private String tuyenSinhDaiHocUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://" + tuyenSinhDaiHocUrl + "/kpi-cong-viec")
//                .baseUrl("http://192.168.1.82:8203/tuyen-sinh-dai-hoc")
                .build();
    }

    @Bean
    KPIClient tuyenSinhDaiHocClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();
        return httpServiceProxyFactory.createClient(KPIClient.class);
    }

    @Bean
    CorsWebFilter corsWebFilter() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));


        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }
}
