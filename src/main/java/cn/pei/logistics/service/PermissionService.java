package cn.pei.logistics.service;

import cn.pei.logistics.pojo.Permission;
import cn.pei.logistics.pojo.PermissionExample;

import java.util.List;

/*
该接口为用户接口
 */
public interface PermissionService {

    int deleteByPrimaryKey(Long permissionId);

    int insert(Permission record);

    int updateByPrimaryKeySelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Long permissionId);

    /**
     * 根据permissionId查询权限表达式
     * @param permissionIdsList
     * @return List<String>
     */
    List<String> serlectPermissionByIds(List<Long> permissionIdsList);
}
