package io.github.swahid.northend.helper;

import io.github.swahid.northend.entity.NorthEnd;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "items", "unit", "order1", "delivery1" };
    static String SHEET = "Pastry 1";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<NorthEnd> excelToTutorials(InputStream is, String fileName) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            List<NorthEnd> tutorials = new ArrayList<>();
            fileName = fileName.substring(0, fileName.lastIndexOf("."));

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if( i >6) break;
                // Do your stuff
                Sheet sheet = workbook.getSheetAt(i);
                System.out.println("sheetName: " + sheet.getSheetName());
                Iterator<Row> rows = sheet.iterator();


                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();
                    //System.out.println("ROW: " + currentRow.getCell(0));
                    // skip header
                    if (rowNumber <3) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    NorthEnd tutorial = new NorthEnd();

                    int cellIdx = 0;
                    if (!ObjectUtils.isEmpty(currentRow.getCell(0))){
                        while (cellsInRow.hasNext()) {
                            Cell currentCell = cellsInRow.next();

                            switch (cellIdx) {
                                case 0:
                                    System.out.println("Items: " + currentCell.getStringCellValue());
                                    tutorial.setItems(currentCell.getStringCellValue());
                                    break;

                                case 1:
                                    tutorial.setUnitName(currentCell.getStringCellValue());
                                    System.out.println(currentCell.getStringCellValue());
                                    break;

                                case 2:
                                    System.out.println(currentCell.getNumericCellValue());
                                    tutorial.setOrder1((int) currentCell.getNumericCellValue());
                                    break;

                                case 4:
                                    System.out.println(currentCell.getNumericCellValue());
                                    tutorial.setDelivery1((int) currentCell.getNumericCellValue());
                                    break;

                                default:
                                    break;
                            }

                            cellIdx++;
                        }

                        tutorial.setDailyDate(ExcelHelper.convertDate(fileName));
                        tutorial.setFileName(fileName);
                        tutorial.setSheetName(sheet.getSheetName());
                        tutorials.add(tutorial);
                    }

                }
                System.out.println("Sheet: " + sheet.getSheetName());
                System.out.println("totalData: " + tutorials.size());
            }

            workbook.close();

            return tutorials;
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private static Date convertDate(String fileName) throws ParseException {
        System.out.println(fileName);
        return new SimpleDateFormat("dd.MM.yyyy").parse(fileName.substring(0,10));
    }
}
