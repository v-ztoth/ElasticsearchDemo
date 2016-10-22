package itv.com.business.service.search;

import com.google.inject.AbstractModule;

public class SearchServiceGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SearchService.class).to(ElasticSearchService.class);
    }
}
