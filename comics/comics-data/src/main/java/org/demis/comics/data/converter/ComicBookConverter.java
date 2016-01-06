package org.demis.comics.data.converter;

import org.demis.comics.data.dto.ComicBookDTO;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.springframework.stereotype.Service;

@Service(value="comicBookConverter")
public class ComicBookConverter extends GenericConverter<ComicBookEntity, ComicBookDTO> {

    public ComicBookConverter() {
        super(ComicBookEntity.class, ComicBookDTO.class);
    }

    @Override
    protected void copyAttributes(ComicBookEntity entity, ComicBookDTO dto) {
        dto.setTitle(entity.getTitle());
    }

    @Override
    protected void copyAttributes(ComicBookDTO dto, ComicBookEntity entity) {
        entity.setTitle(dto.getTitle());
    }
}
