package itv.com.http;


import itv.com.business.service.push.PushService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PushEndpoint
{
    private final PushService pushService;

    @Inject
    public PushEndpoint(PushService pushService)
    {
        this.pushService = pushService;
    }

    //localhost:8080/demo/push
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response pushData() {
        pushService.pushDataFromExcel2Elasticsearch();
        return Response.ok("Data successfully indexed!").build();
    }
}
