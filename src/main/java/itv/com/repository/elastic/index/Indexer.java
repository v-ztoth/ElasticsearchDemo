package itv.com.repository.elastic.index;

import java.util.List;

public interface Indexer {
    void createIndex(String indexName);
    void createAlias(String indexName, String alias);
    void addMapping(String indexName, String indexType);
    void index(String alias, String indexType, String data, String id);
    void bulkIndex(String alias, String indexType, List<String> data);
}
