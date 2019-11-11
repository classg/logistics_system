package cn.pei.logistics.controller;

import cn.pei.logistics.pojo.*;
import cn.pei.logistics.pojo.PermissionExample.Criteria;
import cn.pei.logistics.service.PermissionService;
import cn.pei.logistics.service.RoleService;
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
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/permissionPage")
    @RequiresPermissions("permission:permissionPage")
    public String adminPage(){
        return "permissionPage";
    }

    // 用户数据查询
    @RequestMapping("/list")
    @RequiresPermissions("permission:list")
    @ResponseBody
    public PageInfo<Permission> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize, String keyword){
        // 设置传入的页码和页数
        PageHelper.startPage(pageNum,pageSize);
        // 创建条件查询对象
        PermissionExample example = new PermissionExample();
        // 通过增强字符串类来判断是否为空,如果不为空就进去
        if(StringUtils.isNotBlank(keyword)){
            // 创建条件限制对象
            Criteria criteria = example.createCriteria();
            // 账号和真实姓名模糊查询
            criteria.andNameLike("%"+keyword+"%");
        }
        List<Permission> permissions = permissionService.selectByExample(example);

        PageInfo<Permission> pageInfo = new PageInfo(permissions);

        return pageInfo;
    }


    // 用户数据删除
    @RequestMapping("/permission_del")
    @RequiresPermissions("permission:delete")
    @ResponseBody
    public MessageObject permission_del(Long permissionId){
        MessageObject messageObject;
        PermissionExample example = new PermissionExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(permissionId);
        // 找出当前编号的的父id
        List<Permission> permissions = permissionService.selectByExample(example);
        if(permissions.size() > 0){
            messageObject = new MessageObject(false,"数据删除失败了，此权限有子权限，不可删除！");
            return messageObject;
        }
        int row = permissionService.deleteByPrimaryKey(permissionId);
        if(row == 1){
            messageObject = new MessageObject(true,"用户删除成功！");
        }else{
            messageObject = new MessageObject(false,"用户删除失败@。请联系管理员！！！");
        }

        return messageObject;
    }


    // 新增用户/更新用户
    @RequestMapping("/edit")
    public String edit(Model m,Long permissionId){
        // 根据permissionId查询Permission对象,以供修改回显
        if(permissionId != null){
            Permission permission = permissionService.selectByPrimaryKey(permissionId);
            m.addAttribute("permission",permission);
        }
        // 查询出所有的角色，以供新增和更新管理员的时候选择
        PermissionExample example = new PermissionExample();
        List<Permission> permissions = permissionService.selectByExample(example);
        m.addAttribute("permissions" , permissions);
        return "permissionEdit";
    }

    // 检查用户名是否存在
    @RequestMapping("/checkPermissionname")
    @ResponseBody
    public Boolean checkPermissionname(String Permissionname){
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(Permissionname);
        List<Permission> permission = permissionService.selectByExample(example);
        // 如果有对象则user的长度大于0，表示存在
        return permission.size() > 0 ? false : true;
    }


    // 新增用户
    @RequestMapping("/insert")
    @RequiresPermissions("permission:insert")
    @ResponseBody
    public MessageObject insert(Permission permission){
        // 设置当前日期
        MessageObject messageObject = new MessageObject(false,"数据添加失败，请联系管理员！");
        int row = permissionService.insert(permission);
        if(row == 1){
            messageObject = new MessageObject(true,"数据新增成功！！！");
        }
        return messageObject;
    }

    // 更新用户
    @RequestMapping("/update")
    @RequiresPermissions("permission:update")
    @ResponseBody
    public MessageObject update(Permission permission){
        System.out.println(permission+"-----------------------------");
        MessageObject messageObject = new MessageObject(false, "更新失败，请联系管理员！");
        int row = permissionService.updateByPrimaryKeySelective(permission);
        if(row == 1){
            messageObject = new MessageObject(true,"更新成功！！！");
        }
        return messageObject;
    }


    // 获取json数据
    @RequestMapping("/getAllPermissions")
    @ResponseBody
    public List<Permission> getAllPermissions(){
        PermissionExample example = new PermissionExample();
        List<Permission> permissions = permissionService.selectByExample(example);
        return permissions;
    }

}
