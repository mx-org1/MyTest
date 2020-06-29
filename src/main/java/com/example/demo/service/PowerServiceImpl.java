
package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TreeNode;
import com.example.demo.mapper.PowerDao;

/**

* @author 作者 Your-Name: mx

* @version 创建时间：2019年10月9日 上午10:16:24

* 类说明

*/
@Service
public class PowerServiceImpl implements PowerService{

	@Autowired
	PowerDao powerDao;
	 /**
     * 递归绑定所有子节点
     * @param parentTreeNode
     * @param powerAllList
     */
    private  void bindChirldren(TreeNode parentTreeNode,List<TreeNode> powerAllList ){
        for(TreeNode chirldrenTreeNode:powerAllList){
            if(parentTreeNode.getId()==chirldrenTreeNode.getParentId()){
                //获取当前节点的所有子节点集合
                List<TreeNode> children = parentTreeNode.getChildren();
                if(children==null){//如果孩子节点为空,
                    List<TreeNode> childrenTempList = new ArrayList<TreeNode>();//实例化孩子集合
                    childrenTempList.add(chirldrenTreeNode);//添加子节点到集合里面
                    parentTreeNode.setChildren(childrenTempList);//设置孩子集合
                }else{//不空，说明设置过
                    children.add(chirldrenTreeNode);//添加子节点到集合里面
                }
                //自己调用自己,找孩子
                bindChirldren(chirldrenTreeNode,powerAllList);
            }
        }
    }


    /**
     * 直接给角色
     * @param
     * @return
     */

    public List<TreeNode> getBackList(int userId){

        //查询数据
        List<TreeNode> powerMapList = powerDao.getPowerList(userId);
        //定义返回列表
        List<TreeNode> powerList = new ArrayList<TreeNode>();
        //判断是否为空
        if(powerMapList!=null&&powerMapList.size()>0){
            //循环遍历，构造TreeNode对象，加入powerList
            for(TreeNode treeNode:powerMapList){

                powerList.add(treeNode);
            }
        }
        return powerList;
    }
	/**
	 * 获取全部权限不需要角色id
	 * @param userId
	 * @return
	 */
    public List<TreeNode> getAllBackList(){
    	 List<TreeNode> powerAllList = powerDao.getAllPowerList();
         //临时的powerList
         List<TreeNode> powerTempList = new ArrayList<TreeNode>();
         //判断是否为空
         if(powerAllList!=null&&powerAllList.size()>0){
             for(TreeNode ptreeNode:powerAllList){
                 if(ptreeNode.getParentId()==0){//如果等于0,说明是一级节点
                     powerTempList.add(ptreeNode);
                     //递归绑定子节点,ptreeNode是所有的父节点
                     bindChirldren(ptreeNode,powerAllList);
                 }
             }
         }
         return powerTempList;
    }
    /**
     * 直接给角色
     * @param
     * @return
     */

    @Override
    public List<TreeNode> getBackPowerList(int userId) {

        List<TreeNode> powerAllList = getBackList(userId);
        //临时的powerList
        List<TreeNode> powerTempList = new ArrayList<TreeNode>();
        //判断是否为空
        if(powerAllList!=null&&powerAllList.size()>0){
            for(TreeNode ptreeNode:powerAllList){
                if(ptreeNode.getParentId()==0){//如果等于0,说明是一级节点
                    powerTempList.add(ptreeNode);
                    //递归绑定子节点,ptreeNode是所有的父节点
                    bindChirldren(ptreeNode,powerAllList);
                }
            }
        }
        return powerTempList;
    }


	@Override
	public void updatePowerById(int[] checkedKeys,int id) {
		//看看每个id的父id存在不，不存在的话插进集合
		Set<Integer> powerSet=new HashSet<Integer>();
		for (int i = 0; i < checkedKeys.length; i++) {
			powerSet.add(checkedKeys[i]);
			//直接放父id，set集合会去重的
			Integer fatherPowerId=powerDao.getFatherPowerId(checkedKeys[i]);
			if(fatherPowerId!=null&&fatherPowerId!=0) {
				powerSet.add(fatherPowerId);
			}
		}
		//删除角色的所有权限
		powerDao.deleteRolePower(id);
		powerDao.insertRolePower(powerSet,id);
	}


	@Override
	public List<Integer> getPowerIdsByUserId(Integer id) {
		List<Integer> powerIdsByUserId = powerDao.getPowerIdsByUserId(id);
		return powerIdsByUserId;
	}


    /**
     * 从session获取的
     * @param httpSession
     * @return
     */

   /* public List<TreeNode> getList(HttpSession httpSession){
        //获取员工的角色id
        Integer roleId = getRoleId(httpSession);
        //查询数据
        List<Map> powerMapList = powerDao.getPowerList(roleId);
        //定义返回列表
        List<TreeNode> powerList = new ArrayList<TreeNode>();
        //判断是否为空
        if(powerMapList!=null&&powerMapList.size()>0){
            TreeNode treeNode = null;
            //循环遍历，构造TreeNode对象，加入powerList
            for(Map powerMap:powerMapList){
                // treeNode = new TreeNode(id, text, parentId, state, iconCls, url);
                //if (powerMap.get("id") != null && powerMap.get("parentid") != null) {
                treeNode = new TreeNode(Integer.valueOf(powerMap.get("ID")+""), powerMap.get("NAME")+"",
                        Integer.valueOf(powerMap.get("PARENTID")+""), powerMap.get("STATE")+""
                        , powerMap.get("ICONCLS")+"", powerMap.get("URL")+"");
                //}
                powerList.add(treeNode);
            }
        }
        return powerList;
    }*/

    /**
     * 从session获取的
     * @param
     * @return
     */

    /*@Override
    public List<TreeNode> getPowerList(HttpSession session) {

        // TODO Auto-generated method stub
        List<TreeNode> powerAllList = getList(session);
        //临时的powerList
        List<TreeNode> powerTempList = new ArrayList<TreeNode>();
        //判断是否为空
        if(powerAllList!=null&&powerAllList.size()>0){
            for(TreeNode ptreeNode:powerAllList){
                if(ptreeNode.getParentId()==0){//如果等于0,说明是一级节点
                    powerTempList.add(ptreeNode);
                    //递归绑定子节点,ptreeNode是所有的父节点
                    bindChirldren(ptreeNode,powerAllList);
                }
            }
        }
        return powerTempList;
    }*/


}
