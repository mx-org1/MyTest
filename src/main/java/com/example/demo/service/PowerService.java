
package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.TreeNode;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月9日 上午10:13:57

* 类说明 权限接口

*/

public interface PowerService {

	public List<TreeNode> getBackPowerList(int userId);

	public List<TreeNode> getAllBackList();

	public void updatePowerById(int[] checkedKeys,int id);

	public List<Integer> getPowerIdsByUserId(Integer id);
}
