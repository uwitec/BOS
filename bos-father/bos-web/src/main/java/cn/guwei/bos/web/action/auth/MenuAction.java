package cn.guwei.bos.web.action.auth;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.auth.Menu;
import cn.guwei.bos.service.facede.FacedeService;
import cn.guwei.bos.web.action.BaseAction;
@Controller("menuAction")
@Scope("prototype")
@Namespace("/menu")
@ParentPackage("mavenbos")
public class MenuAction extends BaseAction<Menu>{
	
	@Action(value="ajaxListHasSonMenus",results={
			@Result(name="ajaxListHasSonMenus",type="fastJson",params={"includeProperties","id,name"})})
	public String ajaxListHasSonMenus(){
		List<Menu> menus = facedeService.getMenuService().ajaxListHasSonMenus();
		push(menus);
		return "ajaxListHasSonMenus";
	}
	@Action(value="ajaxList",results={
			@Result(name="ajaxList",type="fastJson",params={"includeProperties","id,name,pId,page"})})
	public String ajaxList(){
		List<Menu> menus = facedeService.getMenuService().findAll();
		push(menus);
		return "ajaxList";
	}
	
	@Action(value = "pageQuery",results={@Result(name="pagequery",type="fastJson",params={"root","map"})})
	public String pageQuery() {
		setPage(Integer.parseInt(getParameter("page")));
		Page<Menu> pageData = facedeService.getMenuService().pageQuery(getPageRequest());
		setPageData(pageData);// 父类
		return "pagequery";
	}
	
	@Action(value = "save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/menu.jsp") })
	public String save() {
		if (model.getMenu()==null||StringUtils.isBlank(model.getMenu().getId())) {
			model.setMenu(null);
		}
		facedeService.getMenuService().save(model);
		return "save";
	}
}
