package org.demis.comics.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demis.comics.data.Range;
import org.demis.comics.data.SortParameterElement;
import org.demis.comics.data.converter.ComicBookConverter;
import org.demis.comics.data.dto.ComicBookDTO;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.search.SearchConfig;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public ComicBookDTO create(ComicBookEntity entity) throws ExecutionException, InterruptedException {
        ComicBookDTO dto = converter.convertEntity(entity);

        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), dto.getId().toString())
                    .setSource(mapper.writeValueAsString(dto))
                    .execute()
                    .get();
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

    public ComicBookDTO update(ComicBookEntity entity) throws ExecutionException, InterruptedException {
        return create(entity);
    }

    public List<Long> searchEverywhere(String value, Range range, List<SortParameterElement> sorts) throws ExecutionException, InterruptedException {


        SearchResponse response = client.prepareSearch(configuration.getIndexName())
                .setTypes(getMapping())
                .setQuery(QueryBuilders.queryStringQuery(value))
                .setFrom(range.getStart()).setSize(range.getSize())
                .addSort(new FieldSortBuilder("title"))
                .execute().get();

        SearchHit[] hits = response.getHits().getHits();

        List<Long> result = new ArrayList<>(hits.length);

        for (SearchHit hit: hits) {
            result.add(Long.parseLong(hit.getId()));
        }

        return result;
    }

    protected String getMapping() {
        return COMIC_BOOK_MAPPING;
    }
}
