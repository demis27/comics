package org.demis.comics.data.jpa.service;

import org.demis.comics.data.jpa.PersistenceJPAConfiguration;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@ContextHierarchy({
        @ContextConfiguration(classes = PersistenceJPAConfiguration.class)
})
public class ComicBookJPAServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("comicBookJPAService")
    public ComicBookJPAService service;

    @Test
    void testCreate() {
        ComicBookEntity comicBook = new ComicBookEntity();
        comicBook.setTitle("Adèle et la bête");

        service.create(comicBook);

        List<ComicBookEntity> comicBookEntities = service.findAll();
        Assert.assertNotNull(comicBookEntities);
    }

    @AfterMethod
    public void deleteAll() throws EntityNotFoundException {
        List<ComicBookEntity> comicBookEntities = service.findAll();

        for (ComicBookEntity comicBookEntity : comicBookEntities) {
            service.delete(comicBookEntity.getId());
        }
    }
}
