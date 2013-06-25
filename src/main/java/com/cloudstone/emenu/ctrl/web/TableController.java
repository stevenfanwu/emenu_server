/**
 * @(#)TableController.java, 2013-6-25. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class TableController {

    @RequestMapping("/table")
    public String tableManage() {
        return "table";
    }

    @RequestMapping(value={"/", "/home", "operate", "/status"})
    public String tableStatus() {
        return "status";
    }
}
