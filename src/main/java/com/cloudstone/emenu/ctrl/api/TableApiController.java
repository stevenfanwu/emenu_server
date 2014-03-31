/**
 * @(#)TableApiController.java, 2013-7-6. 
 *
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 */
@Controller
public class TableApiController extends BaseApiController {

    @RequestMapping(value = "/api/tables", method = RequestMethod.POST)
    public
    @ResponseBody
    Table add(@RequestBody String body,
              HttpServletRequest request,
              HttpServletResponse resp) {
        EmenuContext context = newContext(request);
        Table table = JsonUtils.fromJson(body, Table.class);
        table = tableLogic.add(context, table);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return table;
    }

    @RequestMapping(value = "/api/tables/occupied", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Table> getOccupied(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return tableLogic.getOccupied(context);
    }

    @RequestMapping(value = "/api/tables", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Table> get(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return tableLogic.getAll(context);
    }

    @RequestMapping(value = "/api/tables/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Table update(@PathVariable(value = "id") int tableId,
                 @RequestBody String body,
                 HttpServletRequest request,
                 HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Table table = JsonUtils.fromJson(body, Table.class);
        if (table.getId() != tableId) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return tableLogic.update(context, table);
    }

    @RequestMapping(value = "/api/tables/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") int tableId,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        EmenuContext context = newContext(request);
        tableLogic.delete(context, tableId);
    }

    @RequestMapping(value = "/api/tables/change", method = RequestMethod.PUT)
    public void changeTable(@RequestParam("fromId") int fromId,
                            @RequestParam("toId") int toId,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        tableLogic.changeTable(context, fromId, toId);
    }

    @RequestMapping(value = "/api/tables/{id:[\\d]+}/clear", method = RequestMethod.PUT)
    public
    @ResponseBody
    Table clear(@PathVariable("id") int tableId,
                HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return tableLogic.clearTable(context, tableId);
    }

    @RequestMapping(value = "/api/tables/{id:[\\d]+}/occupy", method = RequestMethod.PUT)
    public
    @ResponseBody
    Table occupy(@PathVariable("id") int tableId,
                 @RequestParam("customerNumber") int customerNumber,
                 HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return tableLogic.occupy(context, tableId, customerNumber);
    }
}
