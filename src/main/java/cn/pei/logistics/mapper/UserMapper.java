package cn.pei.logistics.mapper;

import cn.pei.logistics.pojo.User;
import cn.pei.logistics.pojo.UserExample;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int updateByPrimaryKeySelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);

    User selectByUsername(String username);
}