package itv.com.repository.elastic.index;

import itv.com.repository.elastic.index.ElasticIndexer;
import itv.com.repository.elastic.index.Indexer;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import itv.com.repository.elastic.ESClientProvider;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CreateIndexResponse.class, IndicesAliasesResponse.class
        , PutMappingResponse.class, IndexResponse.class, BulkResponse.class})
public class ElasticIndexerTest {

    private Indexer indexer;
    private IndicesAdminClient indicesAdminClient;
    private Client client;

    @Before
    public void setup() {
        client = mock(Client.class);
        AdminClient adminClient = mock(AdminClient.class);
        when(client.admin()).thenReturn(adminClient);
        indicesAdminClient = mock(IndicesAdminClient.class);
        when(adminClient.indices()).thenReturn(indicesAdminClient);
        ESClientProvider clientProvider = mock(ESClientProvider.class);
        when(clientProvider.getClient()).thenReturn(client);
        indexer = new ElasticIndexer(clientProvider);
    }

    @Test
    public void testCreateIndex() {
        CreateIndexResponse response = PowerMockito.mock(CreateIndexResponse.class);
        CreateIndexRequestBuilder builder = mock(CreateIndexRequestBuilder.class);

        when(builder.get()).thenReturn(response);
        when(indicesAdminClient.prepareCreate(anyString())).thenReturn(builder);
        when(response.isAcknowledged()).thenReturn(true);

        indexer.createIndex("test");

        verify(indicesAdminClient.prepareCreate("test"));
    }

    @Test
    public void testCreateAlias() {
        IndicesAliasesResponse response = PowerMockito.mock(IndicesAliasesResponse.class);
        IndicesAliasesRequestBuilder builder = mock(IndicesAliasesRequestBuilder.class);

        when(builder.get()).thenReturn(response);
        when(builder.addAlias(anyString(), anyString())).thenReturn(builder);
        when(indicesAdminClient.prepareAliases()).thenReturn(builder);
        when(response.isAcknowledged()).thenReturn(true);

        indexer.createAlias("test-v1", "test");

        verify(indicesAdminClient.prepareAliases().addAlias("test-v1", "test"));
    }

    @Test
    public void testAddMapping() {
        PutMappingResponse response = PowerMockito.mock(PutMappingResponse.class);
        PutMappingRequestBuilder builder = mock(PutMappingRequestBuilder.class);

        when(builder.get()).thenReturn(response);
        when(indicesAdminClient.preparePutMapping(anyString())).thenReturn(builder);
        when(builder.setType(anyString())).thenReturn(builder);
        when(builder.setSource(anyString())).thenReturn(builder);
        when(response.isAcknowledged()).thenReturn(true);

        indexer.addMapping("test", "test");

        verify(indicesAdminClient.preparePutMapping("test"));
    }

    @Test
    public void testIndex() {
        IndexResponse response = PowerMockito.mock(IndexResponse.class);
        IndexRequestBuilder builder = mock(IndexRequestBuilder.class);

        when(builder.get()).thenReturn(response);
        when(client.prepareIndex(anyString(), anyString(), anyString())).thenReturn(builder);
        when(builder.setSource(anyString())).thenReturn(builder);
        when(response.isCreated()).thenReturn(true);

        indexer.index("test", "test", "assetAsJson", "1");

        verify(client.prepareIndex("test", "test", "1"));
    }

    @Test
    public void testBulkIndex() {
        BulkResponse response = PowerMockito.mock(BulkResponse.class);
        BulkRequestBuilder bulkBuilder = mock(BulkRequestBuilder.class);
        IndexRequestBuilder indexBuilder = mock(IndexRequestBuilder.class);

        when(bulkBuilder.get()).thenReturn(response);
        when(bulkBuilder.add(any(IndexRequestBuilder.class))).thenReturn(bulkBuilder);
        when(indexBuilder.setSource(anyString())).thenReturn(indexBuilder);
        when(response.hasFailures()).thenReturn(false);
        when(client.prepareIndex(anyString(), anyString(), anyString())).thenReturn(indexBuilder);
        when(client.prepareBulk()).thenReturn(bulkBuilder);

        indexer.bulkIndex("test", "test", Arrays.asList("assetAsJson"));

        verify(client.prepareBulk());
    }
}
