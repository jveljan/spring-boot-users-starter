package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class WebFlux implements WebFluxConfigurer {

  private final ObjectMapper objectMapper;

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer arg) {
    arg.addCustomResolver(new ReactiveSortHandlerMethodArgumentResolver());
    arg.addCustomResolver(new ReactivePageableHandlerMethodArgumentResolver());
  }

  public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    configurer.defaultCodecs().jackson2JsonEncoder(
        new Jackson2JsonEncoder(objectMapper)
    );

    configurer.defaultCodecs().jackson2JsonDecoder(
        new Jackson2JsonDecoder(objectMapper)
    );
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/swagger-ui.html**")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
