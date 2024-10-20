package config;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddCostDataset {
    @DataProvider(name = "AddCostCSVData")
    public Object[][] getCSVData() throws IOException {
        String filepath = "./src/test/resources/addcost.csv";
        List<Object[]> data = new ArrayList<>();
        CSVParser csvParser = new CSVParser(new FileReader(filepath), CSVFormat.DEFAULT.withFirstRecordAsHeader());

        for (CSVRecord csvRecord : csvParser) {
            String itemName = csvRecord.get("itemName");
            String amount = csvRecord.get("amount");
            String quantity = csvRecord.get("quantity");
            String purchaseDate = csvRecord.get("purchaseDate");
            String month = csvRecord.get("month");
            String remarks = csvRecord.get("remarks");
            data.add(new Object[]{itemName, amount, quantity, purchaseDate, month, remarks});
        }

        return data.toArray(new Object[0][]);

    }

}