package org.demis.comics.web;

import org.demis.comics.web.exception.HandlerException400Resolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.demis.comics.web",
        "org.demis.comics.search",
        "org.demis.comics.business.service",
        "org.demis.comics.data"})
@PropertySource(value = {"classpath:comics.properties"})
@Component
public class RestConfiguration extends WebMvcConfigurationSupport {

    public RestConfiguration() {
        super();
        List<HandlerExceptionResolver> resolvers = new ArrayList<>();
        resolvers.add(new HandlerException400Resolver());
        configureHandlerExceptionResolvers(resolvers);
    }

    public static final int DEFAULT_PAGE_SIZE = 20;

    @Autowired
    private Environment environment;

    public int getDefaultPageSize() {
        Integer defaultPageSize = environment.getProperty("rest.controller.default.page.size", Integer.class);
        return defaultPageSize != null ? defaultPageSize : DEFAULT_PAGE_SIZE;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

}

