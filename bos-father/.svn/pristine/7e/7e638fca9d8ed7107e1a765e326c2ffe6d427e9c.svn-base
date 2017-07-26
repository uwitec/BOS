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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		$('#queryStaffWindow').window("open");
	}
	
	function doDelete(){
		var arr = $('#grid').datagrid("getSelections");
		if (arr==null||arr.length==0) {
			return;
		}
		var ids = new Array();
		for(var i=0; i<arr.length; i++){
			ids.push(arr[i].id);
		}
		var idstr = ids.join(",");
		$.post(
			'${pageContext.request.contextPath}/staff/delBatch',
			{ids:idstr},
			function(data){
				if (data) {
					alert("弃用成功");
					$("#grid").datagrid("clearSelections");
					$("#grid").datagrid("reload");
				} else {
					alert("启用成功");
				}
			});
	}
	
	function doRestore(){
		var arr = $('#grid').datagrid("getSelections");
		if (arr==null||arr.length==0) {
			return;
		}
		var ids = new Array();
		for(var i=0; i<arr.length; i++){
			ids.push(arr[i].id);
		}
		var idstr = ids.join(",");
		$.post(
			'${pageContext.request.contextPath}/staff/startBatch',
			{ids:idstr},
			function(data){
				if (data) {
					alert("启用成功");
					$("#grid").datagrid("clearSelections");
					$("#grid").datagrid("reload");
				} else {
					alert("弃用成功");
				}
			});
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center',
		styler:setstyler
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所属单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [3,4,5],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/staff/pageStaffList",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
			
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		// 查询取派员窗口
		$('#queryStaffWindow').window({
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 300,
	        resizable:false
	    });
		
		//添加下拉列表框
		$("input[name='standard']").combobox({
			url:'${pageContext.request.contextPath}/standard/listStandardName',
			valueField:'name',
			textField:'name',
			panelHeight:100
		});
		
		
		//提交表单
		$("#save").click(function(){
			if($("#saveStaffForm").form("validate")){
				$("#saveStaffForm").submit();
				$("#saveStaffForm").window("close");
			}
		});

		//查询
		$("#query").click(function() {
			var params = {"name":$("#qname").val(),"telephone":$("#qtel").val(),
					"haspda":$("input[name='qhaspda']:checked").val(),"standard":$("#qstandard").combobox("getValue")};
			$("#grid").datagrid('load',params);
			$("#queryStaffWindow").window("close");
		});
		$("#queryAll").click(function() {
			$("#grid").datagrid('load');
			$("#queryStaffWindow").window("close");
		});
		
	});
	//验证
	$.extend($.fn.validatebox.defaults.rules, { 
		tel: { 
			validator: function(value, param){ 
				var reg = /^1[3|4|5|7|8]\d{9}$/;
				return reg.test(value); 
			}, 
			message: '手机格式不正确' 
		},
		uniquetel:{
			validator:function(value,param){
				var flag;
				$.ajax({
					url:'${pageContext.request.contextPath}/staff/validStafftel?id='+$("#staffid").val(),
					type:'POST',
					data:{telephone:value},
					timeout:3000,
					async:false,
					success:function(data){
						if (data) {
							flag = true;
						} else {
							flag = false;
						}
					}
				});
				return flag;
			},
			message: '手机号已被注册'
		}
	}); 

	function doDblClickRow(rowIndex,rowData){
		$("#addStaffWindow").window("open");
		$("#saveStaffForm").form("load",rowData);
	}
	
	
	function setstyler(value,row,index) {
		if (value.match(9)) {
			return 'background-color:#FFFBDC;color:green;'; 
		}
	}
	
	function clearData() {
		$("#queryStaffWindow").form("clear");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:void(0);" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="saveStaffForm" action="${pageContext.request.contextPath }/staff/save" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<!-- <tr>
						<td>取派员编号</td>
						<td><input type="text"  name="id" class="easyui-validatebox" data-options="validType:'uniqueId'"/></td>
					</tr> -->
					<tr>
						<td>姓名</td>
						<td><input type="hidden" name="id" id="staffid">
						<input type="text"  name="name" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text"  name="telephone" class="easyui-validatebox" data-options="required:true,validType:'uniquetel'"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" id="qstation" name="station" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>有无PDA</td>
						<td>
							<input type="radio" name="haspda" value="1"/>有 
							<input type="radio" name="haspda" value="0"/>无
						</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text"  name="standard" class="easyui-validatebox" data-options="editable:false"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<!-- 查询window -->
	<div class="easyui-window" title="条件查询" id="queryStaffWindow" data-options="onBeforeClose:clearData" 
	collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="query" icon="icon-search" href="javascript:void(0);" class="easyui-linkbutton" plain="true" >查询</a>
				<a id="queryAll" icon="icon-search" href="javascript:void(0);" class="easyui-linkbutton" plain="true" >查询所有</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="queryStaffForm" action="#" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员查询</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" id="qname"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" id="qtel"/></td>
					</tr>
					<tr>
						<td>有无PDA</td>
						<td>
							<input type="radio" name="qhaspda" value="1"/>有 
							<input type="radio" name="qhaspda" value="0"/>无
						</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" id="qstandard" name="standard" class="easyui-validatebox" data-options="editable:false"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>	