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
    <link rel="stylesheet" type="text/css" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>添加管理员 - 管理员管理 - H-ui.role v3.1</title>
    <meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
    <meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" action="${empty role ? 'role/insert.do' : 'role/update.do'}" id="userFrom" style="margin-right: 115px;">
        <!-- 隐藏域，以供修改数据使用 -->
        <input type="hidden" name="roleId" value="${role.roleId}">

        <!-- 权限permissionIds隐藏域，以供分配权限使用 -->
        <input type="hidden" name="permissionIds" id="permissionIds">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" ${!empty role ? 'disabled' : ''} value="${role.rolename}" placeholder="" id="rolename" name="rolename">
            </div>
        </div><div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色描述：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${role.remark}" placeholder="" id="remark" name="remark">
            </div>
        </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">系统权限：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
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
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
    $(function(){
        $("#userFrom").validate({
            rules:{// 校验规则
                rolename: {
                    required: true,
                    remote: {
                        url: "role/checkRolename.do",// 请求路径
                        type: "post",// 请求发送方式
                        dataType: "json",// 接收数据的格式
                        data: {// 要传递的数据
                            rolename: function(){
                                return $("#rolename").val();

                            }
                        }

                    }
                }

            },messages: {
                rolename: {
                    required: "亲！您的账号不能为空呢！！！",
                    remote: "您输入的账号已存在！请重新输入!!!"
                }
            },
            submitHandler: function(form){
                /*// 设置表单的action提交地址
                form.action = "admin/insert.do";*/
                // 调用表单的ajax提交方式
                getCheckedIds();
                console.log(11111);
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

    //定义zTree的设置
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    //获取选中的复选框的值
    function getCheckedIds(){
        //获取zTree树对象
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");

        //获取复选框选中的节点
        var nodes = treeObj.getCheckedNodes(true);
        console.log(nodes);


        /*
            获取每一个节点的 permissionId 的值，最终拼接成字符串,使用逗号作为分隔符
            如 ： 1,3,8,10...

        */
        //声明一个数组，添加permissionId
        var permissionIdArr = [];
        for(var i = 0 ;i<nodes.length;i++){

            var node = nodes[i];
            console.log("1231"+node);
            //获取每个数据的id
            var permissionId = node.permissionId;
            //将一个个permissionId添加到数组
            permissionIdArr.push(permissionId);
        }


        /*
        使用js数组的join方法或者toString方法将数组转换成字符串
            var str1 = 数组.join("自定义分隔符")
        */
        //var permissionIds = permissionIdArr.join(",");
        var permissionIds = permissionIdArr.toString();//1,8,9,20...
        console.log("permissionIds :",permissionIds);
        //将权限的id字符设置到角色标的隐藏域中
        $("#permissionIds").val(permissionIds);
    }

    //异步加载完毕以后回调的函数
    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
        //获取zTree树对象
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        /*
            回显思路

            1， 把当前角色的权限id 拆分成数组
            var permissionIds = "";
    	//模拟数据
    	var permissionIds = "10,36,37,38,39,40,54,55";

    	var permissionIdArr = permissionIds.split(",");

    	2, 再循环数据，分别通过值获取对应的节点
    		zTree可以根据 指定属性的值获取对应的节点
    		如： zTree的id=10，那么久可以找到对应的节点

    	3，让其对应找到的节点选中即可
    		达到回显目的

    */
        var permissionIds = "${role.permissionIds}";

        var permissionIdArr = permissionIds.split(",");

        for(var i = 0;i<permissionIdArr.length;i++){
            //获取具体的permissionId值
            var permissionId = permissionIdArr[i];
            console.log("permissionId["+i+"]",permissionId);

            //zTree可以根据 指定属性的值获取对应的节点
            //通过zTree数据 id的属性对应的值获取对应的节点

            var node = treeObj.getNodeByParam("id", permissionId, null);
            //console.log(node);
            if (null!=node){

                treeObj.checkNode(node, true, false);

            }
            //让其选中的节点选中
        }
    };

    //定义zTree的设置
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url:"permission/getAllPermissions.do",
            dataFilter: filter
        },
        //异步加载完毕以后回调的函数
        callback: {
            onAsyncSuccess: zTreeOnAsyncSuccess
        }

    }

    //重新格式化异步加载的json数据
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].id = childNodes[i].permissionId;
            childNodes[i].pId = childNodes[i].parentId;
            childNodes[i].open = true;
        }
        return childNodes;
    }

    $(function(){
        //初始化zTree
        $.fn.zTree.init($("#treeDemo"), setting);
    })

</script>

</body>
</html>
