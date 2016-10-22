package itv.com.repository.elastic.search;

import itv.com.business.entity.Asset;
import itv.com.repository.elastic.ESClientProvider;
import itv.com.repository.elastic.search.mapper.Mapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

class ElasticSearcher implements Searcher {

    private final ESClientProvider clientProvider;

    private final Mapper mapper;

    @Inject
    public ElasticSearcher(ESClientProvider clientProvider, Mapper mapper) {
        this.clientProvider = clientProvider;
        this.mapper = mapper;
    }

    @Override
    public List<Asset> termSearch(String indexName, String indexType, String field, String value) {
        try(Client client = clientProvider.getClient()) {

            QueryBuilder query = termQuery(
                    field,
                    value
            );

           return search(indexName, indexType, query, client);
        }
    }

    private List<Asset> search(String indexName, String indexType, QueryBuilder query, Client client) {
        SearchResponse response = client.prepareSearch(indexName)
                .setTypes(indexType)
                .setQuery(query)
                .get();

        return getHits(response);
    }

    private List<Asset> getHits(SearchResponse response) {
        SearchHit[] hits = response.getHits().getHits();

        return Arrays.stream(hits)
                .map(SearchHit::getSource)
                .map(hitAsMap -> mapper.map(hitAsMap))
                .collect(Collectors.toList());
    }

    @Override
    public List<Asset> fullTextSearch(String indexName, String indexType, String value, List<String> fields) {
        try(Client client = clientProvider.getClient()) {

            QueryBuilder query = multiMatchQuery(
                    value,
                    fields.toArray(new String[fields.size()])
            );

            return search(indexName, indexType, query, client);
        }
    }
}
