
package com.example.demo.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.TreeNode;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月9日 上午10:26:02

* 类说明

*/
@Mapper
public interface PowerDao {
	public  List<TreeNode> getPowerList(int userId);
	/**
	 * 获取全部权限
	 * @return
	 */
	public List<TreeNode> getAllPowerList();
	/**
	 * 根据权限id找到父权限id
	 * @param id
	 * @return
	 */
	public Integer getFatherPowerId(int id);

	public void deleteRolePower(int id);

	public void insertRolePower(Set<Integer> powerSet,int id);

	public List<Integer> getPowerIdsByUserId(Integer id);

}
