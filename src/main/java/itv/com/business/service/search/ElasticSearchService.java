package itv.com.business.service.search;

import itv.com.business.entity.Asset;
import itv.com.repository.elastic.search.Searcher;

import javax.inject.Inject;
import java.util.List;

class ElasticSearchService implements SearchService {

    private final Searcher searcher;

    @Inject
    public ElasticSearchService(Searcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public List<Asset> termSearch(String indexName, String indexType, String field, String value) {
        return searcher.termSearch(indexName, indexType, field, value);
    }

    @Override
    public List<Asset> fullTextSearch(String indexName, String indexType, String value, List<String> fields) {
        return searcher.fullTextSearch(indexName, indexType, value, fields);
    }
}
