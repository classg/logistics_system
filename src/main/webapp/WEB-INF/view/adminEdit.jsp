<%--
  Created by IntelliJ IDEA.
  User: 71516
  Date: 2019/10/28
  Time: 17:08
  To change this template use File | Settings | File Templates.
  用于新增和更新用户数据的表单
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    /* http://localhost:8080/logistics/ */
%>
<!DOCTYPE HTML>
<html>
<head>
    <!-- 设置页面的 基本路径，页面所有资源引入和页面的跳转全部基于 base路径 -->
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5shiv.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>添加管理员 - 管理员管理 - H-ui.admin v3.1</title>
    <meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
    <meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" action="${empty user ? 'admin/insert.do' : 'admin/update.do'}" id="userFrom" style="margin-right: 115px;">
        <input type="hidden" name="userId" value="${user.userId}">

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>账号：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" ${!empty user ? 'disabled' : ''} value="${user.username}" placeholder="" id="username" name="username">
            </div>
        </div><div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${user.realname}" placeholder="" id="realname" name="realname">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>初始密码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="password" class="input-text" autocomplete="off" value="" placeholder="密码" id="password" name="password">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>确认密码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="password" class="input-text" autocomplete="off"  placeholder="确认新密码" id="password2" name="password2">
            </div>
        </div>

        <%--<div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
            <div class="formControls col-xs-8 col-sm-9 skin-minimal">
                <div class="radio-box">
                    <input name="sex" type="radio" id="sex-1" checked>
                    <label for="sex-1">男</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="sex-2" name="sex">
                    <label for="sex-2">女</label>
                </div>
            </div>
        </div>--%>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">角色：</label>
            <div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="roleId" size="1">
				<option value="-1">请选择</option>
                <c:forEach items="${roles}" var="role">
                    <option value="${role.roleId}" ${user.roleId eq role.roleId ? 'selected' : ''}>${role.rolename}</option>
                </c:forEach>
			</select>
			</span> </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
    $(function(){
        $("#userFrom").validate({
            rules:{// 校验规则
                username: {
                    required: true,
                    minlength: 4,
                    <c:if test="${empty user}">
                    remote: {
                        url: "admin/checkUsername.do",// 请求路径
                        type: "post",// 请求发送方式
                        dataType: "json",// 接收数据的格式
                        data: {// 要传递的数据
                            username: function(){
                                console.log(username);
                                return $("#username").val();
                            }
                        }
                    }
                    </c:if>
                },
                realname: {
                    required: true,
                    minlength: 2,
                    isChinese: true,
                },
                password: {
                    required: true,
                    rangelength: [5,16]
                },
                password2: {
                    equalTo: "#password"
                },
                roleId: {
                    min: 1
                }
            },messages: {
                username: {
                    required: "亲！您的账号不能为空呢！！！",
                    minlength: "亲！您的账号不能少于4位呢",
                    remote: "您输入的账号已存在！请重新输入!!!"
                },
                realname: {
                    required: "亲！您的真实姓名不能为空！！！",
                    minlength: "亲！您的真实姓名不能少于2位呢",
                    isChinese: "亲！请您输入你的中文名！"
                },
                password: {
                    required: "亲！您的密码不能为空！！！",
                    rangelength: "亲！请输入5到16位的密码哈"
                },
                password2: {
                    equalTo: "亲！您的初始化密码和确认密码不同呢！"
                },
                roleId: {
                    min: "亲！请选择角色哈"
                }
            },
            submitHandler: function(form){
                /*// 设置表单的action提交地址
                form.action = "admin/insert.do";*/
                // 调用表单的ajax提交方式
                $(form).ajaxSubmit(function(data){
                    layer.msg(data.msg,{icon: 1,time: 1000},function(){
                        if(data.flag == true){
                            // 删除成功，刷新父页面表格
                            parent.refreshTable();
                            // 关闭父类弹出的所有层
                            parent.layer.closeAll();
                        }
                    })
                })
            }
        })
    });
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
