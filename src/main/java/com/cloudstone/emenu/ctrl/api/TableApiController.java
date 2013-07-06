/**
 * @(#)TableApiController.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.logic.TableLogic;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class TableApiController extends BaseApiController {
    
    @Autowired
    private TableLogic tableLogic;

    @RequestMapping(value="/api/tables", method=RequestMethod.POST)
    public @ResponseBody Table add(@RequestBody String body, HttpServletResponse resp) {
        Table table = JsonUtils.fromJson(body, Table.class);
        table = tableLogic.add(table);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return table;
    }

    @RequestMapping(value="/api/tables", method=RequestMethod.GET)
    public @ResponseBody List<Table> get() {
        return tableLogic.getAll();
    }
    
    @RequestMapping(value="/api/tables/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Table update(@PathVariable(value="id") long tableId,
            @RequestBody String body, HttpServletResponse response) {
        Table table = JsonUtils.fromJson(body, Table.class);
        if (table.getId() != tableId) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return tableLogic.update(table);
    }
    
    @RequestMapping(value="/api/tables/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") long tableId,
            HttpServletResponse response) {
        tableLogic.delete(tableId);
    }
}
