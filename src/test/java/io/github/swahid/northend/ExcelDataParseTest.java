package io.github.swahid.northend;

import io.github.swahid.northend.helper.ExcelHelper;
import io.github.swahid.northend.service.NorthendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootTest
public class ExcelDataParseTest {

    @Autowired
    NorthendService northEndService;

    @Test
    public void parseData() throws FileNotFoundException {

        String filePath = "/Users/swahid/workspaces/self/shormin/document/northend/Delivery-Apolo/data";
        System.out.println("filePath: " + filePath);

        File folder = new File(filePath);
        System.out.println("folder Size: " + folder.list().length);

        for(final File file : folder.listFiles()) {
//            if (file.isDirectory()) {
                System.out.println("fileName: " + file.getName());
                northEndService.save(ExcelHelper.excelToTutorials(new FileInputStream(file), file.getName()));
//            } else {
//                System.out.println(file.getName());
//            }
            System.out.println("delete file:  " + file.getName());
            file.delete();

            //break;
        }
    }
}
