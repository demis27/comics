package org.demis.comics.search.service;

import org.demis.comics.data.jpa.entity.ComicBookEntity;
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
public class ComicBookSearchServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("comicBookSearchService")
    private ComicBookSearchService service;

    @Test
    public void create() {
        ComicBookEntity entity = new ComicBookEntity();
        entity.setId(1L);
        entity.setTitle("Batman");
        service.create(entity);
    }

    @AfterMethod
    public void deleteAll() {
        service.delete(1L);
    }

}
