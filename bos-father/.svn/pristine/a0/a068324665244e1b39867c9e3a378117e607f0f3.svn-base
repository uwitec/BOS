<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码主页</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/style.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style_grey.css" />
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>

<style>
input[type=text] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

input[type=password] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

#loginform\:codeInput {
	margin-left: 1px;
	margin-top: 1px;
}

#loginform\:vCode {
	margin: 0px 0 0 60px;
	height: 34px;
}
</style>
<script type="text/javascript">
	if (window.self!=window.top) {
		window.top.location=window.location;
	}
	var active = true; //发送按钮的状态
	var second = 120;  //倒计时120s
	var secondInterval;
	$(function () {
		$("#go").click(function() {
			if (active==false) {
				return ;
			}
			var regex = /^1(3|5|7)\d{9}$/;
			var telephone = $("input[name='telephone']").val();
			if (regex.test(telephone)) {
				$.post(
				    '${pageContext.request.contextPath}/user/sendSmsAjax',
				    {telephone:telephone},
				    function(data){
				    	if (data) {
							active = true;
							if (active) {
								active = false;
								secondInterval = setInterval(function () {
									if (second<0) {
										$("#go").html("发送验证码").css();
										active = true;
										second = 120;
										clearInterval(secondInterval);
										secondInterval = undefined;
									} else {
										$("#go").html(second+"秒后重发");
										second --;
									}
								},1000);
							}
						} else {
							$.messager.alert("警告","系统繁忙，请稍后重试！","Warning");
							active = false;
						}
				    }
				);
			} else {
				$.messager.alert("警告","请输入正确的手机号！","Warning");
				return;
			}
		});
		
		
		$("#sub").click(function(){
			if ($("input[name='checkcode']").val()=="") {
				alert("请填写验证码");
				return ;
			}
			$.post(
				'${pageContext.request.contextPath}/user/tocheckCode',
				{"checkcode":$("input[name=checkcode]").val(),"telephone":$("input[name='telephone']").val()},
				function (dat) {
					if (dat) {
						location="${pageContext.request.contextPath}/getbackpassword.jsp?telephone="+$("input[name='telephone'").val()
					} else {
						$.messager.alert("提醒","验证失败，请重新发送！","info");
					}
				});
		});
	});
	
	
</script>
</head>
<body>
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: -280px;">
		<img src="${pageContext.request.contextPath }/images/logo.png" style="border-width: 0; margin-left: 0;" />
		<span style="float: right; margin-top: 35px; color: #488ED5;">新BOS系统以宅急送开发的ERP系统为基础，致力于便捷、安全、稳定等方面的客户体验</span>
	</div>
	<div class="main-inner" id="mainCnt"
		style="width: 900px; height: 440px; overflow: hidden; position: absolute; left: 50%; top: 50%; margin-left: -450px; margin-top: -220px; background-image: url('${pageContext.request.contextPath }/images/bg_login.jpg')">
		<div id="loginBlock" class="login"
			style="margin-top: 80px; height: 255px;">
			<div class="loginFunc">
				<div id="lbNormal" class="loginFuncMobile">密码找回</div>
			</div>
			<div class="loginForm">
				<form id="newsmsform" name="loginform" method="post" class="niceform"
					action="#">
					<div id="idInputLine" class="loginFormIpt showPlaceholder"
						style="margin-top: 5px;">
						<input id="loginform:idInput" type="text" name="telephone"
							class="loginFormTdIpt" maxlength="50"/>
						<label for="idInput" class="placeholder" id="idPlaceholder">手机号：</label>
					</div>
					<div class="forgetPwdLine"></div>
					<div id="pwdInputLine" class="loginFormIpt showPlaceholder">
						<input id="loginform:pwdInput" class="loginFormTdIpt" type="text"
							name="checkcode"/>
						<label for="pwdInput" class="placeholder" id="pwdPlaceholder">验证码：</label>
						
					</div>
						<div class="loginFormIpt loginFormIptWiotTh"
						style="margin-top:58px;">
						<a href="javascript:void(0);" id="loginform:j_id19" name="loginform:j_id19">
						<span
							id="go" class="btn btn-login"
							style="margin-top:-36px;margin-right: 155px">发送验证码</span>
						</a>
						<a href="javascript:void(0);" id="loginform:j_id19" name="loginform:j_id19">
						<span
							id="sub" class="btn btn-login"
							style="margin-top:-36px;">确认</span>
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: 220px;">
		<span style="color: #488ED5;">Powered By www.itcast.cn</span><span
			style="color: #488ED5;margin-left:10px;">推荐浏览器（右键链接-目标另存为）：<a
			href="http://download.firefox.com.cn/releases/full/23.0/zh-CN/Firefox-full-latest.exe">Firefox</a>
		</span><span style="float: right; color: #488ED5;">宅急送BOS系统</span>
	</div>
</body>
</html>