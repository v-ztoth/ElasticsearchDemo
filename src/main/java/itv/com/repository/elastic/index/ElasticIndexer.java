package itv.com.repository.elastic.index;

import javax.inject.Inject;

import itv.com.repository.elastic.ESClientProvider;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import java.util.List;

class ElasticIndexer implements Indexer{

    private final ESClientProvider clientProvider;

    @Inject
    public ElasticIndexer(ESClientProvider clientProvider) {
        this.clientProvider = clientProvider;
    }

    @Override
    public void createIndex(String indexName) {
        try(Client client = clientProvider.getClient()) {

            boolean isIndexCreated = createIndex(indexName, client);
            if (!isIndexCreated){
                throw new RuntimeException("Index was not created!");
            }
        }
    }

    @Override
    public void createAlias(String indexName, String alias) {
        try(Client client = clientProvider.getClient()) {
            boolean isAliasCreated = createAlias(indexName, alias, client);
            if (!isAliasCreated){
                throw new RuntimeException("Alias was not created!");
            }
        }
    }

    @Override
    public void addMapping(String indexName, String indexType) {
        try(Client client = clientProvider.getClient()) {
            boolean isAdded = addMapping(indexName, indexType, client);
            if (!isAdded){
                throw new RuntimeException("Mapping was not added!");
            }
        }
    }

    @Override
    public void index(String alias, String indexType, String asset, String id) {
        try (Client client = clientProvider.getClient()) {

            boolean isIndexed = indexData(alias, indexType, client, asset, id.toString());
            if (!isIndexed) {
                throw new RuntimeException("Data was not indexed!");
            }
        }
    }

    @Override
    public void bulkIndex(String alias, String indexType, List<String> assets) {
        try(Client client = clientProvider.getClient()) {

            Long id = 1L;

            BulkRequestBuilder bulkRequest = client.prepareBulk();

            for (String asset : assets) {
                bulkRequest.add(client.prepareIndex(alias, indexType, id.toString())
                        .setSource(asset));
                id++;
            }

            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                throw new RuntimeException("Bulk index failed!!" + bulkResponse.buildFailureMessage());
            }
        }
    }

    private boolean createIndex(String indexName, Client client) {
        CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).get();
        return response.isAcknowledged();
    }

    private boolean createAlias(String indexName, String alias, Client client) {
        IndicesAliasesResponse response = client.admin().indices()
                .prepareAliases()
                .addAlias(indexName, alias)
                .get();

        return response.isAcknowledged();
    }

    private boolean addMapping(String indexName, String indexType, Client client) {

        String mapping = "{" +
                "    \"" + indexType + "\" : {" +
                "      \"properties\" : {" +
                "        \"productionId\" : {" +
                "          \"type\" :    \"string\"" +
                "        }," +
                "        \"assetId\" : {" +
                "          \"type\" :   \"string\"" +
                "        }," +
                "        \"assetName\" : {" +
                "          \"type\" :   \"string\"" +
                "        }," +
                "        \"assetDescription\" : {" +
                "          \"type\" :   \"string\"" +
                "        }" +
                "      }" +
                "    }" +
                "}";

        PutMappingResponse response = client.admin().indices()
                .preparePutMapping(indexName)
                .setType(indexType)
                .setSource(mapping)
                .get();

        return response.isAcknowledged();
    }

    private boolean indexData(String alias, String indexType, Client client, String data, String id) {
        IndexResponse response = client.prepareIndex(alias, indexType, id)
                .setSource(data)
                .get();

        return response.isCreated();
    }
}
