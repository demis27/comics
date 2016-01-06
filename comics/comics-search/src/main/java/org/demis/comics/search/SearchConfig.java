package org.demis.comics.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = {"classpath:comics.properties"})
public class SearchConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchConfig.class);

    @Autowired
    private Environment environment;

    public String getIndexName() {
        return environment.getProperty("elasticsearch.index.name");
    }

}
