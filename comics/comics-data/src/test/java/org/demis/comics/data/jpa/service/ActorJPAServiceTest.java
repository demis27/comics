package org.demis.comics.data.jpa.service;

import org.demis.comics.data.jpa.PersistenceJPAConfiguration;
import org.demis.comics.data.jpa.entity.ActorEntity;
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
public class ActorJPAServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("actorJPAService")
    public ActorJPAService service;

    @Test
    void testCreate() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setCoverName("Tardi");

        service.create(actorEntity);

        List<ActorEntity> actorEntities = service.findAll();
        Assert.assertNotNull(actorEntities);
    }

    @AfterMethod
    public void deleteAll() throws EntityNotFoundException {
        List<ActorEntity> actorEntities = service.findAll();

        for (ActorEntity actorEntity : actorEntities) {
            service.delete(actorEntity.getId());
        }
    }
}
