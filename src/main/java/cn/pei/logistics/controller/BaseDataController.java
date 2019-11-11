package cn.pei.logistics.controller;

import cn.pei.logistics.pojo.MessageObject;
import cn.pei.logistics.pojo.BaseData;
import cn.pei.logistics.pojo.BaseDataExample;
import cn.pei.logistics.pojo.BaseDataExample.Criteria;
import cn.pei.logistics.service.BaseDataService;
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
@RequestMapping("/baseData")
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/baseDataPage")
    @RequiresPermissions("basicData:basicDatapage")
    public String adminPage(){
        return "baseDataPage";
    }

    // 用户数据查询
    @RequestMapping("/list")
    @RequiresPermissions("basicData:list")
    @ResponseBody
    public PageInfo<BaseData> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize, String keyword){
        // 设置传入的页码和页数
        PageHelper.startPage(pageNum,pageSize);
        // 创建条件查询对象
        BaseDataExample example = new BaseDataExample();
        // 通过增强字符串类来判断是否为空,如果不为空就进去
        if(StringUtils.isNotBlank(keyword)){
            // 创建条件限制对象
            Criteria criteria = example.createCriteria();
            // 账号和真实姓名模糊查询
            criteria.andBaseNameLike("%"+keyword+"%");
        }
        List<BaseData> baseDatas = baseDataService.selectByExample(example);

        PageInfo<BaseData> pageInfo = new PageInfo(baseDatas);

        return pageInfo;
    }


    // 用户数据删除
    @RequestMapping("/baseData_del")
    @RequiresPermissions("basicData:delete")
    @ResponseBody
    public MessageObject baseData_del(Long baseId){
        System.out.println("----0----"+baseId);
        MessageObject messageObject;
        BaseDataExample example = new BaseDataExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(baseId);
        // 找出当前编号的的父id
        List<BaseData> baseDatas = baseDataService.selectByExample(example);
        if(baseDatas.size() > 0){
            messageObject = new MessageObject(false,"数据删除失败了，此权限有子权限，不可删除！");
            return messageObject;
        }
        int row = baseDataService.deleteByPrimaryKey(baseId);
        if(row == 1){
            messageObject = new MessageObject(true,"用户删除成功！");
        }else{
            messageObject = new MessageObject(false,"用户删除失败@。请联系管理员！！！");
        }

        return messageObject;
    }


    // 新增用户/更新用户
    @RequestMapping("/edit")
    public String edit(Model m,Long baseDataId){
        // 根据baseDataId查询BaseData对象,以供修改回显
        if(baseDataId != null){
            BaseData baseData = baseDataService.selectByPrimaryKey(baseDataId);
            m.addAttribute("baseData",baseData);
        }
        // 查询出所有的角色，以供新增和更新管理员的时候选择
        BaseDataExample example = new BaseDataExample();
        List<BaseData> baseDatas = baseDataService.selectByExample(example);
        m.addAttribute("baseDatas" , baseDatas);
        return "baseDataEdit";
    }

    // 检查用户名是否存在
    @RequestMapping("/checkBaseDataname")
    @ResponseBody
    public Boolean checkBaseDataname(String baseName){
        BaseDataExample example = new BaseDataExample();
        Criteria criteria = example.createCriteria();
        criteria.andBaseNameEqualTo(baseName);
        List<BaseData> baseData = baseDataService.selectByExample(example);
        // 如果有对象则user的长度大于0，表示存在
        return baseData.size() > 0 ? false : true;
    }


    // 新增用户
    @RequestMapping("/insert")
    @RequiresPermissions("basicData:insert")
    @ResponseBody
    public MessageObject insert(BaseData baseData){
        // 设置当前日期
        MessageObject messageObject = new MessageObject(false,"数据添加失败，请联系管理员！");
        int row = baseDataService.insert(baseData);
        if(row == 1){
            messageObject = new MessageObject(true,"数据新增成功！！！");
        }
        return messageObject;
    }

    // 更新用户
    @RequestMapping("/update")
    @RequiresPermissions("basicData:update")
    @ResponseBody
    public MessageObject update(BaseData baseData){
        System.out.println(baseData+"-----------------------------");
        MessageObject messageObject = new MessageObject(false, "更新失败，请联系管理员！");
        int row = baseDataService.updateByPrimaryKeySelective(baseData);
        if(row == 1){
            messageObject = new MessageObject(true,"更新成功！！！");
        }
        return messageObject;
    }


    // 获取json数据
    @RequestMapping("/getAllBaseDatas")
    @ResponseBody
    public List<BaseData> getAllBaseDatas(){
        BaseDataExample example = new BaseDataExample();
        List<BaseData> baseDatas = baseDataService.selectByExample(example);
        return baseDatas;
    }

}
