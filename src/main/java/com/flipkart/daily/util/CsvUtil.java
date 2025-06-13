package com.flipkart.daily.util;

import com.flipkart.daily.model.Item;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.exceptions.CsvValidationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    private static final String[] CSV_HEADER = {"brand", "category", "price", "quantity"};

    // ✅ Read CSV file and return a list of Items
    public static List<Item> readItemsFromCsv(MultipartFile file) throws IOException, CsvValidationException {
        List<Item> items = new ArrayList<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] nextRecord;
            boolean isFirstRow = true;

            while ((nextRecord = csvReader.readNext()) != null) {
                if (isFirstRow) {
                    isFirstRow = false; // skip header
                    continue;
                }

                if (nextRecord.length != 4) continue;

                Item item = new Item();
                item.setBrand(nextRecord[0].trim());
                item.setCategory(nextRecord[1].trim());
                item.setPrice(Integer.parseInt(nextRecord[2].trim()));
                item.setQuantity(Integer.parseInt(nextRecord[3].trim()));
                items.add(item);
            }
        }

        return items;
    }

    // ✅ Write list of Items to CSV file and stream it to output
    public static void writeItemsToCsv(List<Item> items, OutputStream outputStream) throws IOException {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            writer.writeNext(CSV_HEADER); // header
            for (Item item : items) {
                String[] row = {
                        item.getBrand(),
                        item.getCategory(),
                        String.valueOf(item.getPrice()),
                        String.valueOf(item.getQuantity())
                };
                writer.writeNext(row);
            }
        }
    }
}
