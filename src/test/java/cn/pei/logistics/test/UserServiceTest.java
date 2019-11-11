package cn.pei.logistics.test;

import cn.pei.logistics.pojo.User;
import cn.pei.logistics.pojo.UserExample;
import cn.pei.logistics.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSelectByExample(){
        UserExample example = new UserExample();
        List<User> users = userService.selectByExample(example);
        System.out.println(users);
    }
}
