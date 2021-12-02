/**
 * 
 */
package io.github.swahid.northend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author swahid
 *
 */

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(){
        return "index";
    }



}
