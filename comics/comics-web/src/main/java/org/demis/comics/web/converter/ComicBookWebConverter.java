package org.demis.comics.web.converter;

import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.web.dto.ComicBookWebDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "comicBookWebConverter")
public class ComicBookWebConverter {

    public ComicBookWebDTO convertEntity(ComicBookEntity entity) {
        ComicBookWebDTO dto = new ComicBookWebDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        return dto;
    }

    public List<ComicBookWebDTO> convertEntities(List<ComicBookEntity> entities) {
        List<ComicBookWebDTO> dtos = new ArrayList<>(entities.size());

        dtos.addAll(entities.stream().map(this::convertEntity).collect(Collectors.toList()));

        return dtos;
    }

    public void updateEntity(ComicBookEntity entity, ComicBookWebDTO dto) {
        entity.setTitle(dto.getTitle());
    }

    public ComicBookEntity convertWebDTO(ComicBookWebDTO dto) {
        ComicBookEntity entity = new ComicBookEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());

        return entity;
    }

    public List<ComicBookEntity> convertWebDTOs(List<ComicBookWebDTO> dtos) {
        List<ComicBookEntity> entities = new ArrayList<>(dtos.size());

        entities.addAll(dtos.stream().map(this::convertWebDTO).collect(Collectors.toList()));

        return entities;
    }
}
