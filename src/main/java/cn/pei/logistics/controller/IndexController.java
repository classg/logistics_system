package cn.pei.logistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @PackageName:cn.pei.logistics.controller
 * @ClassName:IndexController
 * @Description: 主页
 * @author:CJ
 * @date:2019/10/27 17:32
 */

@Controller
public class IndexController {

    /*
     * @Author 才捷
     * @Description 显示主页面
     * @Date 18:50 2019-10-27  18:50:12
     * @Param  * @param
     * @return java.lang.String
     **/
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    /*
     * @Author 才捷
     * @Description 显示欢迎页面
     * @Date 18:51 2019-10-27  18:51:21
     * @Param  * @param
     * @return java.lang.String
     **/
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

}
