package cn.pei.logistics.controller;

import cn.pei.logistics.pojo.*;
import cn.pei.logistics.service.RoleService;
import cn.pei.logistics.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @PackageName:cn.pei.logistics.controller
 * @ClassName:AdminController
 * @Description:
 * @author:CJ
 * @date:2019/10/27 18:38
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping("/rolePage")
    @RequiresPermissions("role:rolePage")
    public String adminPage(){
        return "rolePage";
    }

    // 用户数据查询
    @RequestMapping("/list")
    @RequiresPermissions("role:list")
    @ResponseBody
    public PageInfo<Role> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize, String keyword){
        // 设置传入的页码和页数
        PageHelper.startPage(pageNum,pageSize);
        // 创建条件查询对象
        RoleExample example = new RoleExample();
        // 通过增强字符串类来判断是否为空,如果不为空就进去
        if(StringUtils.isNotBlank(keyword)){
            // 创建条件限制对象
            RoleExample.Criteria criteria = example.createCriteria();
            // 账号和真实姓名模糊查询
            criteria.andRolenameLike("%"+keyword+"%");
        }
        List<Role> roles = roleService.selectByExample(example);

        PageInfo<Role> pageInfo = new PageInfo(roles);

        return pageInfo;
    }


    // 用户数据删除
    @RequestMapping("/role_del")
    @RequiresPermissions("role:delete")
    @ResponseBody
    public MessageObject role_del(Long roleId){
        MessageObject messageObject;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<User> users = userService.selectByExample(example);
        if(users.size() > 0){
            messageObject = new MessageObject(false,"此角色还拥有用户，不能直接删除");
            return messageObject;
        }
        int row = roleService.deleteByPrimaryKey(roleId);
        if(row == 1){
            messageObject = new MessageObject(true,"用户删除成功！");
        }else{
            messageObject = new MessageObject(false,"删除数据失败，请联系管理员");
        }

        return messageObject;
    }


    // 新增用户/更新用户
    @RequestMapping("/edit")
    public String edit(Model m,Long roleId){
        // 根据roleId查询Role对象,以供修改回显
        if(roleId != null){
            Role role = roleService.selectByPrimaryKey(roleId);
            m.addAttribute("role",role);
        }
        // 查询出所有的角色，以供新增和更新管理员的时候选择
        RoleExample example = new RoleExample();
        List<Role> roles = roleService.selectByExample(example);
        m.addAttribute("roles" , roles);
        return "roleEdit";
    }

    // 检查用户名是否存在
    @RequestMapping("/checkRolename")
    @ResponseBody
    public Boolean checkRolename(String rolename){
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andRolenameEqualTo(rolename);
        List<Role> role = roleService.selectByExample(example);
        for (Role role1 : role) {
            System.out.println(role1);
        }
        // 如果有对象则user的长度大于0，表示存在
        return role.size() > 0 ? false : true;
    }



    // 新增用户
    @RequestMapping("/insert")
    @RequiresPermissions("role:insert")
    @ResponseBody
    public MessageObject insert(Role role){
        // 设置当前日期
        MessageObject messageObject = new MessageObject(false,"数据添加失败，请联系管理员！");
        int row = roleService.insert(role);
        if(row == 1){
            messageObject = new MessageObject(true,"数据新增成功！！！");
        }
        System.out.println(role.toString());
        return messageObject;
    }

    // 更新用户
    @RequestMapping("/update")
    @RequiresPermissions("role:update")
    @ResponseBody
    public MessageObject update(Role role){
        System.out.println(role+"-----------------------------");
        MessageObject messageObject = new MessageObject(false, "更新失败，请联系管理员！");
        int row = roleService.updateByPrimaryKeySelective(role);
        if(row == 1){
            messageObject = new MessageObject(true,"更新成功！！！");
        }
        return messageObject;
    }

}
