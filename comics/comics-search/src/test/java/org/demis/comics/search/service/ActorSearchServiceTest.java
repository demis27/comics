package org.demis.comics.search.service;

import org.demis.comics.data.jpa.entity.ActorEntity;
import org.demis.comics.search.ElasticSearchClientFactoryBean;
import org.demis.comics.search.SearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@ContextHierarchy({
        @ContextConfiguration(classes = SearchConfig.class),
        @ContextConfiguration(classes = ElasticSearchClientFactoryBean.class)
})
public class ActorSearchServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("actorSearchService")
    private ActorSearchService service;

    @Test
    public void create() {
        ActorEntity entity = new ActorEntity();
        entity.setId(1L);
        entity.setCoverName("RG");
        service.create(entity);
    }

    @AfterMethod
    public void deleteAll() {
        service.delete(1L);
    }

}
