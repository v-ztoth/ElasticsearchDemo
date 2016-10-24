package itv.com.business.service.search;

import itv.com.business.entity.Asset;
import itv.com.repository.elastic.search.Searcher;
import org.apache.commons.collections4.CollectionUtils;

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
        if (CollectionUtils.isEmpty(fields)) {
            return searcher.fullTextSearchWithinAllFields(indexName, indexType, value);
        }
        return searcher.fullTextSearchWithinGivenFields(indexName, indexType, value, fields);
    }
}
