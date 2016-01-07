package org.demis.comics.business.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.Sort;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.data.jpa.service.ComicBookJPAService;
import org.demis.comics.search.service.ComicBookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service(value = "comicBookBusinessService")
public class ComicBookBusinessService {

    @Autowired
    @Qualifier("comicBookJPAService")
    private ComicBookJPAService comicBookJPAService;

    @Autowired
    @Qualifier("comicBookSearchService")
    private ComicBookSearchService comicBookSearchService;

    @Transactional
    public ComicBookEntity create(ComicBookEntity created) {
        ComicBookEntity entity = comicBookJPAService.create(created);
        comicBookSearchService.create(entity);
        return entity;
    }

    @Transactional
    public ComicBookEntity delete(Long id) throws EntityNotFoundException {
        comicBookSearchService.delete(id);
        return comicBookJPAService.delete(id);
    }

    @Transactional
    public List<ComicBookEntity> findAll() {
        return comicBookJPAService.findAll();
    }

    @Transactional
    public ComicBookEntity findById(Long id) {
        return comicBookJPAService.findById(id);
    }

    @Transactional
    public ComicBookEntity update(ComicBookEntity updated) throws EntityNotFoundException {
        ComicBookEntity entity = comicBookJPAService.update(updated);
        comicBookSearchService.update(entity);
        return entity;
    }

    public List<ComicBookEntity> findPart(Range range, List<Sort> sorts) {
        return comicBookJPAService.findPart(range, sorts);
    }
}
