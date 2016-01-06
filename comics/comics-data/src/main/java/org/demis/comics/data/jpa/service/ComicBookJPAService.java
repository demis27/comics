package org.demis.comics.data.jpa.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.Sort;
import org.demis.comics.data.SortConverter;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.data.jpa.repository.ComicBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service(value ="comicBookJPAService")
public class ComicBookJPAService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComicBookJPAService.class);

    @Resource(name = "comicBookRepository")
    private ComicBookRepository comicBookRepository;

    @Transactional
    public ComicBookEntity create(ComicBookEntity created) {
        return comicBookRepository.save(created);
    }

    public ComicBookEntity delete(Long id) throws EntityNotFoundException {
        ComicBookEntity deleted = comicBookRepository.findOne(id);

        if (deleted == null) {
            LOGGER.debug("No model found with id: " + id);
            throw new EntityNotFoundException();
        }
        comicBookRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    public List<ComicBookEntity> findAll() {
        return comicBookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ComicBookEntity findById(Long id) {
        return comicBookRepository.findOne(id);
    }

    @Transactional
    public ComicBookEntity update(ComicBookEntity updated) throws EntityNotFoundException {
        ComicBookEntity founded = comicBookRepository.findOne(updated.getId());

        if (founded == null) {
            LOGGER.debug("No model found with id: " + updated.getId());
            throw new EntityNotFoundException();
        } else {
            comicBookRepository.save(updated);
        }
        return founded;
    }

    public List<ComicBookEntity> findPart(Range range, List<Sort> sorts) {
        return comicBookRepository.findAll(new PageRequest(range.getPage(), range.getSize(), SortConverter.convert(sorts))).getContent();
    }
}
