package itv.com.http;

import itv.com.business.entity.Asset;
import itv.com.business.service.search.SearchService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class SearchEndpoint
{
    private final SearchService searchService;

    @Inject
    public SearchEndpoint(SearchService searchService)
    {
        this.searchService = searchService;
    }

    //http://localhost:8080/demo/search/term?indexName=demo&indexType=asset&fieldName=productionId&fieldValue=111
    @GET()
    @Path("term")
    @Produces(MediaType.APPLICATION_JSON)
    public Response termSearch(@QueryParam("indexName") String indexName,
                               @QueryParam("indexType") String indexType,
                               @QueryParam("fieldName") String fieldName,
                               @QueryParam("fieldValue") String fieldValue)
    {
        List<Asset> assets = searchService.termSearch(indexName, indexType, fieldName, fieldValue);
        return Response.ok(assets).build();
    }

    //http://localhost:8080/demo/search/fulltext?indexName=demo&indexType=asset&fieldValue=David%20James&fieldNames=assetDescription&fieldNames=assetName
    @GET()
    @Path("fulltext")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fullTextSearch(@QueryParam("indexName") String indexName,
                               @QueryParam("indexType") String indexType,
                               @QueryParam("fieldValue") String fieldValue,
                               @QueryParam("fieldNames") List<String> fieldNames)
    {
        List<Asset> assets = searchService.fullTextSearch(indexName, indexType, fieldValue, fieldNames);
        return Response.ok(assets).build();
    }
}
