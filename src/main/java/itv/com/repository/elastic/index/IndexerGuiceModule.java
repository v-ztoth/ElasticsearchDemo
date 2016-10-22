package itv.com.repository.elastic.index;

import com.google.inject.AbstractModule;

public class IndexerGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Indexer.class).to(ElasticIndexer.class);
    }
}
