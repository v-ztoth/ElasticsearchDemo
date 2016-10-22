package itv.com.repository.elastic.search;

import com.google.inject.AbstractModule;

public class SearcherGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Searcher.class).to(ElasticSearcher.class);
    }
}
