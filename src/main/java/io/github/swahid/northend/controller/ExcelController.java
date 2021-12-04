package io.github.swahid.northend.controller;

import io.github.swahid.northend.entity.NorthEnd;
import io.github.swahid.northend.helper.ExcelHelper;
import io.github.swahid.northend.service.NorthendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    NorthendService northEndService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                northEndService.save(ExcelHelper.excelToTutorials(file.getInputStream(), file.getOriginalFilename()));

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @GetMapping("/data")
    public ResponseEntity<?> getData(@RequestParam(value = "startDate", required = false)Date startDate,
                                             @RequestParam(value = "endDate", required = false)Date endDate
                                             ) {
        try {
            List<NorthEnd> tutorials = northEndService.findAll(startDate, endDate);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteDate(@RequestParam(value = "fileName", required = false)String fileName){
        try {
             northEndService.deleteByFileName(fileName);

            return new ResponseEntity<>("{'delete success'}", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
