package itv.com.business.service.push;

import com.google.inject.AbstractModule;
import itv.com.business.service.push.Excel2ElasticDataPusher;

public class PusherGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PushService.class).to(Excel2ElasticDataPusher.class);
    }
}
