package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Emp;
import com.example.demo.entity.EmpNew;
@Mapper
public interface EmpDao {

	@Select("select count(*) from emp")
	int getCount();

	List<Map<String,Object>> getList();

	List<EmpNew> getEmpList(String input);
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int batchInsert(List<Emp> list);

	int batchUpdate(List<Emp> list);

	int addEmp(String emp_name,String dept_id);

	int updateEmp(String id ,String emp_name,String dept_id );

	void deleteEmp(String id);
	/**
	 * 批量删除
	 * @param idsList
	 */
	int batchDeleteEmp(List<String> idsList);
}
