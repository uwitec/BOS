<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
	
	<SCRIPT type="text/javascript">
	
		var setting = {
			callback: {
				onClick: function(event, treeId, treeNode, clickFlag){
					
					 if(treeNode.page!=undefined && treeNode.page!=""){
						var flag = $('#tt').tabs('exists',treeNode.name);
						if (flag) {
							$('#tt').tabs('select',treeNode.name);
						} else {
							 $('#tt').tabs('add',{ 

								title:treeNode.name, 

								//  content 内容 添加嵌套页面<iframe>
								content:'<div style="width:100%;height:100%;overflow:hidden;">'
										+ '<iframe src="'
										+ treeNode.page
										+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>',
										
								closable:true, 

							});
						}
					}  
				}
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		$(function(){
			$.post("${pageContext.request.contextPath}/json/menu.json",function(data){
				$.fn.zTree.init($("#treeDemo"), setting, data);
			});
		});
		
	</SCRIPT>
	
</head>
<body class="easyui-layout"> 

<div data-options="region:'north'" style="height:120px;background:url(../images/header_bg.png) no-repeat right">

	<div>
		<img src="../images/logo.png" style="position: absolute;top:30px">
	</div>
	<div id="sessionInfoDiv" style="position: absolute;right: 5px;top: 10px">
		[<strong>超级管理员</strong>],欢迎登陆！您的登录地址是：<strong>192.168.0.0.1</strong>
	</div>
	<div style="position: absolute;right: 5px;bottom: 10px ">
		<a href="#" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
	</div>
	<div id="layout_north_pfMenu" style="width: 120px; display: none;">
		<div onclick="changeTheme('default');">default</div>
		<div onclick="changeTheme('gray');">gray</div>
		<div onclick="changeTheme('black');">black</div>
		<div onclick="changeTheme('bootstrap');">bootstrap</div>
		<div onclick="changeTheme('metro');">metro</div>
	</div>
	<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
		<div onclick="editPassword();">修改密码</div>
		<div onclick="showAbout();">联系管理员</div>
		<div class="menu-sep"></div>
		<div onclick="logoutFun();">退出系统</div>
	</div>
</div> 

<div data-options="region:'south'" style="height:80px;"></div> 

<div data-options="region:'west',title:'导航菜单',split:true" style="width:200px;">

	<div id="aa" class="easyui-accordion"  data-options="fit:true"> 
	
		<div title="基本功能" data-options="selected:true" style="overflow:auto;"> 
		
				<ul id="treeDemo" class="ztree"></ul>
		
		</div> 
	
		<div title="系统管理" data-options="" style="padding:10px;"> 
		
			<ul id="adminMenu" class="ztree"></ul> 
		
		</div> 
	
	</div> 

</div> 

<div id="tt" data-options="region:'center'" class="easyui-tabs">

	<div title="消息中心" id="subWarp" style="padding:20px;">

		<iframe src="panel.jsp" style="width:100%;height:100%;border:0;"></iframe>

	</div> 

</div> 

</body> 

</html>