package cn.guwei.bos.web.action.city;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.city.City;
import cn.guwei.bos.web.action.BaseAction;

@Controller("cityAction")
@Scope("prototype")
@Namespace("/city")
@ParentPackage("mavenbos")
public class CityAction extends BaseAction<City>{

	@Action(value="load")
	public String load(){
		String citys = facedeService.getCityService().findByRedis(model.getPid());
		try {
			HttpServletResponse response = getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().println(citys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
}
