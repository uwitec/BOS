package cn.guwei.bos.web.action.result;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class FastJsonTest extends ActionSupport{

	@Action(value="demo",results={@Result(name="xx",type="fastJson")})
	public String demo(){
		System.out.println("action 执行了");
		return "xx";
	}
}
