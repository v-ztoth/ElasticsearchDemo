package itv.com.business.converter;

import com.google.gson.Gson;
import itv.com.business.entity.Asset;

public class Asset2JsonConverter implements Converter{

    @Override
    public String convert(Asset asset) {
        Gson gson = new Gson();
        return gson.toJson(asset);
    }
}
