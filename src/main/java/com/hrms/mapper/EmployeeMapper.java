package com.hrms.mapper;

import com.hrms.bean.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author GenshenWang.nomico
 * @date 2018/3/5.
 */
public interface EmployeeMapper {

    String TABLE_NAME = "tbl_emp";
    String INSERT_FIELDS = "emp_name, emp_email, gender, department_id";
    String SELECT_FIELDS = "emp_id, " + INSERT_FIELDS;

    /**
     * ================================= ɾ�� ============================================
     */
    @Delete({"DELETE FROM", TABLE_NAME, "WHERE emp_id = #{empId}"})
    int deleteOneById(@Param("empId") Integer empId);
    /**
     * ================================= �޸� ============================================
     */
    int updateOneById(@Param("empId") Integer empId,
                      @Param("employee") Employee employee);
    /**
     * =================================����============================================
     */
    @Insert({"INSERT INTO", TABLE_NAME, "(",INSERT_FIELDS,") " +
                    "VALUES(#{empName}, " +
                    "#{empEmail}, " +
                    "#{gender}, " +
                    "#{departmentId})"})
    int insertOne(Employee employee);

    /**
     * =================================��ѯ============================================
     */
    Employee selectOneById(@Param("empId") int empId);
    Employee selectOneByName(@Param("empName") String empName);
    //��ѯ���в�����Ϣ��Employee
    Employee selectWithDeptById(@Param("empId") Integer empId);

    /**
     * ��ҳ��ѯ
     * @param limit ���ؼ�¼�������
     * @param offset ���ؼ�¼�е�ƫ����
     * @return ��offset=10��limit=5��ʱ�򣬾ͻ�����ݿ��11�м�¼��ʼ����5����ѯ���������Χ��(offset+1)---(offset+limit)
     */
    List<Employee> selectByLimitAndOffset(@Param("offset") Integer offset,
                                          @Param("limit") Integer limit);

    /**
     * ��ѯ�ܼ�¼��
     * @return
     */
    @Select({"select count(*) from", TABLE_NAME})
    int countEmps();

}