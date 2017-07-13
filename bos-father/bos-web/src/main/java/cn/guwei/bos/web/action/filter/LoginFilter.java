package cn.guwei.bos.web.action.filter;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import cn.guwei.bos.domain.User;
@Component("loginInterceptor")
public class LoginFilter extends MethodFilterInterceptor{

	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		User exitUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		if (exitUser==null) {
			ActionSupport sa = (ActionSupport) invocation.getAction();
			sa.addActionError(sa.getText("login.none"));
			return "no_login";
		}
		return invocation.invoke();
	}

}
