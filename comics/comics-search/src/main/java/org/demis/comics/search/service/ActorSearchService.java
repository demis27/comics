package org.demis.comics.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.comics.data.converter.ActorConverter;
import org.demis.comics.data.dto.ActorDTO;
import org.demis.comics.data.jpa.entity.ActorEntity;
import org.demis.comics.search.SearchConfig;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value ="actorSearchService")
public class ActorSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorSearchService.class);

    public static final String ACTOR_MAPPING = "actor";

    final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Client client;

    @Autowired
    private SearchConfig configuration;

    @Autowired
    private ActorConverter converter;

    public ActorDTO create(ActorEntity entity) {
        ActorDTO dto = converter.convertEntity(entity);

        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), dto.getId().toString())
                    .setSource(mapper.writeValueAsString(dto))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            LOGGER.error("Error in JSon conversion for model: " + entity, e);
        }
        return dto;
    }

    public void delete(Long id) {
        client.prepareDelete(configuration.getIndexName(), getMapping(), id.toString())
                .execute()
                .actionGet();
    }

    public ActorDTO update(ActorEntity entity) {
        return create(entity);
    }

    protected String getMapping() {
        return ACTOR_MAPPING;
    }
    
}
