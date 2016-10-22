package itv.com.repository.elastic.search.mapper;


import itv.com.business.entity.Asset;

import java.util.Map;

public class Hit2AssetMapper implements Mapper<Map<String, Object>> {

    public Asset map(Map<String, Object> hit) {
        String productionId = hit.get("productionId").toString();
        String assetId = hit.get("assetId").toString();
        String assetName = hit.get("assetName").toString();
        String assetDescription = hit.get("assetDescription").toString();

        return new Asset(productionId, assetId, assetName, assetDescription);
    }
}
