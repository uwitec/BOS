package cn.guwei.bos.web.action.bc;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.bc.Staff;
import cn.guwei.bos.web.action.BaseAction;
@Controller("staffAction")
@Scope("prototype")
@Namespace("/staff")
@ParentPackage("mavenbos")
public class StaffAction extends BaseAction<Staff>{
	
	@Action(value="save",results={@Result(name="saveSuccess",location="/WEB-INF/pages/base/staff.jsp")})
	public String save(){
		facedeService.getStaffService().saveStaff(model);
		return "saveSuccess";
	}

	@Action(value="validStaffId",results={@Result(name="validStaffId",type="json")})
	public String validStaffId(){
		Staff staff = facedeService.getStaffService().findStaffById(model.getId());
		if (staff==null) {
			push(true);
		} else {
			push(false);
		}
		return "validStaffId";
	}
	
	@Action(value="pageStaffList",results={@Result(name="pageStaffList",type="json")})
	public String pageStaffList(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			PageRequest pageable = new PageRequest(page-1, rows);
			Page<Staff> page = facedeService.getStaffService().findAll(pageable);
			map.put("total", page.getTotalElements());
			map.put("rows", page.getContent());
			push(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pageStaffList";
	}
	
	@Action(value="delBatch",results={@Result(name="delBatch",type="json")})
	public String delBatch(){
		try{
			String ids = getParameter("ids");
			if (StringUtils.isNotBlank(ids)) {
				String[] arr = ids.split(",");
				facedeService.getStaffService().delBatch(arr);
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
				facedeService.getStaffService().startBatch(arr);
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
}
