package itv.com.business.service.push;

import itv.com.business.converter.Converter;
import javax.inject.Inject;

import itv.com.business.entity.Asset;
import itv.com.repository.elastic.index.Indexer;
import itv.com.repository.dataextract.DataExtractor;

import java.util.ArrayList;
import java.util.List;

class Excel2ElasticDataPusher implements PushService {

    private static final String ALIAS = "demo";
    private static final String INDEX_NAME = "demo-v1";
    private static final String INDEX_TYPE = "asset";

    private final DataExtractor dataExtractor;
    private final Indexer indexer;
    private final Converter converter;

    @Inject
    Excel2ElasticDataPusher(DataExtractor dataExtractor, Indexer indexer, Converter converter) {
        this.dataExtractor = dataExtractor;
        this.indexer = indexer;
        this.converter = converter;
    }

    @Override
    public void pushDataFromExcel2Elasticsearch() {
        prepareIndex();
        pushData();
    }

    private void prepareIndex() {
        indexer.createIndex(INDEX_NAME);
        indexer.createAlias(INDEX_NAME, ALIAS);
        indexer.addMapping(INDEX_NAME, INDEX_TYPE);
    }

    private void pushData() {
        List<Asset> assets = dataExtractor.extract();

        //Validation

        List<String> jsonAssets = new ArrayList<>();

        for (Asset asset : assets) {
            jsonAssets.add(converter.convert(asset));
        }

        indexer.bulkIndex(INDEX_NAME, INDEX_TYPE, jsonAssets);
    }
}
