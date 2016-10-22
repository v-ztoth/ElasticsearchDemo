package itv.com.http;

import io.soabase.guice.JerseyGuiceModule;

public class EndpointGuiceModule extends JerseyGuiceModule {

    @Override
    protected void configureServlets() {
        bind(ESDemoEndpoint.class);
        bind(PushEndpoint.class);
        bind(SearchEndpoint.class);
    }
}
