package itv.com.business.converter;

import com.google.inject.AbstractModule;

public class ConverterGuiceModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(Converter.class).to(Asset2JsonConverter.class);
    }
}
