package org.demis.comics.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.demis.comics.web",
        "org.demis.comics.business.service",
        "org.demis.comics.data"})
@PropertySource(value = {"classpath:comics.properties"})
@Component
public class RestConfiguration extends WebMvcConfigurerAdapter {

    public static final int DEFAULT_PAGE_SIZE = 20;

    @Autowired
    private Environment environment;

    public int getDefaultPageSize() {
        Integer defaultPageSize = environment.getProperty("rest.controller.default.page.size", Integer.class);
        return defaultPageSize != null ? defaultPageSize : DEFAULT_PAGE_SIZE;
    }
}

