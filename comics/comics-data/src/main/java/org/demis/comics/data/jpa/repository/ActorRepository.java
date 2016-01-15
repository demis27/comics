package org.demis.comics.data.jpa.repository;

import org.demis.comics.data.jpa.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("actorRepository")
public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
}
