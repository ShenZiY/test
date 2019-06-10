package com.hrms.controller;

import com.hrms.bean.Employee;
import com.hrms.service.EmployeeService;
import com.hrms.util.JsonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author GenshenWang.nomico
 * @date 2018/3/7.
 */
@Controller
@RequestMapping(value = "/hrms/emp")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * Ա��ɾ������
     * @param empId
     * @return
     */
    @RequestMapping(value = "/deleteEmp/{empId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonMsg deleteEmp(@PathVariable("empId") Integer empId){
        int res = 0;
        if (empId > 0){
            res = employeeService.deleteEmpById(empId);
        }
        if (res != 1){
            return JsonMsg.fail().addInfo("emp_del_error", "Ա��ɾ���쳣");
        }
        return JsonMsg.success();
    }

    /**
     * ����Ա����Ϣ
     * @param empId
     * @param employee
     * @return
     */
    @RequestMapping(value ="/updateEmp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonMsg updateEmp(@PathVariable("empId") Integer empId,  Employee employee){
        int res = employeeService.updateEmpById(empId, employee);
        if (res != 1){
            return JsonMsg.fail().addInfo("emp_update_error", "�����쳣");
        }
        return JsonMsg.success();
    }

    /**
     * ��ѯ�����Ա�������Ƿ��ظ�
     * @param empName
     * @return
     */
    @RequestMapping(value = "/checkEmpExists", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg checkEmpExists(@RequestParam("empName") String empName){
        //������������������ʽ������֤
        String regName = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regName)){
            return JsonMsg.fail().addInfo("name_reg_error", "��������Ϊ2-5λ���Ļ�6-16λӢ�ĺ��������");
        }
        Employee employee = employeeService.getEmpByName(empName);
        if (employee != null){
            return JsonMsg.fail().addInfo("name_reg_error", "�û����ظ�");
        }else {
            return JsonMsg.success();
        }
    }

    /**
     * ������¼�󣬲�ѯ���µ�ҳ��
     * @return
     */
    @RequestMapping(value = "/getTotalPages", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getTotalPage(){
        int totalItems = employeeService.getEmpCount();
        //��ȡ�ܵ�ҳ��
        int temp = totalItems / 5;
        int totalPages = (totalItems % 5 == 0) ? temp : temp+1;
        return JsonMsg.success().addInfo("totalPages", totalPages);
    }

    /**
     * ����Ա��
     * @param employee ������Ա����Ϣ
     * @return
     */
    @RequestMapping(value = "/addEmp", method = RequestMethod.POST)
    @ResponseBody
    public JsonMsg addEmp(Employee employee){
        int res = employeeService.addEmp(employee);
        if (res == 1){
            return JsonMsg.success();
        }else {
            return JsonMsg.fail();
        }
    }

    /**
     * ����id��ѯԱ����Ϣ
     * @param empId
     * @return
     */
    @RequestMapping(value = "/getEmpById/{empId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getEmpById(@PathVariable("empId") Integer empId){
        Employee employee = employeeService.getEmpById(empId);
        if (employee != null){
            return JsonMsg.success().addInfo("employee", employee);
        }else {
            return JsonMsg.fail();
        }

    }
    /**
     * ��ѯ
     * @param pageNo ��ѯָ��ҳ�����������
     * @return
     */
    @RequestMapping(value = "/getEmpList", method = RequestMethod.GET)
    public ModelAndView getEmp(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        ModelAndView mv = new ModelAndView("employeePage");
        int limit = 5;
        // ��¼��ƫ����(���ӵ�offset�м�¼��ʼ��ѯ)��
        // ���1ҳ�Ǵӵ�1��(offset=(21-1)*5=0,offset+1=0+1=1)��ʼ��ѯ��
        // ��2ҳ�ӵ�6��(offset=(2-1)*5=5,offset+1=5+1=6)��¼��ʼ��ѯ
        int offset = (pageNo-1)*limit;
        //��ȡָ��ҳ��������Ա����Ϣ
        List<Employee> employees = employeeService.getEmpList(offset, limit);
        //��ȡ�ܵļ�¼��
        int totalItems = employeeService.getEmpCount();
        //��ȡ�ܵ�ҳ��
        int temp = totalItems / limit;
        int totalPages = (totalItems % limit == 0) ? temp : temp+1;
        //��ǰҳ��
        int curPage = pageNo;

        //��������ѯ����ŵ�Model�У���JSPҳ���п��Խ���չʾ
        mv.addObject("employees", employees)
                .addObject("totalItems", totalItems)
                .addObject("totalPages", totalPages)
                .addObject("curPage", curPage);
        return mv;
    }





}
