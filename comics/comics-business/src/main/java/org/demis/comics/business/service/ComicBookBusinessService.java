package org.demis.comics.business.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.Sort;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.data.jpa.service.ComicBookJPAService;
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

    @Transactional
    public ComicBookEntity create(ComicBookEntity created) {
        return comicBookJPAService.create(created);
    }

    @Transactional
    public ComicBookEntity delete(Long id) throws EntityNotFoundException {
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
        return comicBookJPAService.update(updated);
    }

    public List<ComicBookEntity> findPart(Range range, List<Sort> sorts) {
        return comicBookJPAService.findPart(range, sorts);
    }
}
