package cn.guwei.bos.web.action.user;


import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cn.guwei.bos.domain.User;
import cn.guwei.bos.service.facede.FacedeService;
import cn.guwei.bos.web.action.BaseAction;
@Controller("userAction")
@Scope("prototype")
@Namespace("/user")
@ParentPackage("mavenbos")
public class UserAction extends BaseAction<User> {

	@Autowired
	private FacedeService facedeService;

	@Action(value="validateCodeAjax",results={@Result(name="validateCode",type="json")})
	public String validateCodeAjax(){
		String inputcode = getParameter("checkcode");
		String sessioncode = (String) getSessionAttribute("key");
		if (inputcode.equalsIgnoreCase(sessioncode)) {
			push(true);
		} else {
			push(false);
		}
		return "validateCode";
	}
	
	@Action(value="login",results={@Result(name="login_success",type="redirect",location="/index.jsp"),
									@Result(name="login_fail",location="/login.jsp"),
									@Result(name="login_exception",location="/login.jsp")})
	@InputConfig(methodName="login",resultName="login_input")
	public String login(){
		String inputcode = getParameter("checkcode");
		String sessioncode = (String) getSessionAttribute("key");
		if (StringUtils.isNotBlank(inputcode) && inputcode.equalsIgnoreCase(sessioncode)) {
			removeSessionAttribute("key");
			User exitUser = facedeService.getUserService().findUserByEmailAndPassword(model.getEmail(), model.getPassword());
			if (exitUser==null) {
				addActionError(this.getText("login.emailorpassword.error"));
				return "login_fail";
			} else {
				setSessionAttribute("loginUser", exitUser);
				return "login_success";
			}
		} else {
             addActionError(this.getText("login.exception"));
             return "login_exception";
		}
	}
	
	@Action(value="loginOut",results={@Result(name="loginout",type="redirect",location="/login.jsp")})
	public String loginOut(){
		removeSessionAttribute("loginUser");
		
		return "loginout";
	}
	
}
