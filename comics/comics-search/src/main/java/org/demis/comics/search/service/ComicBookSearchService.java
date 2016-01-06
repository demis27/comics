package org.demis.comics.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.comics.data.converter.ComicBookConverter;
import org.demis.comics.data.converter.GenericConverter;
import org.demis.comics.data.dto.ComicBookDTO;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.search.SearchConfig;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value ="comicBookSearchService")
public class ComicBookSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComicBookSearchService.class);

    public static final String COMIC_BOOK_MAPPING = "comicBook";

    final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Client client;

    @Autowired
    private SearchConfig configuration;

    @Autowired
    private ComicBookConverter converter;

    public ComicBookDTO create(ComicBookEntity entity) {
        ComicBookDTO dto = converter.convertEntity(entity);

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
    protected String getMapping() {
        return COMIC_BOOK_MAPPING;
    }
}
