package cn.pei.logistics.service.impl;

import cn.pei.logistics.mapper.UserMapper;
import cn.pei.logistics.pojo.User;
import cn.pei.logistics.pojo.UserExample;
import cn.pei.logistics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName:cn.pei.logistics.service.impl
 * @ClassName:UserServiceImpl
 * @Description: 实现用户接口的实现类
 * @author:CJ
 * @date:2019/10/27 15:42
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<User> selectByExample(UserExample example) {
        return userMapper.selectByExample(example);
    }

    @Override
    public User selectByPrimaryKey(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
