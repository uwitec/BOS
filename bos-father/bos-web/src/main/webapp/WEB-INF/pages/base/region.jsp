<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 一键上传组件js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/oneclickupload/jquery.ocupload-1.1.2.js"></script>
	
<script type="text/javascript">
	function doAdd(){
		$('#addRegionWindow').window("open");
	}
	
	function doView(){
		$('#addRegionWindow').window("open");
	}
	
	function doQuery(){
		$("#queryRegionWindow").window("open");
	}
	
	//工具栏
	var toolbar = [{
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doQuery
	},{
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [3,5,10],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/region/pageRegion",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加、修改区域窗口
		$('#addRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询区域窗口
		$('#queryRegionWindow').window({
	        title: '条件查询区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 300,
	        resizable:false
	    });
		
		//一键上传
		$("#button-import").upload({
			name:'upload',
			action:'${pageContext.request.contextPath}/region/oneclickupload',
			enctype:'multipart/form-data',
			onSelect:function(){
				this.autoSubmit = false;
				var re = /^.+\.xls|.+\xlsx$/;
				if (re.test(this.filename())) {
					this.submit();
				} else {
					alert("只能上传xls或xlsx的文件");
				}
			},
			//回调函数
			onComplete:function(data){
				if (data) {
					$.messager.alert("恭喜","上传成功","info");
				} else {
					$.messageer.alert("错误","上传失败","error");
				}
			}
		});
		
		//搜索框
		$('#ss').searchbox({
			searcher : function(value, name) {
				var j = {};
				j[name] = value;  //动态设置json的key**
				$("#grid").datagrid("load", j);
			},
			menu : '#mm',
			prompt : 'Please Input Value'
		});

		//保存
		$("#save").click(function() {
			if ($("#updateRegionForm").form("validate")) {
				$("#updateRegionForm").submit();
				$("#updateRegionForm").window("close");
			}
		});
		//条件查询 
		$("#query").click(function() {
			var params = {
				"province" : $("#qprovince").val(),
				"city" : $("#qcity").val(),
				"district" : $("qdistrict").val()
			};
			$("#grid").datagrid("load", params);
			$("#queryRegionWindow").window("close");
		});
	});

	function doDblClickRow(rowIndex, rowData) {
		$('#addRegionWindow').window("open");
		$('#updateRegionForm').form("load", rowData);
	}
	
	//关闭widow 清除数据
	function cleardata() {
		$("#addRegionWindow").form("clear");
	}
	//关闭widow 清除数据
	function cleardata1() {
		$("#queryRegionWindow").form("clear");
	}

	//验证
	$.extend(
		$.fn.validatebox.defaults.rules,
		{
			uniqueId : {
				validator : function(value, param) {
					var flag;
					$
							.ajax({
								url : '${pageContext.request.contextPath}/Region/validRegionId',
								type : 'POST',
								data : {
									id : value
								},
								timeout : 3000,
								async : false,
								success : function(d) {
									if (d) {
										flag = true;
									} else {
										flag = false;
									}
								}
							});
					if (flag) {
						$("#id").removeClass('validatebox-invalid');
					}
					return flag;
				},
				message : '区域编号已存在'
			},
			uniquePostcode : {
				validator : function(value, param) {
					var flag;
					$.ajax({
							url : '${pageContext.request.contextPath}/Region/validRegionPostcode',
							type : 'POST',
							data : {
								postcode : value
							},
							timeout : 3000,
							async : false,
							success : function(data) {
								if (data) {
									flag = true;
								} else {
									flag = false;
								}
							}
						});
					return flag;
				},
				message : '邮政编码已存在'
			}
		});
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" border="false">
		<input id="ss"></input>
		<div id="mm" style="width: 120px">
			<div data-options="name:'shortcode'">地区简码</div>
			<div data-options="name:'postcode'">邮编</div>
		</div>
	</div>
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow" data-options="onBeforeClose:cleardata"
	collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="updateRegionForm" action="${pageContext.request.contextPath }/Region/updateRegion" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>区域编号</td>
						<td><input type="text" name="id" class="easyui-validatebox" data-options="required:true,validType:'uniqueId'"/></td>
					</tr>
					<tr>
						<td>省</td>
						<td>
						<input type="text" name="province" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" data-options="required:true,validType:'uniquePostcode'"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" /></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<!-- 条件查询 -->
	<div class="easyui-window" title="条件查询" id="queryRegionWindow" data-options="onBeforeClose:cleardata1"
	collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="query" icon="icon-search" href="#" class="easyui-linkbutton" plain="true" >查询</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="queryRegionForm" action="#" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">条件查询</td>
					</tr>
					<tr>
						<td>省份</td>
						<td>
						<input type="text" name="province" id="qprovince"/></td>
					</tr>
					<tr>
						<td>城市</td>
						<td><input type="text" name="city" id="qcity"/></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" name="district" id="qdistrict"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</body>
</html>