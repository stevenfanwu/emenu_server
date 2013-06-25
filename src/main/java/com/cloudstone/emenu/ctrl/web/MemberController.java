/**
 * @(#)MemberController.java, 2013-6-25. 
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
public class MemberController {
    
    @RequestMapping("/member")
    public String get() {
        return "member";
    }

}
