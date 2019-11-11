package cn.pei.logistics.service;

import cn.pei.logistics.pojo.User;
import cn.pei.logistics.pojo.UserExample;

import java.util.List;

/*
该接口为用户接口
 */
public interface UserService {

    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int updateByPrimaryKeySelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);

    // 用于通过用户查找对象
    User selectByUsername(String username);
}
