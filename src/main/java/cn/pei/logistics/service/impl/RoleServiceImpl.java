package cn.pei.logistics.service.impl;

import cn.pei.logistics.mapper.RoleMapper;
import cn.pei.logistics.pojo.Role;
import cn.pei.logistics.pojo.RoleExample;
import cn.pei.logistics.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:cn.pei.logistics.service.impl
 * @ClassName:RoleServiceImpl
 * @Description: 实现用户接口的实现类
 * @author:CJ
 * @date:2019/10/27 15:42
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Long roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Role> selectByExample(RoleExample example) {
        return roleMapper.selectByExample(example);
    }

    @Override
    public Role selectByPrimaryKey(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }
}
