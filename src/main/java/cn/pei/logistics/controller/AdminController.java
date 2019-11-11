package cn.pei.logistics.controller;

import cn.pei.logistics.pojo.*;
import cn.pei.logistics.pojo.UserExample.Criteria;
import cn.pei.logistics.service.RoleService;
import cn.pei.logistics.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @PackageName:cn.pei.logistics.controller
 * @ClassName:AdminController
 * @Description:
 * @author:CJ
 * @date:2019/10/27 18:38
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @RequestMapping("/login")
    private String login(HttpServletRequest request,Model m){
        String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("异常==="+shiroLoginFailure);
        if(UnknownAccountException.class.getName().equals(shiroLoginFailure)){
            m.addAttribute("errorMsg","亲！您的账号不存在呢");
        }else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
            m.addAttribute("errorMsg","亲！您的密码不正确！");
        }
        return "forward:/login.jsp";
    }


    @RequestMapping("/adminPage")
    @RequiresPermissions("admin:adminPage")
    public String adminPage(){
        return "adminPage";
    }

    // 用户数据查询
    @RequiresPermissions("admin:list")
    @RequestMapping("/list")
    @ResponseBody
    public PageInfo<User> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize, String keyword){
        // 设置传入的页码和页数
        PageHelper.startPage(pageNum,pageSize);
        // 创建条件查询对象
        UserExample example = new UserExample();
        // 通过增强字符串类来判断是否为空,如果不为空就进去
        if(StringUtils.isNotBlank(keyword)){
            // 创建条件限制对象
            Criteria criteria = example.createCriteria();
            // 账号和真实姓名模糊查询
            criteria.andUsernameLike("%"+keyword+"%");
            /*
            直接在一个Criteria中设置多条件是AND关系，如果要条件使用OR，必须创建多个Criteria，再设置OR
             */
            Criteria criteria1 = example.createCriteria();
            criteria1.andRealnameLike("%"+keyword+"%");
            // 再设置OR关系
            example.or(criteria1);
        }
        List<User> users = userService.selectByExample(example);
        for (User user : users) {
            System.out.println(user);
        }

        PageInfo<User> pageInfo = new PageInfo<User>(users);

        return pageInfo;
    }


    // 用户数据删除
    @RequestMapping("/admin_del")
    @RequiresPermissions("admin:delete")
    @ResponseBody
    public MessageObject admin_del(Long userId){
        MessageObject messageObject = new MessageObject(false,"数据删除失败了，请联系管理员！");
        int row = userService.deleteByPrimaryKey(userId);
        if(row == 1){
            messageObject = new MessageObject(true,"数据删除成功！");
        }
        return messageObject;
    }


    // 新增用户/更新用户
    @RequestMapping("/edit")
    public String edit(Model m,Long userId){
        // 根据userId查询User对象,以供修改回显
        if(userId != null){
            User user = userService.selectByPrimaryKey(userId);
            m.addAttribute("user",user);
        }
        // 查询出所有的角色，以供新增和更新管理员的时候选择
        RoleExample example = new RoleExample();
        List<Role> roles = roleService.selectByExample(example);
        m.addAttribute("roles" , roles);
        return "adminEdit";
    }

    // 检查用户名是否存在
    @RequestMapping("/checkUsername")
    @ResponseBody
    public Boolean checkUsername(String username){
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> user = userService.selectByExample(example);
        // 如果有对象则user的长度大于0，表示存在
        return user.size() > 0 ? false : true;
    }


    // 新增用户
    @RequestMapping("/insert")
    @RequiresPermissions("admin:insert")
    @ResponseBody
    public MessageObject insert(User user){
        // 设置当前日期
        user.setCreateDate(new Date());

        // 设置密码、设置盐
        // 密码 = 原密码 + 盐 MD5 * 3 次散列结果
        // 生成随机盐
        String salt = UUID.randomUUID().toString().substring(0, 7);
        user.setSalt(salt);

        // 密码加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 3);
        user.setPassword(md5Hash.toString());
        MessageObject messageObject = new MessageObject(false,"数据添加失败，请联系管理员！");
        int row = userService.insert(user);
        if(row == 1){
            messageObject = new MessageObject(true,"数据新增成功！！！");
        }
        return messageObject;
    }

    // 更新用户
    @RequestMapping("/update")
    @RequiresPermissions("admin:update")
    @ResponseBody
    public MessageObject update(User user){
        // 设置密码、设置盐
        // 密码 = 原密码 + 盐 MD5 * 3 次散列结果
        // 生成随机盐
        String salt = UUID.randomUUID().toString().substring(0, 7);
        user.setSalt(salt);

        // 密码加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 3);
        user.setPassword(md5Hash.toString());
        MessageObject messageObject = new MessageObject(false, "更新失败，请联系管理员！");
        int row = userService.updateByPrimaryKeySelective(user);
        if(row == 1){
            messageObject = new MessageObject(true,"更新成功！！！");
        }
        return messageObject;
    }

}
