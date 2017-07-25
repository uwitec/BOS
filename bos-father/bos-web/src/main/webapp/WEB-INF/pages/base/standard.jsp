<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>取派标准</title>
		<!-- 导入jquery核心类库 -->
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
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				//datagrid
				$("#grid").datagrid({
					columns:columns,  //定义列
					idField:'id',
					url:"${pageContext.request.contextPath}/standard/listStandard", //ajax请求数据 
					toolbar:toolbar, //定义工具栏
					pagination:true, //显示分页栏
					pageList:[3,4,5],
					rownumbers:true, //显示列号
					iconCls:'icon-forward',
					fit:true,
					striped:true,
					border:false,
					onDblClickRow:function(rowIndex, rowData){
						$('#editStandardWindow').window("open");
						$("#editStandardForm").form("load",rowData);
					}
				});
				
				//save提交
				$("#save").click(function(){
					var flag = $("#addStandardForm").form("validate");
					if (flag) {
						$("#addStandardForm").submit();
					}
				});
				$("#edit").click(function(){
					var flag = $("#editStandardForm").form("validate");
					if (flag) {
						$("#editStandardForm").submit();
					}
				});
			});
			
			
			//列
			var columns = [[
					{field:'id',checkbox:true},
					{field:'name',title:'标准名称',width:'120',align:'center'},
					{field:'minweight',title:'最小重量',width:'120',align:'center'},
					{field:'maxweight',title:'最大重量',width:'120',align:'center'},
					{field:'minlength',title:'最小长度',width:'120',align:'center'},
					{field:'maxlength',title:'最大长度',width:'120',align:'center'},
					{field:'operator',title:'操作人',width:'120',align:'center'},
					{field:'operationtime',title:'操作时间',width:'120',align:'center'},
					{field:'operatorcompany',title:'操作单位',width:'120',align:'center'},
					{field:'deltag',title:'是否作废',width:'120',align:'center',
						formatter:function(value,row,index){ // 列属性设置
						if (value==1) {
							return "已启用";
						} else {
							return "已弃用";
						}
					}}]];
			//工具栏
			var toolbar = [
			        {id:'btn-add',text:'增加',iconCls:'icon-add',handler:function(){
			        	$('#addStandardWindow').window("open");
			        }},
			        {id:'btn-edit',text:'修改',iconCls:'icon-edit',handler:function(){
			        	$('#editStandardWindow').window("open");
			        	$("#editStandardForm").form("load",$("#grid").datagrid("getSelected"));
			        }},
			        {id:'btn-delete',text:'作废',iconCls:'icon-cancel',handler:function(){
			        	//弃用
						var arr = $("#grid").datagrid("getSelections");
						if(arr==null||arr.length==0){
							alert("请先选择行");
						} else {
							var ids = new Array();
							for(var i=0;i<arr.length;i++){
								ids.push(arr[i].id);
							}
							var idstring = ids.join(",");
							$.post(
								'${pageContext.request.contextPath}/standard/delBatch',
								{ids:idstring},
								function(data){
									if (data) {
										alert("作废成功");
										$("#grid").datagrid("clearSelections");
										$("#grid").datagrid("reload");
									} else {
										alert("作废失败");
									}
								});
						}
			        }},
			        {id:'btn-restore',text:'还原',iconCls:'icon-save',handler:function(){
			        	//还原
						var arr = $("#grid").datagrid("getSelections");
						if(arr==null||arr.length==0){
							alert("请先选择行");
						} else {
							var ids = new Array();
							for(var i=0;i<arr.length;i++){
								ids.push(arr[i].id);
							}
							var idstring = ids.join(",");
							$.post(
								'${pageContext.request.contextPath}/standard/startBatch',
								{ids:idstring},
								function(data){
									if (data) {
										alert("启用成功");
										$("#grid").datagrid("clearSelections");
										$("#grid").datagrid("reload");
									} else {
										alert("启用失败");
									}
								});
						}
			        }}];
			
			function clearFormData(){
				$("#editStandardForm").form("clear");//清除表单数据
				return true;
			}
		</script>
	</head>

<body class="easyui-layout" >
	<!-- datagrid -->
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	<!-- addwindow -->
	<div id="addStandardWindow" title="收派标准添加" class="easyui-window" style="width:400px;top:100px;left:300px" modal="true" 
	closed="true" collapsible="false" minimizable="false" maximizable="false">
		
		<div region="center" style="overflow: auto;padding:5px;" border="false" fit="true">
			<form id="addStandardForm" method="post" action="${pageContext.request.contextPath }/standard/save">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2" align="center">收派标准信息</td>
					</tr>
					<tr>
						<td >收派名称</td>
						<td>
							<!-- <input type="hidden" name="id"> -->
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true">
						</td>
					</tr>
					<tr >
						<td >最小重量(kg)</td>
						<td>
							<input type="text" name="minweight" class="easyui-numberbox" data-options="min:1,required:true">
						</td>
					</tr>
					<tr >
						<td >最大重量(kg)</td>
						<td>
							<input type="text" name="maxweight" class="easyui-numberbox" data-options="max:200,required:true">
						</td>
					</tr>
					<tr >
						<td >最小长度(cm)</td>
						<td>
							<input type="text" name="minlength" class="easyui-numberbox" data-options="min:10,required:true">
						</td>
					</tr>
					<tr>
						<td >最大长度 (cm)</td>
						<td>
							<input type="text" name="maxlength" class="easyui-numberbox" data-options="max:200,required:true">
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div region="sourth" style="height: 31px;overflow: hidden;">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain=true >保存</a>
			</div>
		</div>
	</div>
	<!-- 修改window -->
	<div id="editStandardWindow" title="收派标准修改" class="easyui-window" style="width:400px;top:100px;left:300px" modal="true" 
	closed="true" collapsible="false" minimizable="false" maximizable="false" data-options="onBeforeClose:clearFormData">
		
		<div region="center" style="overflow: auto;padding:5px;" border="false" fit="true">
			<form id="editStandardForm" method="post" action="${pageContext.request.contextPath }/standard/save">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2" align="center">收派标准信息</td>
					</tr>
					<tr>
						<td >收派名称</td>
						<td>
							<input type="hidden" name="id"> 
							<input type="hidden" name="deltag"> 
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true">
						</td>
					</tr>
					<tr >
						<td >最小重量(kg)</td>
						<td>
							<input type="text" name="minweight" class="easyui-numberbox" data-options="min:1,required:true">
						</td>
					</tr>
					<tr >
						<td >最大重量(kg)</td>
						<td>
							<input type="text" name="maxweight" class="easyui-numberbox" data-options="max:200,required:true">
						</td>
					</tr>
					<tr >
						<td >最小长度(cm)</td>
						<td>
							<input type="text" name="minlength" class="easyui-numberbox" data-options="min:1,required:true">
						</td>
					</tr>
					<tr>
						<td >最大长度 (cm)</td>
						<td>
							<input type="text" name="maxlength" class="easyui-numberbox" data-options="max:200,required:true">
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		<div region="sourth" style="height: 31px;overflow: hidden;">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain=true >修改</a>
			</div>
		</div>
	</div>
</body>

</html>