package org.demis.comics.data.converter;

import org.demis.comics.data.dto.ActorDTO;
import org.demis.comics.data.jpa.entity.ActorEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ActorConverterTest  {

    public ActorConverter converter = new ActorConverter();

    @Test
    public void convertEntity() {
        ActorEntity entity = new ActorEntity();
        entity.setId(1L);
        entity.setCoverName("RG");

        ActorDTO dto = converter.convertEntity(entity);
        Assert.assertNotNull(dto);
        Assert.assertEquals(entity.getId(), dto.getId());
        Assert.assertEquals(entity.getCoverName(), dto.getCoverName());
    }
}