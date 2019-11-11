package cn.pei.logistics.mapper;

import cn.pei.logistics.pojo.Permission;
import cn.pei.logistics.pojo.PermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Long permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<String> serlectPermissionByIds(@Param("permissionIds") List<Long> permissionIdsList);
}