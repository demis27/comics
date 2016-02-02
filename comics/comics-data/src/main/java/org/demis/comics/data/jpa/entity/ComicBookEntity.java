package org.demis.comics.data.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="comic_book")
public class ComicBookEntity extends AbstractEntity implements EntityInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComicBookEntity.class);

    private Long id;
    private String title;
    private List<ActorComicBookAssociationEntity> actors;
    private String isbn;
    private String summary;

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

    @Column (name="isbn", nullable = true, unique = false, length = 17)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Lob
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @OneToMany(mappedBy = "comicBook", fetch = FetchType.EAGER)
    public List<ActorComicBookAssociationEntity> getActors() {
        return actors;
    }

    public void setActors(List<ActorComicBookAssociationEntity> actors) {
        this.actors = actors;
    }

    public void addActor(ActorEntity actorEntity, ActorRole role) {
        ActorComicBookAssociationEntity association = new ActorComicBookAssociationEntity();
        association.setComicBook(this);
        association.setActor(actorEntity);
        association.setRole(role);

        if (this.actors == null) {
            actors = new ArrayList<>();
        }

        this.actors.add(association);
        actorEntity.getComics().add(association);
    }
}
