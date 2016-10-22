package itv.com.repository.dataextract;


import itv.com.business.entity.Asset;

import java.util.List;

public interface DataExtractor {

    List<Asset> extract();

}
