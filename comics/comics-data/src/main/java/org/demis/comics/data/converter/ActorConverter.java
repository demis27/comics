package org.demis.comics.data.converter;

import org.demis.comics.data.dto.ActorDTO;
import org.demis.comics.data.jpa.entity.ActorEntity;
import org.springframework.stereotype.Service;

@Service(value="actorConverter")
public class ActorConverter extends GenericConverter<ActorEntity, ActorDTO> {

    public ActorConverter() {
        super(ActorEntity.class, ActorDTO.class);
    }

    @Override
    protected void copyAttributes(ActorEntity entity, ActorDTO dto) {
        dto.setCoverName(entity.getCoverName());
    }

    @Override
    protected void copyAttributes(ActorDTO dto, ActorEntity entity) {
        entity.setCoverName(dto.getCoverName());
    }


}
