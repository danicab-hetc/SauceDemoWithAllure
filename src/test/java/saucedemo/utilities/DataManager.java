package saucedemo.utilities;

import java.lang.reflect.Field;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataManager {

    public static <T> Object[][] excelToDataProvider(String fileName, String sheetName, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        String path = Route.DATA_PATH + fileName;

        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next(); // skip header

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                T instance = clazz.getDeclaredConstructor().newInstance();

                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    Cell cell = row.getCell(i);

                    String value = (cell == null || cell.toString().trim().isEmpty()) ? "" : cell.toString();
                    fields[i].set(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0];  // return empty data provider on error
        }

        return list.stream()
                .map(item -> new Object[]{item})
                .toArray(Object[][]::new);
    }

    public static Object[][] getSortOptions() {
        return new Object[][] {
                {"Name (A to Z)"},
                {"Name (Z to A)"},
                {"Price (low to high)"},
                {"Price (high to low)"}
        };
    }
    public static Object[][] getQuantity(){
        return new Object[][]{
                {"one"},
                {"all"}
        };
    }

    public static Object[][] getItemClick() {
        return new Object[][] {
                {"title"},
                {"image"}
        };
    }
}
