package itv.com.business.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Asset {
    private final String productionId;
    private final String assetId;
    private final String assetName;
    private final String assetDescription;

    public Asset(String productionId, String assetId, String assetName, String assetDescription) {
        this.productionId = productionId;
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetDescription = assetDescription;
    }

    public String getProductionId() {
        return productionId;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getAssetDescription() {
        return assetDescription;
    }
}
