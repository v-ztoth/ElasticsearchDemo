package itv.com;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.soabase.guice.GuiceBundle;
import io.soabase.guice.InjectorProvider;
import io.soabase.guice.StandardInjectorProvider;
import itv.com.business.converter.ConverterGuiceModule;
import itv.com.business.service.push.PusherGuiceModule;
import itv.com.business.service.search.SearchServiceGuiceModule;
import itv.com.http.EndpointGuiceModule;
import itv.com.http.RuntimeExceptionMapper;
import itv.com.repository.dataextract.ExtractorGuiceModule;
import itv.com.repository.elastic.index.IndexerGuiceModule;
import itv.com.repository.elastic.search.SearcherGuiceModule;
import itv.com.repository.elastic.search.mapper.MapperGuiceModule;
import org.glassfish.jersey.server.ServerProperties;

import java.util.HashMap;
import java.util.Map;

public class ESDemoApplication extends Application<Configuration> {

    private InjectorProvider<Configuration> injectorProvider;

    public static void main(String[] args) throws Exception
    {
        new ESDemoApplication().run("server");
    }

    @Override
    public String getName() {
        return "ES Demo";
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap)
    {
        // Use the StandardInjectorProvider unless you need special behaviour - you can pass as many modules as you like
        injectorProvider = new StandardInjectorProvider<>(
                new ConverterGuiceModule(),
                new PusherGuiceModule(),
                new SearchServiceGuiceModule(),
                new ExtractorGuiceModule(),
                new IndexerGuiceModule(),
                new MapperGuiceModule(),
                new SearcherGuiceModule(),
                new EndpointGuiceModule());
        bootstrap.addBundle(new GuiceBundle<>(injectorProvider));
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception
    {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(ServerProperties.WADL_FEATURE_DISABLE, false);
        environment.jersey().getResourceConfig().addProperties(properties);
        environment.jersey().register(new RuntimeExceptionMapper());
    }
}
