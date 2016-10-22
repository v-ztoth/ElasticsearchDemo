package itv.com.repository.dataextract;

import itv.com.business.entity.Asset;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class ExcelExtractor implements DataExtractor {

    public List<Asset> extract() {

        try {
            Path path = Paths.get(getClass().getResource("/sample.xlsx").toURI());
            Workbook workbook = new XSSFWorkbook(Files.newInputStream(path));


            Sheet sheet = workbook.getSheetAt(0);

            List<Asset> assets = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Asset asset = convertRowToAsset(row);
                assets.add(asset);
            }

            return assets;
        } catch (Exception e) {
            throw new RuntimeException("Cannot read excel file!", e);
        }
    }

    private Asset convertRowToAsset(Row row) {
        DataFormatter formatter = new DataFormatter();

        String productionId = formatter.formatCellValue(row.getCell(0));
        String assetId = formatter.formatCellValue(row.getCell(1));
        String assetName = formatter.formatCellValue(row.getCell(3));
        String assetDescription = formatter.formatCellValue(row.getCell(4));

        return new Asset(productionId, assetId, assetName, assetDescription);
    }

}
