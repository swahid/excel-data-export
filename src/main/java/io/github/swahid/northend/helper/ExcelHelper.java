package io.github.swahid.northend.helper;

import io.github.swahid.northend.entity.NorthEnd;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
            //fileName = fileName.substring(0, fileName.lastIndexOf("."));
            DataFormatter formatter = new DataFormatter();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                // Do your stuff
                Sheet sheet = workbook.getSheetAt(i);
                System.out.println("sheetName: " + sheet.getSheetName());
                Iterator<Row> rows = sheet.iterator();

                if( i >6 || sheet.getSheetName().equalsIgnoreCase("Unit Guide")) break;

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
                    if (Objects.nonNull(currentRow.getCell(0))){
                        while (cellsInRow.hasNext()) {
                            Cell currentCell = cellsInRow.next();

                            switch (cellIdx) {
                                case 0:
//                                    System.out.println("Items: " + formatter.formatCellValue(currentCell));
                                    tutorial.setItems(formatter.formatCellValue(currentCell));
                                    break;

                                case 1:
//                                    System.out.println(formatter.formatCellValue(currentCell));
                                    tutorial.setUnitName("Unit: " + formatter.formatCellValue(currentCell));
                                    break;

                                case 2:
//                                    System.out.println("order1: " + formatter.formatCellValue(currentCell));
                                    tutorial.setOrder1(parseString(currentCell));
                                    break;

                                case 4:
//                                    System.out.println("delivery1: " + formatter.formatCellValue(currentCell));
                                    tutorial.setDelivery1(parseString(currentCell));
                                    break;

                                default:
                                    break;
                            }

                            cellIdx++;
                        }

                        tutorial.setDailyDate(ExcelHelper.convertDate(fileName));
                        tutorial.setFileName(new SimpleDateFormat("yyyy-MM-dd EEE").format(tutorial.getDailyDate()));
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
            System.out.println("error file: " + fileName);
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private static Date convertDate(String fileName) throws ParseException {
        //System.out.println(fileName);
        return new SimpleDateFormat("dd.MM.yyyy").parse(fileName.substring(0,10));
    }

    private static Integer parseString(Cell cellValue){

        try{
            return (int) cellValue.getNumericCellValue();
        }catch (Exception ne){
            System.out.println("Skip number" +cellValue.getStringCellValue());
            if ("" != cellValue.getStringCellValue()) {
                String parseVal = cellValue.getStringCellValue().replaceAll("[^\\d]", "");
                if ("" != parseVal) {
                    return Integer.valueOf(parseVal);
                } else {
                    return 0;
                }
            }else{
                return 0;
            }
        }

    }

}
