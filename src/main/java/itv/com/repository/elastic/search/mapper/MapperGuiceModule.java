package itv.com.repository.elastic.search.mapper;

import com.google.inject.AbstractModule;

public class MapperGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Mapper.class).to(Hit2AssetMapper.class);
    }
}
