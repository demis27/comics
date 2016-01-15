package org.demis.comics.business.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.SortParameterElement;
import org.demis.comics.data.jpa.entity.ActorEntity;
import org.demis.comics.data.jpa.service.ActorJPAService;
import org.demis.comics.search.service.ActorSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service(value = "actorBusinessService")
public class ActorBusinessService {
    
    @Autowired
    @Qualifier("actorJPAService")
    private ActorJPAService actorJPAService;

    @Autowired
    @Qualifier("actorSearchService")
    private ActorSearchService actorSearchService;

    @Transactional
    public ActorEntity create(ActorEntity created) {
        ActorEntity entity = actorJPAService.create(created);
        actorSearchService.create(entity);
        return entity;
    }

    @Transactional
    public ActorEntity delete(Long id) throws EntityNotFoundException {
        actorSearchService.delete(id);
        return actorJPAService.delete(id);
    }

    @Transactional
    public List<ActorEntity> findAll() {
        return actorJPAService.findAll();
    }

    @Transactional
    public ActorEntity findById(Long id) {
        return actorJPAService.findById(id);
    }

    @Transactional
    public ActorEntity update(ActorEntity updated) throws EntityNotFoundException {
        ActorEntity entity = actorJPAService.update(updated);
        actorSearchService.update(entity);
        return entity;
    }

    public List<ActorEntity> findPart(Range range, List<SortParameterElement> sorts) {
        return actorJPAService.findPart(range, sorts);
    }
}
