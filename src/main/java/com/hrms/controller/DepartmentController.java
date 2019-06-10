package com.hrms.controller;

import com.hrms.bean.Department;
import com.hrms.service.DepartmentService;
import com.hrms.util.JsonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author GenshenWang.nomico
 * @date 2018/3/8.
 */
@Controller
@RequestMapping(value = "/hrms/dept")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    /**
     * ɾ��
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/delDept/{deptId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonMsg deleteDept(@PathVariable("deptId") Integer deptId){
        int res = 0;
        if (deptId > 0){
            res = departmentService.deleteDeptById(deptId);
        }
        if (res != 1){
            return JsonMsg.fail().addInfo("del_dept_error", "ɾ���쳣");
        }
        return JsonMsg.success();
    }

    /**
     * ���Ÿ���
     * @param deptId
     * @param department
     * @return
     */
    @RequestMapping(value = "/updateDept/{deptId}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonMsg updateDeptById(@PathVariable("deptId") Integer deptId, Department department){

        int res = 0;
        if (deptId > 0){
            res = departmentService.updateDeptById(deptId, department);
        }
        if (res != 1){
            return JsonMsg.fail().addInfo("update_dept_error", "���Ÿ���ʧ��");
        }
        return JsonMsg.success();
    }

    /**
     * ��������
     * @param department
     * @return
     */
    @RequestMapping(value = "/addDept", method = RequestMethod.PUT)
    @ResponseBody
    public JsonMsg addDept(Department department){
        int res = departmentService.addDept(department);
        if (res != 1){
            return JsonMsg.fail().addInfo("add_dept_error", "����쳣��");
        }
        return JsonMsg.success();
    }

    /**
     * ��ѯ������Ϣ��ҳ����
     * @return
     */
    @RequestMapping(value = "/getTotalPages", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getTotalPages(){

        //ÿҳ��ʾ�ļ�¼����
        int limit = 5;
        //�ܼ�¼��
        int totalItems = departmentService.getDeptCount();
        int temp = totalItems / limit;
        int totalPages = (totalItems % limit== 0) ? temp : temp+1;

        return JsonMsg.success().addInfo("totalPages", totalPages);
    }

    /**
     *
     */

    @RequestMapping(value = "/getDeptById/{deptId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getDeptById(@PathVariable("deptId") Integer deptId){
        Department department = null;
        if (deptId > 0){
            department = departmentService.getDeptById(deptId);
        }
        if (department != null){
            return JsonMsg.success().addInfo("department", department);
        }
        return JsonMsg.fail().addInfo("get_dept_error", "�޲�����Ϣ");
    }

    /**
     * ��ҳ��ѯ������ָ��ҳ����Ӧ������
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/getDeptList", method = RequestMethod.GET)
    public ModelAndView getDeptList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        ModelAndView mv = new ModelAndView("departmentPage");
        //ÿҳ��ʾ�ļ�¼����
        int limit = 5;
        //�ܼ�¼��
        int totalItems = departmentService.getDeptCount();
        int temp = totalItems / limit;
        int totalPages = (totalItems % limit== 0) ? temp : temp+1;
        //ÿҳ����ʼ��(offset+1)���ݣ����һҳ(offset=0���ӵ�1(offset+1)�����ݿ�ʼ)
        int offset = (pageNo - 1)*limit;
        List<Department> departments = departmentService.getDeptList(offset, limit);

        mv.addObject("departments", departments)
                .addObject("totalItems", totalItems)
                .addObject("totalPages", totalPages)
                .addObject("curPageNo", pageNo);
        return mv;
    }

    /**
     * ��ѯ���в�������
     * @return
     */
    @RequestMapping(value = "/getDeptName", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getDeptName(){
        List<Department> departmentList = departmentService.getDeptName();
        if (departmentList != null){
            return JsonMsg.success().addInfo("departmentList", departmentList);
        }
        return JsonMsg.fail();
    }


}
