package cn.pei.logistics.realm;

import cn.pei.logistics.pojo.User;
import cn.pei.logistics.service.PermissionService;
import cn.pei.logistics.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    // 认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = null;
        try {
            user = userService.selectByUsername(username);
        } catch (Exception e){
        e.printStackTrace();
    }

        if(user == null) {
            return null;
        }
        // 获取密码
        String password = user.getPassword();
        // 获取盐
        String salt = user.getSalt();

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, password, ByteSource.Util.bytes(salt), this.getName());

        return authenticationInfo;
    }





    // 权限方法
    /**
     * 自定义授权方法
     * --------------------
     * 1、获取身份的角色的权限id
     * User user = principals.getPrinmaryPrincipal();
     * String roleId = user.getPermissionIds;// 1,3,5,6,9...
     *
     * 2、切割权限id字符串获取每一个权限的id值
     *
     * 3、根据每一个权限的id值获取对应的权限表达式
     * List<String> permission = permissionService.serlectPermissionByIds();
     * 如：
     *      user:list
     *      user:insert
     *      role:delete
     *      role:update
     *      ...
     *
     * 4、创建授权信息对象
     * SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo()
     *
     * 5、将第三部查询出的权限表达式添加到权限信息中
     * authorizationInfo.addStringPermission("user:list")
     *
     * 6、程序运行时候会根据有权限判断地方获取权限表达式和设置Shiro框架权限表达式数据进行对比
     * 有：   放行
     * 没有：  不放行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前主身份（认证的User对象）
        User user = (User) principals.getPrimaryPrincipal();
        System.out.println("我是授权方法的User"+user);
        // 获取身份的角色的权限id
        String permissionIds = user.getPermissionIds();
        System.out.println("我是授权方法的权限的ids"+permissionIds);
        // 通过，号分割，返回一个数组
        String[] split = permissionIds.split(",");
        // 创建List<Long>
        List<Long> permissionIdsList = new ArrayList<>();
        for (String permissionId : split) {
            permissionIdsList.add(Long.valueOf(permissionId));
        }
        // 根据每一个权限的id值获取对应的权限表达式
        List<String> list = permissionService.serlectPermissionByIds(permissionIdsList);
        System.out.println("我是授权方法的权限表达式 ："+list);
        // 创建授权信息对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 将第三步查询出的权限表达式添加到权限信息中
        authorizationInfo.addStringPermissions(list);
        return authorizationInfo;
    }


}
