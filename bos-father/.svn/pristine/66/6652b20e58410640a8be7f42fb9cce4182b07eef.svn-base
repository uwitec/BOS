<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加业务受理单</title>
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
		$("body").css({visibility:"visible"});
		load(0,province);
		
		// 对save按钮条件 点击事件
		$('#save').click(function(){
			// 对form 进行校验
			if($('#noticebillForm').form('validate')){
				var p = document.getElementById("province").selectedOptions[0].text;
				var c = document.getElementById("city").selectedOptions[0].text;
				var cc = document.getElementById("county").selectedOptions[0].text;
				//var p = $("#hprovince")[0].selectOptions[0].text;				
				//var c = $("#hcity")[0].selectOptions[0].text;				
				//var cc = $("#hcounty")[0].selectOptions[0].text;				
				$("#hprovince").val(p);
				$("#hcity").val(c);
				$("#hcounty").val(cc);
				$('#noticebillForm').submit();
			}
		});
		
		$("#telephone").blur(function(){
			$.post(
				'${pageContext.request.contextPath}/noticebill/findCustomerByTel',
				{"telephone":this.value},
				function(data){
					if (data) {
						$("#tel_sp").html("<font color='green'>老客户</font>");
						$("#customerId").show();
						$("#cidtext").show();
						$("#customerId").val(data.id);
						$("#customerId").attr("readonly","readonly");
						$("#customerName").val(data.name);
					}else{
						$("#tel_sp").html("");
						$("#customerId").hide();
						$("#cidtext").hide();
						$("#customerId").val("");
						$("#customerName").val("");
					}
				});
		});
	});
	//三级联动
	function load(pid,target){
		target.length=1;
		county.length=1;
		if (pid=="none") {
			return;
		}
		$.post(
			'${pageContext.request.contextPath}/city/load',
			{"pid":pid},
			function(data){  //[{},{}]
				$(data).each(function(){
					$(target).append("<option value='"+this.id+"'>"+this.name+"</option>");
				});
			});
	}
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false"
		border="false">
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
				plain="true">新单</a>
			<a id="edit" icon="icon-edit" href="${pageContext.request.contextPath }/page_qupai_noticebill.action" class="easyui-linkbutton"
				plain="true">工单操作</a>	
		</div>
	</div>
	<div region="center" style="overflow:auto;padding:5px;" border="false">
		<form id="noticebillForm" action="${pageContext.request.contextPath}/noticebill/save" method="post">
			<table class="table-edit" width="95%" align="center">
				<tr class="title">
					<td colspan="4">客户信息</td>
				</tr>
				<tr>
					<td>来电号码:</td>
					<td><input type="text" class="easyui-validatebox" name="telephone"
						id="telephone" required="true" />
						<span id="tel_sp"></span></td>
					<td id="cidtext">客户编号:</td>
					<td><input type="text"   name="customerId"
						id="customerId"  /></td>
				</tr>
				<tr>
					<td>客户姓名:</td>
					<td><input type="text" class="easyui-validatebox" name="customerName"
						id="customerName" required="true" /></td>
				</tr>
				<tr class="title">
					<td colspan="4">货物信息</td>
				</tr>
				<tr>
					<td>品名:</td>
					<td><input type="text" class="easyui-validatebox" name="product"
						required="true" /></td>
					<td>件数:</td>
					<td><input type="text" class="easyui-numberbox" name="num"
						required="true" /></td>
				</tr>
				<tr>
					<td>重量:</td>
					<td><input type="text" class="easyui-numberbox" name="weight"
						required="true" /></td>
					<td>体积:</td>
					<td><input type="text" class="easyui-validatebox" name="volume"
						required="true" /></td>
				</tr>
				<tr>
					<td>取件地址</td>
					<td colspan="3">
					<select id="province" name="province" onchange="load(value,city)">
						<option value="none">--请选择省份--</option>
					</select>
					<select id="city" name="city" onchange="load(value,county)">
						<option value="none">--请选择市区--</option>
					</select>
					<select id="county" name="county">
						<option value="none">--请选择县镇--</option>
					</select>
					<input type="hidden" name="hprovince" id="hprovince"/>
					<input type="hidden" name="hcity" id="hcity"/>
					<input type="hidden" name="hcounty" id="hcounty"/>
					<input type="text" class="easyui-validatebox" name="pickaddress"
						required="true" size="68"/></td>
				</tr>
				<tr>
					<td>到达城市:</td>
					<td><input type="text" class="easyui-validatebox" name="arrivecity"
						required="true" /></td>
					<td>预约取件时间:</td>
					<td><input type="text" class="easyui-datebox" name="pickdate"
						data-options="required:true, editable:false" /></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3"><textarea rows="5" cols="80" type="text" class="easyui-validatebox" name="remark"
						required="true" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>