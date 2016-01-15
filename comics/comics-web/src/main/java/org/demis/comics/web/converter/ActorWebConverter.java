package org.demis.comics.web.converter;

import org.demis.comics.data.jpa.entity.ActorEntity;
import org.demis.comics.web.dto.ActorWebDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "actorWebConverter")
public class ActorWebConverter {

    public ActorWebDTO convertEntity(ActorEntity entity) {
        ActorWebDTO dto = new ActorWebDTO();
        dto.setId(entity.getId());
        dto.setCoverName(entity.getCoverName());

        return dto;
    }

    public List<ActorWebDTO> convertEntities(List<ActorEntity> entities) {
        List<ActorWebDTO> dtos = new ArrayList<>(entities.size());

        dtos.addAll(entities.stream().map(this::convertEntity).collect(Collectors.toList()));

        return dtos;
    }

    public void updateEntity(ActorEntity entity, ActorWebDTO dto) {
        entity.setCoverName(dto.getCoverName());
    }

    public ActorEntity convertWebDTO(ActorWebDTO dto) {
        ActorEntity entity = new ActorEntity();
        entity.setId(dto.getId());
        entity.setCoverName(dto.getCoverName());

        return entity;
    }

    public List<ActorEntity> convertWebDTOs(List<ActorWebDTO> dtos) {
        List<ActorEntity> entities = new ArrayList<>(dtos.size());

        entities.addAll(dtos.stream().map(this::convertWebDTO).collect(Collectors.toList()));

        return entities;
    }
}
