package itv.com.repository.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESClientProvider implements AutoCloseable {

    public Client getClient() {
        Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch").build();
        try {
            return TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            throw new RuntimeException("Cannot connect to ES!", e);
        }
    }

    @Override
    public void close() {
        System.out.println("Close!");
    }
}
