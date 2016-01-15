package org.demis.comics.data.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name="comic_actor_relation")
public class ActorComicBookAssociationEntity extends AbstractEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorComicBookAssociationEntity.class);

    private Long id;
    private ActorRole role;
    private ActorEntity actor;
    private ComicBookEntity comicBook;

    @Id
    @Column (name="comic_actor_relation_id", precision = 10)
    @GeneratedValue(generator="comic_actor_relation_sequence")
    @SequenceGenerator(name="comic_actor_relation_sequence", sequenceName="comic_actor_relation_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column (name="role")
    @Enumerated(EnumType.STRING)
    public ActorRole getRole() {
        return role;
    }

    public void setRole(ActorRole role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name="actor_id", referencedColumnName="actor_id")
    public ActorEntity getActor() {
        return actor;
    }

    public void setActor(ActorEntity actor) {
        this.actor = actor;
    }

    @ManyToOne
    @JoinColumn(name="comic_book_id", referencedColumnName="comic_book_id")
    public ComicBookEntity getComicBook() {
        return comicBook;
    }

    public void setComicBook(ComicBookEntity comicBook) {
        this.comicBook = comicBook;
    }
}
