package org.demis.comics.data.jpa.repository;

import org.demis.comics.data.jpa.entity.ActorComicBookAssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("actorComicBookAssociationRepository")
public interface ActorComicBookAssociationRepository extends JpaRepository<ActorComicBookAssociationEntity, Long> {
}
