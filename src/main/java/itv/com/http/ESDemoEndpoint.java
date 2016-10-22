package itv.com.http;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("demo")
public class ESDemoEndpoint
{
    private Injector injector;

    @Inject
    public ESDemoEndpoint(final Injector i) {
        this.injector = i;
    }

    @Path("push")
    public PushEndpoint getAssetEndpoint() {
        return injector.getInstance(PushEndpoint.class);
    }

    @Path("search")
    public SearchEndpoint getSearchEndpoint() {
        return injector.getInstance(SearchEndpoint.class);
    }

}
