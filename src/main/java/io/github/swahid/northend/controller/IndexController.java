/**
 * 
 */
package io.github.swahid.northend.controller;

import io.github.swahid.northend.helper.ExcelHelper;
import io.github.swahid.northend.service.NorthendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author swahid
 *
 */
@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    NorthendService northEndService;

    @GetMapping
    public String index(){
        return "index";
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                northEndService.save(ExcelHelper.excelToTutorials(file.getInputStream(), file.getOriginalFilename()));

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                log.info("file uplaod: " + message);
                attributes.addFlashAttribute("successMessage", message + '!');
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                log.error(message);
                attributes.addFlashAttribute("errorMessage", message  + '!');
            }
        }else{
            message = "Please upload an excel file!";
            // return success response
            log.error(message);
            attributes.addFlashAttribute("errorMessage", message + '!');
        }
        return "redirect:/";
    }

}
