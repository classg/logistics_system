package cn.pei.logistics.controller;

import cn.pei.logistics.pojo.*;
import cn.pei.logistics.pojo.CustomerExample.Criteria;
import cn.pei.logistics.service.BaseDataService;
import cn.pei.logistics.service.CustomerService;
import cn.pei.logistics.service.RoleService;
import cn.pei.logistics.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDataService baseDataService;

    @RequestMapping("/customerPage")
    @RequiresPermissions("customer:customerPage")
    public String adminPage(){
        return "customerPage";
    }

    // 用户数据查询
    @RequestMapping("/list")
    @RequiresPermissions("customer:list")
    @ResponseBody
    public PageInfo<Customer> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize, String keyword){
        // 设置传入的页码和页数
        PageHelper.startPage(pageNum,pageSize);
        // 创建条件查询对象
        CustomerExample example = new CustomerExample();
        // 创建条件限制对象
        Criteria criteria1 = example.createCriteria();
        // 判断当前身份是否业务员
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        System.out.println(user+"-----------------------");
        if(user.getRolename().equals("业务员")){
            // 查询出客户表中user_id等当前身份的信息
            criteria1.andUserIdEqualTo(user.getUserId());
        }
        // 通过增强字符串类来判断是否为空,如果不为空就进去
        if(StringUtils.isNotBlank(keyword)){
            // 创建条件限制对象
            Criteria criteria = example.createCriteria();
            // 账号和真实姓名模糊查询
            criteria.andCustomerNameLike("%"+keyword+"%");
        }
        List<Customer> customers = customerService.selectByExample(example);

        PageInfo<Customer> pageInfo = new PageInfo(customers);

        return pageInfo;
    }


    // 用户数据删除
    @RequestMapping("/customer_del")
    @RequiresPermissions("customer:delete")
    @ResponseBody
    public MessageObject customer_del(Long customerId){
        MessageObject messageObject;

        int row = customerService.deleteByPrimaryKey(customerId);
        if(row == 1){
            messageObject = new MessageObject(true,"用户删除成功！");
        }else{
            messageObject = new MessageObject(false,"用户删除失败@。请联系管理员！！！");
        }

        return messageObject;
    }


    // 新增用户/更新用户
    @RequestMapping("/edit")
    public String edit(Model m,Long customerId){
        // 根据customerId查询Customer对象,以供修改回显
        if(customerId != null){
            Customer customer = customerService.selectByPrimaryKey(customerId);
            m.addAttribute("customer",customer);
        }

        // 共享当前认证的身份
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        m.addAttribute("user", user);


        // 查询出所有的业务员
        UserExample example1 = new UserExample();
        UserExample.Criteria criteria = example1.createCriteria();
        criteria.andRolenameEqualTo("业务员");
        List<User> users = userService.selectByExample(example1);
        m.addAttribute("users",users);

        // 查询出所有的角色，以供新增和更新管理员的时候选择
        CustomerExample example = new CustomerExample();
        List<Customer> customers = customerService.selectByExample(example);
        m.addAttribute("customers" , customers);

        // 查询所有区间
        List<BaseData> baseDatas = baseDataService.selectBaseDataByParentName("区间管理");
        m.addAttribute("baseDatas",baseDatas);

        return "customerEdit";
    }

    // 检查用户名是否存在
    @RequestMapping("/checkCustomername")
    @ResponseBody
    public Boolean checkCustomername(String Customername){
        CustomerExample example = new CustomerExample();
        Criteria criteria = example.createCriteria();
        criteria.andCustomerNameEqualTo(Customername);
        List<Customer> customer = customerService.selectByExample(example);
        // 如果有对象则user的长度大于0，表示存在
        return customer.size() > 0 ? false : true;
    }


    // 新增用户
    @RequestMapping("/insert")
    @RequiresPermissions("customer:insert")
    @ResponseBody
    public MessageObject insert(Customer customer){
        // 设置当前日期
        MessageObject messageObject = new MessageObject(false,"数据添加失败，请联系管理员！");
        int row = customerService.insert(customer);
        if(row == 1){
            messageObject = new MessageObject(true,"数据新增成功！！！");
        }
        return messageObject;
    }

    // 更新用户
    @RequestMapping("/update")
    @RequiresPermissions("customer:update")
    @ResponseBody
    public MessageObject update(Customer customer){
        System.out.println(customer+"-----------------------------");
        MessageObject messageObject = new MessageObject(false, "更新失败，请联系管理员！");
        int row = customerService.updateByPrimaryKeySelective(customer);
        if(row == 1){
            messageObject = new MessageObject(true,"更新成功！！！");
        }
        return messageObject;
    }


    // 获取json数据
    @RequestMapping("/getAllCustomers")
    @ResponseBody
    public List<Customer> getAllCustomer(){
        CustomerExample example = new CustomerExample();
        List<Customer> customers = customerService.selectByExample(example);
        return customers;
    }

}
