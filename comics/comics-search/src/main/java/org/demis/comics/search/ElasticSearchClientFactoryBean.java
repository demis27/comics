package org.demis.comics.search;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.Inet4Address;

@Component
@PropertySource(value = {"classpath:comics.properties"})
@ComponentScan(basePackages = {"org.demis.comics.search.service","org.demis.comics.data"})
public class ElasticSearchClientFactoryBean extends AbstractFactoryBean<Client> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchClientFactoryBean.class);

    private Client client;

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @Override
    protected Client createInstance() throws Exception {
        if (client == null) {
            Settings.Builder settings = Settings.builder().put("cluster.name", environment.getProperty("elasticsearch.cluster.name"));

            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(Inet4Address.getByName(environment.getProperty("elasticsearch.address.ip")), environment.getProperty("elasticsearch.address.port", Integer.class)));
        }

        return client;
    }

    @PreDestroy
    public void shutdown() {
        LOGGER.info("Shutting down service TransportClient ...");
        if (client != null) {
            client.close();
        }
        LOGGER.info("Elasticsearch TransportClient shutdown.");
    }

    @PostConstruct
    public void createMapping() throws Exception {
        createInstance();
        LOGGER.info("Create Index and Mappings...");

        IndicesExistsResponse res = client.admin().indices().prepareExists(environment.getProperty("elasticsearch.index.name")).execute().actionGet();
        if (!res.isExists()) {
            LOGGER.info("Index comics don't exist, we will create it");

            Settings.Builder settings = Settings.builder().put("number_of_replicas", environment.getProperty("elasticsearch.index.replicas.number"));

            CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(environment.getProperty("elasticsearch.index.name")).setSettings(settings);
            createIndexRequestBuilder.execute().actionGet();

            LOGGER.info("Index comics don't exist, created");
        }
        // TODO verify and add mapping



        LOGGER.info("Index and Mappings created");
    }
}
