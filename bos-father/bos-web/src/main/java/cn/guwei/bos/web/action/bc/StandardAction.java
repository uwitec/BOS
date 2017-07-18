package cn.guwei.bos.web.action.bc;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.bc.Standard;
import cn.guwei.bos.domain.user.User;
import cn.guwei.bos.web.action.BaseAction;
@Controller("standardAction")
@Scope("prototype")
@Namespace("/standard")
@ParentPackage("mavenbos")
public class StandardAction extends BaseAction<Standard> {

	@Action(value="save",results={@Result(name="save",location="/WEB-INF/pages/base/standard.jsp")})
	public String save(){
		User user = (User)getSessionAttribute("loginUser");
		model.setOperator(user.getEmail());
		model.setOperatorcompany(user.getStation());
		facedeService.getStandardService().save(model);
		return "save";
	}
	
	@Action(value="listStandard",results={@Result(name="listStandard",type="json")})
	public String listStandard(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			Pageable pageable = new PageRequest(page-1, rows);
			Page<Standard> page = facedeService.getStandardService().listStandardPage(pageable);
			map.put("total", page.getTotalElements());
			map.put("rows", page.getContent());
			push(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listStandard";
	}
	
	@Action(value="delBatch",results={@Result(name="delBatch",type="json")})
	public String delBatch(){
		try{
			String ids = getParameter("ids");
			if (StringUtils.isNotBlank(ids)) {
				String[] arr = ids.split(",");
				facedeService.getStandardService().delBatch(arr);
				push(true);
			} else {
				push(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			push(false);
		}
		return "delBatch";
	}
	
	@Action(value="startBatch",results={@Result(name="startBatch",type="json")})
	public String startBatch(){
		try{
			String ids = getParameter("ids");
			if (StringUtils.isNotBlank(ids)) {
				String[] arr = ids.split(",");
				facedeService.getStandardService().startBatch(arr);
				push(true);
			} else {
				push(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			push(false);
		}
		return "startBatch";
	}
	
	@Action(value="listStandardName",results={@Result(name="listStandardName",type="json")})
	public String listStandardName(){
		List<Standard> list = facedeService.getStandardService().findAll();
		push(list);
		return "listStandardName";
	}
	

}
