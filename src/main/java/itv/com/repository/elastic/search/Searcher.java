package itv.com.repository.elastic.search;


import itv.com.business.entity.Asset;

import java.util.List;

public interface Searcher {
    List<Asset> termSearch(String indexName, String indexType, String field, String value);
    List<Asset> fullTextSearchWithinGivenFields(String indexName, String indexType, String value, List<String> fields);
    List<Asset> fullTextSearchWithinAllFields(String indexName, String indexType, String value);
}
