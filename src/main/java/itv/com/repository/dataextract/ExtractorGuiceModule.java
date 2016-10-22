package itv.com.repository.dataextract;

import com.google.inject.AbstractModule;

public final class ExtractorGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataExtractor.class).to(ExcelExtractor.class);
    }

}
