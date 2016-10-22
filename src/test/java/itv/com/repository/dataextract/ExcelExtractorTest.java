package itv.com.repository.dataextract;

import itv.com.business.entity.Asset;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelExtractorTest {

    private DataExtractor dataExtractor;

    private List<Asset> expectedAssets;
    private List<Asset> actualAssets;

    @Test
    public void testExtract() {
        givenAnExcelExtractor();
        givenAnExceptedAssetList();
        whenExtractCalled();
        thenProperAssetsExtracted();
    }

    private void givenAnExcelExtractor() {
        this.dataExtractor = new ExcelExtractor();
    }

    private void givenAnExceptedAssetList() {
        Asset assetOne = new Asset("111", "11111", "asset11111", "desc11111");
        Asset assetTwo = new Asset("111", "22222", "asset22222", "desc22222");
        Asset assetThree = new Asset("111", "33333", "asset33333", "desc33333");

        List<Asset> assets = new ArrayList<>();
        assets.add(assetOne);
        assets.add(assetTwo);
        assets.add(assetThree);

        this.expectedAssets = assets;
    }

    private void whenExtractCalled() {
        actualAssets = dataExtractor.extract();
    }

    private void thenProperAssetsExtracted() {
        Assert.assertEquals(expectedAssets, actualAssets);
    }

}
