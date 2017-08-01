package cn.guwei.bos.web.action.auth;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.auth.Menu;
import cn.guwei.bos.domain.auth.Role;
import cn.guwei.bos.web.action.BaseAction;
@Controller("roleAction")
@Scope("prototype")
@Namespace("/role")
@ParentPackage("mavenbos")
public class RoleAction extends BaseAction<Role>{

	@Action(value="save",results={
			@Result(name="save",location="/WEB-INF/pages/admin/role.jsp")})
	public String save(){
		String menusIds = getParameter("menuIds");
		String[] functionIds = ServletActionContext.getRequest().getParameterValues("functionIds");
		facedeService.getRoleService().save(model,menusIds,functionIds);
		return "save";
	}
	
	@Action(value="pageQuery",results={@Result(name="pagequery",type="fastJson",params={"root","map"})})
	public String pageQuery(){
		Page<Role> roles = facedeService.getRoleService().findAll(getPageRequest());
		setPageData(roles);
		return "pagequery";
	}
	
	@Action(value="ajaxList",results={
			@Result(name="ajaxList",type="fastJson",params={"includeProperties","id,name"})})
	public String ajaxList(){
		List<Role> roles = facedeService.getRoleService().findAll();
		push(roles);
		return "ajaxList";
	}
	
}
