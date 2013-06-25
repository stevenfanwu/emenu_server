/**
 * @(#)PadController.java, 2013-6-25. 
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
public class PadController {
    
    @RequestMapping("/pad")
    public String padManage() {
        return "pad";
    }
    
    @RequestMapping("/monitor")
    public String padMonitor() {
        return "monitor";
    }

}
