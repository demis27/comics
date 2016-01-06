package org.demis.comics.data.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name="comic_book")
public class ComicBookEntity extends AbstractEntity implements EntityInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComicBookEntity.class);

    private Long id;
    private String title;

    @Id
    @Column (name="comic_book_id", precision = 10)
    @GeneratedValue(generator="comic_book_sequence")
    @SequenceGenerator(name="comic_book_sequence", sequenceName="comic_book_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column (name="title", nullable = false, unique = false, length = 256)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
