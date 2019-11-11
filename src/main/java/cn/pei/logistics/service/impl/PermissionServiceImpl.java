package cn.pei.logistics.service.impl;

import cn.pei.logistics.mapper.PermissionMapper;
import cn.pei.logistics.pojo.Permission;
import cn.pei.logistics.pojo.PermissionExample;
import cn.pei.logistics.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:cn.pei.logistics.service.impl
 * @ClassName:PermissionServiceImpl
 * @Description: 实现用户接口的实现类
 * @author:CJ
 * @date:2019/10/27 15:42
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public int deleteByPrimaryKey(Long permissionId) {
        return permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public int insert(Permission record) {
        return permissionMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Permission record) {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Permission> selectByExample(PermissionExample example) {
        return permissionMapper.selectByExample(example);
    }

    @Override
    public Permission selectByPrimaryKey(Long permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public List<String> serlectPermissionByIds(List<Long> permissionIdsList) {
        return permissionMapper.serlectPermissionByIds(permissionIdsList);
    }
}
