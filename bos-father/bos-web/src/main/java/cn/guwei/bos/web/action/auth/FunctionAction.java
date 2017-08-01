package cn.guwei.bos.web.action.auth;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.auth.Function;
import cn.guwei.bos.domain.auth.Menu;
import cn.guwei.bos.service.facede.FacedeService;
import cn.guwei.bos.web.action.BaseAction;

@Controller("functionAction")
@Scope("prototype")
@Namespace("/function")
@ParentPackage("mavenbos")
public class FunctionAction extends BaseAction<Function> {
	
	@Action(value = "save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/function.jsp") })
	public String save() {
		facedeService.getFunctionService().save(model);
		return "save";
	}

	@Action(value = "pageQuery",results={@Result(name="pagequery",type="fastJson",params={"root","map"})})
	public String pageQuery() {
		Page<Function> pageData = facedeService.getFunctionService().pageQuery(getPageRequest());
		setPageData(pageData);// 父类
		return "pagequery";
	}
	
	@Action(value="ajaxList",results={
			@Result(name="ajaxList",type="fastJson",params={"includeProperties","id,name"})})
	public String ajaxList(){
		List<Function> functions = facedeService.getFunctionService().findAll();
		push(functions);
		return "ajaxList";
	}

}
