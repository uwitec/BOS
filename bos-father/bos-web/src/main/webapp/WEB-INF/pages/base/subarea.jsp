<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理分区</title>
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
<!-- 一键上传组件js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/oneclickupload/jquery.ocupload-1.1.2.js"></script>
<script type="text/javascript">
	function doAdd(){
		$('#addSubareaWindow').window("open");
	}
	
	function doEdit(){
		$("#grid").datagrid("endEdit",editIndex);
	}
	
	function doDelete(){
		$("#grid").datagrid("cancelEdit",editIndex);
	}
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	function doexport(){
		$("#querySubareaForm").submit();
	}
	
	var editIndex;
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-edit',	
		text : '保存',
		iconCls : 'icon-edit',
		handler : doEdit
	},{
		id : 'button-delete',
		text : '取消',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doexport
	}];
	// 定义列
	var columns = [ [ {
		field : 'sid',
		title : '分拣编号',
		width : 120,
		align : 'center'
		
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			if(row.region!=null){
			return row.region.province;
				
			}
		}
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			if(row.region!=null){
				
			return row.region.city;
			}
		}
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			if(row.region!=null){
			return row.region.district;
				
			}
		}
	}, {
		field : 'addresskey',
		title : '关键字',
		width : 120,
		align : 'center',
		editor:{
			type:'validatebox',
			options:{
				required:true
			}
		}
	}, {
		field : 'startnum',
		title : '起始号',
		width : 100,
		align : 'center',
		editor:{
			type:'validatebox',
			options:{
				required:true
			}
		}
	}, {
		field : 'endnum',
		title : '终止号',
		width : 100,
		align : 'center',
		editor:{
			type:'validatebox',
			options:{
				required:true
			}
		}
	} , {
		field : 'single',
		title : '单双号',
		width : 100,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "单";
			}else {
				return "双";
			}
		},
		editor:{
			type:'validatebox',
			options:{
				required:true
			}
		}
	} , {
		field : 'position',
		title : '位置',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [2,3,4],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/subarea/pageQuery",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow,
			onAfterEdit : afterEdit
		});
		
		// 添加、修改分区
		$('#addSubareaWindow').window({
	        title: '添加修改分区',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		//查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		//查询
		$("#btn").click(function(){
			var params = $("#querySubareaForm").serializeJson();
			$("#grid").datagrid('load',params);
			$('#searchWindow').window("close");
		});
		
		//保存
		$("#save").click(function(){
			if($("#saveSubareaForm").form("validate")){
				$("#saveSubareaForm").submit();
				$("#addSubareaWindow").window("close");
			}
		});
		
	 	//一键导入
		$("#button-import").upload({
			name:'upload',
			action:'${pageContext.request.contextPath}/subarea/oneclickupload',
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
			onComplete:function(data){
				if (data) {
					$.messager.alert("恭喜","上传成功","info");
				} else {
					$.messageer.alert("错误","上传失败","error");
				}
			}
		}); 
	
	});

	function doDblClickRow(rowIndex,rowData ){
		if(endediting()){
			$("#grid").datagrid("beginEdit",rowIndex);
			editIndex = rowIndex;
		} else {
			$("#grid").datagrid('selectRow',editIndex);
		} 
	}
	function endediting(){
		if (editIndex==undefined) {
			return true;
		} else if ($("#grid").datagrid("validateRow",editIndex)){
			$("#grid").datagrid('endEdit',editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	//行内编辑
	function afterEdit(rowIndex, rowData, changes){
		//rowData 修改的行记录
		$.post(
			'${pageContext.request.contextPath}/subarea/updateField',
			rowData,
			function(data){
				if (!data) {
					alert("修改失败");
				}
			})
	}
	
	//清空表格数据
	function cleardata(){
		$("#addSubareaWindow").form("clear");
	}
	
	$.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
        var str=this.serialize();  
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    }; 
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<!-- 添加 修改分区 -->
	<div class="easyui-window" title="分区添加修改" id="addSubareaWindow" data-options="onBeforeClose:cleardata"
	collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="saveSubareaForm" action="${pageContext.request.contextPath }/subarea/save" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>选择区域</td>
						<td>
							<input class="easyui-combobox" name="region.id"  
    							data-options="mode:'remote',valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/region/ajaxList'" />  
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" name="startnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" name="endnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" name="single" style="width:150px;">  
							    <option value="0">单双号</option>  
							    <option value="1">单号</option>  
							    <option value="2">双号</option>  
							</select> 
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" name="position" class="easyui-validatebox" required="true" style="width:250px;"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="querySubareaForm" action="${pageContext.request.contextPath }/subarea/download" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" id="qp" name="region.province" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" id="qc" name="region.city" /></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" id="qd" name="region.district" /></td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td><input type="text" id="qdec" name="decidedzone.id" /></td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" id="qeky" name="addresskey" /></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>