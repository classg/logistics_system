package cn.pei.logistics.service;

import cn.pei.logistics.pojo.Role;
import cn.pei.logistics.pojo.RoleExample;

import java.util.List;

/*
该接口为用户接口
 */
public interface RoleService {

    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int updateByPrimaryKeySelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long roleId);
}
