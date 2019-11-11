package cn.pei.logistics.realm;

import cn.pei.logistics.pojo.User;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.codehaus.plexus.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        /**
         * 清除掉Shiro记录的上一个请求路径，认证成功以后跳转到
         * <property name="successUrl" value="/index.do"></>
         */
        // 方式一：开发者自己手动清除
        // 1、获取session
        /*Session session = subject.getSession(false);
        if(session != null){
            // 清除shiro共享的上一次地址：//shiroSavedRequest
            session.removeAttribute(WebUtils.SAVED_REQUEST_KEY);
        }*/

        // 方式二：调用WebUtils工具类中写好的清除的方法
        WebUtils.getAndClearSavedRequest(request);
        return super.onLoginSuccess(token, subject, request, response);
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 从请求中获取Shiro的主体
        Subject subject = getSubject(request, response);
        // 从主体重获取Shiro框架的session
        Session session = subject.getSession();
        // 如果主体没有认证（sesison中认证）并且主体已经设置了记住我选项
        if(!subject.isAuthenticated() && subject.isRemembered()) {
            // 获取主体的身份（从记住我的Cookie中获取）
            User principal = (User) subject.getPrincipal();
            System.out.println(principal+"--------0----------");
            // 将身份认证信息共享到Session中
            session.setAttribute("principal",principal);
        }
//        return super.isAccessAllowed(request, response, mappedValue);
        return subject.isAuthenticated() || subject.isRemembered();
    }


    // 从reques请求对象中国区验证码表单数据
    /**
     * 如果有：说明用户在做登录操作，要进行验证码判断
     * 思路：
     *      1、获取用户提交的验证码
     *      2、从Session获取共享的写入图片的随机数
     *   如果判断失败，跳转到登录页面，病共享错误信息
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        // 判断用户提交的验证码
        String verifyCode = req.getParameter("verifyCode");
        System.out.println("verifyCode : "+verifyCode);
        // 获取Session中的随机数
        String rand = (String) req.getSession().getAttribute("rand");
        System.out.println("rand : "+rand);
        // 判断都不为空的时候才比较
        if(StringUtils.isNotBlank(verifyCode) && StringUtils.isNotBlank(rand)) {
            // 对比是否相等，不区分大小写
            if(!verifyCode.equalsIgnoreCase(rand)){
                // 跳转到登录页面
                System.out.println("验证码错了才会进来这个页面的");
                req.setAttribute("errorMsg","亲！验证码不正确");
                req.getRequestDispatcher("/login.jsp").forward(req,response);
                return false;
            }
        }
        return super.onAccessDenied(request, response);
    }
}
