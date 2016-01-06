package org.demis.comics.data.jpa.repository;

import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("comicBookRepository")
public interface ComicBookRepository extends JpaRepository<ComicBookEntity, Long> {
}
