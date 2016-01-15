package org.demis.comics.data.jpa.service;

import org.demis.comics.data.jpa.PersistenceJPAConfiguration;
import org.demis.comics.data.jpa.entity.ActorComicBookAssociationEntity;
import org.demis.comics.data.jpa.entity.ActorEntity;
import org.demis.comics.data.jpa.entity.ActorRole;
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
public class ActorComicBookAssociationEntityTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("actorJPAService")
    public ActorJPAService actorJPAService;

    @Autowired
    @Qualifier("comicBookJPAService")
    public ComicBookJPAService comicBookJPAService;

    @Autowired
    @Qualifier("actorComicBookAssociationJPAService")
    public ActorComicBookAssociationJPAService actorComicBookAssociationJPAService;

    @Test
    void testCreate() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setCoverName("Tardi");
        actorEntity = actorJPAService.create(actorEntity);

        ComicBookEntity comicBook = new ComicBookEntity();
        comicBook.setTitle("Adèle et la bête");
        comicBook = comicBookJPAService.create(comicBook);

        ActorComicBookAssociationEntity associationEntity = new ActorComicBookAssociationEntity();
        associationEntity.setComicBook(comicBook);
        associationEntity.setActor(actorEntity);
        associationEntity.setRole(ActorRole.W);

        actorComicBookAssociationJPAService.create(associationEntity);

        List<ActorEntity> actorEntities = actorJPAService.findAll();
        Assert.assertNotNull(actorEntities);

        List<ComicBookEntity> comicBookEntities = comicBookJPAService.findAll();
        Assert.assertNotNull(comicBookEntities);
    }

    @AfterMethod
    public void deleteAll() throws EntityNotFoundException {
        List<ActorComicBookAssociationEntity> actorComicBookAssociationEntities = actorComicBookAssociationJPAService.findAll();

        for (ActorComicBookAssociationEntity actorComicBookAssociationEntity : actorComicBookAssociationEntities) {
            actorComicBookAssociationJPAService.delete(actorComicBookAssociationEntity.getId());
        }

        List<ComicBookEntity> comicBookEntities = comicBookJPAService.findAll();

        for (ComicBookEntity comicBookEntity : comicBookEntities) {
            comicBookJPAService.delete(comicBookEntity.getId());
        }

        List<ActorEntity> actorEntities = actorJPAService.findAll();

        for (ActorEntity actorEntity : actorEntities) {
            actorJPAService.delete(actorEntity.getId());
        }
    }

}
