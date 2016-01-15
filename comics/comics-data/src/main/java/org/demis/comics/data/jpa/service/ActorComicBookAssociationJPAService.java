package org.demis.comics.data.jpa.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.SortParameterElement;
import org.demis.comics.data.SortParameterElementConverter;
import org.demis.comics.data.jpa.entity.ActorComicBookAssociationEntity;
import org.demis.comics.data.jpa.repository.ActorComicBookAssociationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service(value ="actorComicBookAssociationJPAService")
public class ActorComicBookAssociationJPAService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorComicBookAssociationJPAService.class);

    @Resource(name = "actorComicBookAssociationRepository")
    private ActorComicBookAssociationRepository repository;

    @Transactional
    public ActorComicBookAssociationEntity create(ActorComicBookAssociationEntity created) {
        return repository.save(created);
    }

    public ActorComicBookAssociationEntity delete(Long id) throws EntityNotFoundException {
        ActorComicBookAssociationEntity deleted = repository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No model found with id: " + id);
            throw new EntityNotFoundException();
        }
        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    public List<ActorComicBookAssociationEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ActorComicBookAssociationEntity findById(Long id) {
        return repository.findOne(id);
    }

    @Transactional
    public ActorComicBookAssociationEntity update(ActorComicBookAssociationEntity updated) throws EntityNotFoundException {
        ActorComicBookAssociationEntity founded = repository.findOne(updated.getId());

        if (founded == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new EntityNotFoundException();
        } else {
            repository.save(updated);
        }
        return founded;
    }

    public List<ActorComicBookAssociationEntity> findPart(Range range, List<SortParameterElement> sorts) {
        return repository.findAll(new PageRequest(range.getPage(), range.getSize(), SortParameterElementConverter.convert(sorts))).getContent();
    }
}
