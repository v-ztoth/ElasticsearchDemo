package itv.com.repository.elastic.search;

import itv.com.business.entity.Asset;
import itv.com.repository.elastic.ESClientProvider;
import itv.com.repository.elastic.search.mapper.Mapper;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchResponse.class, SearchHit.class})
public class ElasticSearcherTest
{
    private Searcher searcher;
    private Client client;

    @Before
    public void setup() {
        client = mock(Client.class);
        ESClientProvider clientProvider = mock(ESClientProvider.class);
        Mapper mapper = mock(Mapper.class);
        Asset asset = mock(Asset.class);

        when(clientProvider.getClient()).thenReturn(client);
        when(mapper.map(anyObject())).thenReturn(asset);

        searcher = new ElasticSearcher(clientProvider, mapper);
    }

    @Test
    public void testTermSearch() {
        SearchResponse response = PowerMockito.mock(SearchResponse.class);
        SearchRequestBuilder builder = mock(SearchRequestBuilder.class);

        when(builder.get()).thenReturn(response);
        when(builder.setTypes(anyString())).thenReturn(builder);
        when(builder.setQuery(any(QueryBuilder.class))).thenReturn(builder);

        when(client.prepareSearch(anyString())).thenReturn(builder);

        SearchHits searchHits = mock(SearchHits.class);
        when(response.getHits()).thenReturn(searchHits);

        SearchHit hit = PowerMockito.mock(SearchHit.class);
        when(searchHits.getHits()).thenReturn(new SearchHit[]{hit});

        searcher.termSearch("test", "type", "field", "value");

        verify(client.prepareSearch("test"));
    }

    @Test
    public void testFullTextSearch() {
        SearchResponse response = PowerMockito.mock(SearchResponse.class);
        SearchRequestBuilder builder = mock(SearchRequestBuilder.class);

        when(builder.get()).thenReturn(response);
        when(builder.setTypes(anyString())).thenReturn(builder);
        when(builder.setQuery(any(QueryBuilder.class))).thenReturn(builder);

        when(client.prepareSearch(anyString())).thenReturn(builder);

        SearchHits searchHits = mock(SearchHits.class);
        when(response.getHits()).thenReturn(searchHits);

        SearchHit hit = PowerMockito.mock(SearchHit.class);
        when(searchHits.getHits()).thenReturn(new SearchHit[]{hit});

        searcher.fullTextSearchWithinGivenFields("test", "type", "value", Arrays.asList("field"));

        verify(client.prepareSearch("test"));
    }

    @Test
    public void testFullTextSearchAll() {
        SearchResponse response = PowerMockito.mock(SearchResponse.class);
        SearchRequestBuilder builder = mock(SearchRequestBuilder.class);

        when(builder.get()).thenReturn(response);
        when(builder.setTypes(anyString())).thenReturn(builder);
        when(builder.setQuery(any(QueryBuilder.class))).thenReturn(builder);

        when(client.prepareSearch(anyString())).thenReturn(builder);

        SearchHits searchHits = mock(SearchHits.class);
        when(response.getHits()).thenReturn(searchHits);

        SearchHit hit = PowerMockito.mock(SearchHit.class);
        when(searchHits.getHits()).thenReturn(new SearchHit[]{hit});

        searcher.fullTextSearchWithinAllFields("test", "type", "value");

        verify(client.prepareSearch("test"));
    }
}
