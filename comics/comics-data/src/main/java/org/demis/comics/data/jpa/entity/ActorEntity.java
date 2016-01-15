package org.demis.comics.data.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="actor")
public class ActorEntity extends AbstractEntity implements EntityInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorEntity.class);

    private Long id;
    private String coverName;
    private List<ActorComicBookAssociationEntity> comics;

    @Id
    @Column(name="actor_id", precision = 10)
    @GeneratedValue(generator="actor_sequence")
    @SequenceGenerator(name="actor_sequence", sequenceName="actor_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column (name="cover_name", nullable = false, unique = false, length = 256)
    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    @OneToMany(mappedBy = "actor")
    public List<ActorComicBookAssociationEntity> getComics() {
        if (comics == null) {
            comics = new ArrayList<>();
        }
        return comics;

    }

    public void setComics(List<ActorComicBookAssociationEntity> comics) {
        this.comics = comics;
    }
}
