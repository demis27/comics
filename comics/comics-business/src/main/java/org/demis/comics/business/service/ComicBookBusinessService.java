package org.demis.comics.business.service;

import org.demis.comics.data.Range;
import org.demis.comics.data.SortParameterElement;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.data.jpa.service.ComicBookJPAService;
import org.demis.comics.search.service.ComicBookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service(value = "comicBookBusinessService")
public class ComicBookBusinessService {

    @Autowired
    @Qualifier("comicBookJPAService")
    private ComicBookJPAService comicBookJPAService;

    @Autowired
    @Qualifier("comicBookSearchService")
    private ComicBookSearchService comicBookSearchService;

    @Transactional
    public ComicBookEntity create(ComicBookEntity created) throws ExecutionException, InterruptedException {
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
    public ComicBookEntity update(ComicBookEntity updated) throws EntityNotFoundException, ExecutionException, InterruptedException {
        ComicBookEntity entity = comicBookJPAService.update(updated);
        comicBookSearchService.update(entity);
        return entity;
    }

    public List<ComicBookEntity> findPart(Range range, List<SortParameterElement> sorts) {
        return comicBookJPAService.findPart(range, sorts);
    }

    public List<ComicBookEntity> searchEverywhere(String value, Range range, List<SortParameterElement> sorts) throws ExecutionException, InterruptedException {
        List<Long> ids = comicBookSearchService.searchEverywhere(value, range, sorts);
        List<ComicBookEntity> result = new ArrayList<>(ids.size());

        for (Long id: ids) {
            ComicBookEntity entity = comicBookJPAService.findById(id);
            if (entity != null) {
                result.add(entity);
            }
        }

        return result;
    }
}
