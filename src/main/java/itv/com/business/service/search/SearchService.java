package itv.com.business.service.search;


import itv.com.business.entity.Asset;

import java.util.List;

public interface SearchService {
    List<Asset> termSearch(String indexName, String indexType, String field, String value);
    List<Asset> fullTextSearch(String indexName, String indexType, String value, List<String> fields);
}
