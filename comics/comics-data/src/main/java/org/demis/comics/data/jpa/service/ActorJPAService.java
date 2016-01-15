package org.demis.comics.data.jpa.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.SortParameterElement;
import org.demis.comics.data.SortParameterElementConverter;
import org.demis.comics.data.jpa.entity.ActorEntity;
import org.demis.comics.data.jpa.repository.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service(value ="actorJPAService")
public class ActorJPAService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorJPAService.class);

    @Resource(name = "actorRepository")
    private ActorRepository actorRepository;

    @Transactional
    public ActorEntity create(ActorEntity created) {
        return actorRepository.save(created);
    }

    public ActorEntity delete(Long id) throws EntityNotFoundException {
        ActorEntity deleted = actorRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No model found with id: " + id);
            throw new EntityNotFoundException();
        }
        actorRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    public List<ActorEntity> findAll() {
        return actorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ActorEntity findById(Long id) {
        return actorRepository.findOne(id);
    }

    @Transactional
    public ActorEntity update(ActorEntity updated) throws EntityNotFoundException {
        ActorEntity founded = actorRepository.findOne(updated.getId());

        if (founded == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new EntityNotFoundException();
        } else {
            actorRepository.save(updated);
        }
        return founded;
    }

    public List<ActorEntity> findPart(Range range, List<SortParameterElement> sorts) {
        return actorRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortParameterElementConverter.convert(sorts))).getContent();
    }
}
