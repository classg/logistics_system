package cn.pei.logistics.controller;

import cn.pei.logistics.pojo.*;
import cn.pei.logistics.service.BaseDataService;
import cn.pei.logistics.service.CustomerService;
import cn.pei.logistics.service.OrderService;
import cn.pei.logistics.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BaseDataService baseDataService;

    @RequestMapping("/orderPage")
    @RequiresPermissions("order:orderPage")
    public String adminPage(){
        return "orderPage";
    }

    // 用户数据查询
    @RequestMapping("/list")
    @RequiresPermissions("order:list")
    @ResponseBody
    public PageInfo<Order> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize, String keyword){
        // 设置传入的页码和页数
        PageHelper.startPage(pageNum,pageSize);
        // 创建条件查询对象
        OrderExample example = new OrderExample();
        // 通过增强字符串类来判断是否为空,如果不为空就进去
        if(StringUtils.isNotBlank(keyword)){
            // 创建条件限制对象
            OrderExample.Criteria criteria = example.createCriteria();
            // 账号和真实姓名模糊查询
//            criteria.andOrdernameLike("%"+keyword+"%");
        }
        List<Order> orders = orderService.selectByExample(example);

        PageInfo<Order> pageInfo = new PageInfo(orders);

        return pageInfo;
    }


    @RequestMapping("/detail")
    @ResponseBody
    public List<OrderDetail> detail(Long orderId){
        List<OrderDetail> orderDetails = orderService.selectOrderDetailsByOrderId(orderId);
        return orderDetails;
    }


    // 用户数据删除
    @RequestMapping("/order_del")
    @RequiresPermissions("order:delete")
    @ResponseBody
    public MessageObject order_del(Long orderId){
        MessageObject messageObject;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
//        criteria.andOrderIdEqualTo(orderId);
        List<User> users = userService.selectByExample(example);
        if(users.size() > 0){
            messageObject = new MessageObject(false,"此角色还拥有用户，不能直接删除");
            return messageObject;
        }
        int row = orderService.deleteByPrimaryKey(orderId);
        if(row == 1){
            messageObject = new MessageObject(true,"用户删除成功！");
        }else{
            messageObject = new MessageObject(false,"删除数据失败，请联系管理员");
        }

        return messageObject;
    }


    // 新增用户/更新用户
    @RequestMapping("/edit")
    public String edit(Model m,Long orderId){
        // 用于新增数据的时候要给用户选择的数据
        // 查询业务员
        UserExample userExample = new UserExample();
        UserExample.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andRolenameEqualTo("业务员");
        List<User> users = userService.selectByExample(userExample);
        m.addAttribute("users", users);

        // 查询所有客户
        CustomerExample customerExample = new CustomerExample();
        List<Customer> customers = customerService.selectByExample(customerExample);
        m.addAttribute("customers",customers);

        // 区间、到达区域
        List<BaseData> intervals = baseDataService.selectBaseDataByParentName("区间管理");
        m.addAttribute("intervals", intervals);

        // 付款方式
        List<BaseData> payments = baseDataService.selectBaseDataByParentName("付款方式");
        m.addAttribute("payments", payments);

        // 货运方式
        List<BaseData> freights = baseDataService.selectBaseDataByParentName("货运方式");
        m.addAttribute("freights", freights);

        // 取件方式
        List<BaseData> fetchTypes = baseDataService.selectBaseDataByParentName("取件方式");
        m.addAttribute("fetchTypes", fetchTypes);

        // 获取所有的单位
        List<BaseData> units = baseDataService.selectBaseDataByParentName("单位");
        m.addAttribute("units",units);

        // 根据orderId查询Order对象,以供修改回显
        if(orderId != null){
            Order order = orderService.selectByPrimaryKey(orderId);
            m.addAttribute("order",order);
        }
        // 查询出所有的角色，以供新增和更新管理员的时候选择
        OrderExample example = new OrderExample();
        List<Order> orders = orderService.selectByExample(example);
        m.addAttribute("orders" , orders);
        return "orderEdit";
    }



    // 新增用户
    @RequestMapping("/insert")
    @RequiresPermissions("order:insert")
    @ResponseBody
    public MessageObject insert(@RequestBody Order order){
        MessageObject messageObject = new MessageObject(false,"数据添加失败，请联系管理员！");
        int row = orderService.insert(order);
        if(row == 1){
            messageObject = new MessageObject(true,"数据新增成功！！！");
        }
        System.out.println(order.toString());
        return messageObject;
    }

    // 更新用户
    @RequestMapping("/update")
    @RequiresPermissions("order:update")
    @ResponseBody
    public MessageObject update(Order order){
        System.out.println(order+"-----------------------------");
        MessageObject messageObject = new MessageObject(false, "更新失败，请联系管理员！");
        int row = orderService.updateByPrimaryKeySelective(order);
        if(row == 1){
            messageObject = new MessageObject(true,"更新成功！！！");
        }
        return messageObject;
    }

}
