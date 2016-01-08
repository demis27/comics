package org.demis.comics.data.converter;

import org.demis.comics.data.dto.DTO;
import org.demis.comics.data.jpa.entity.EntityInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class GenericConverter<EntityImpl extends EntityInterface, DTOImpl extends DTO> {

    private static final Logger logger = LoggerFactory.getLogger(GenericConverter.class);

    protected final Class<EntityImpl> entityClass;

    protected final Class<DTOImpl> dtoClass;

    public GenericConverter(Class<EntityImpl> entityClass, Class<DTOImpl> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    protected abstract void copyAttributes(EntityImpl entity, DTOImpl dto);

    public DTOImpl convertEntity(EntityImpl entity) {
        DTOImpl dto = null;

        try {
            dto = dtoClass.newInstance();
            copyAttributes(entity, dto);
            dto.setId(entity.getId());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public List<DTOImpl> convertEntities(List<EntityImpl> entities) {
        List<DTOImpl> dtos = new ArrayList<>(entities.size());

        dtos.addAll(entities.stream().map(this::convertEntity).collect(Collectors.toList()));

        return dtos;
    }

    protected abstract void copyAttributes(DTOImpl dto, EntityImpl entity);

    public EntityImpl convertDTO(DTOImpl dto) {
        EntityImpl entity = null;

        try {
            entity = entityClass.newInstance();
            copyAttributes(entity, dto);
            entity.setId(dto.getId());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public List<EntityImpl> convertDTOs(List<DTOImpl> dtos) {
        List<EntityImpl> entities = new ArrayList<>(dtos.size());

        entities.addAll(dtos.stream().map(this::convertDTO).collect(Collectors.toList()));

        return entities;
    }
}
