package cn.guwei.bos.web.action.qp;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.customer.Customers;
import cn.guwei.bos.domain.qp.Noticebill;
import cn.guwei.bos.domain.user.User;
import cn.guwei.bos.service.facede.FacedeService;
import cn.guwei.bos.web.action.BaseAction;

@Controller("noticebillAction")
@Scope("prototype")
@Namespace("/noticebill")
@ParentPackage("mavenbos")
public class NoticebillAction extends BaseAction<Noticebill>{

	@Autowired
	private FacedeService facedeService;
	
	@Action(value="findCustomerByTel")
	public String findCustomerByTel(){
		Customers c = facedeService.getNoticebillService().findCustomerByTel(model.getTelephone());
		push(c);
		return "success";
	}
	
	/**
	 * noticebill 包括受理人loginuser , staff , customer等
	 * @return
	 */
	@Action(value="save",results={@Result(name="save",location="/WEB-INF/pages/qupai/noticebill_add.jsp")})
	public String save(){
		User user = (User) getSessionAttribute("loginUser");
		model.setUser(user); //受理人
		String province = getParameter("hprovince");
		String city = getParameter("hcity");
		String county = getParameter("hcounty");
		//model.setPickaddress(province+city+county);
		
		facedeService.getNoticebillService().saveNoticebill(model,province,city,county);
		return "save";
	}
	
}
