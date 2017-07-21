package cn.guwei.bos.web.action.bc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

	@Action(value="validStaffId")
	public String validStaffId(){
		Staff staff = facedeService.getStaffService().findStaffById(model.getId());
		if (staff==null) {
			push(true);
		} else {
			push(false);
		}
		return "success";
	}
	
	@Action(value="pageStaffList")
	public String pageStaffList(){
		
		
		HashMap<String, Object> map = new HashMap<>();
		try {
			Specification<Staff> specification = new Specification<Staff>() {
				
				@Override
				public Predicate toPredicate(Root<Staff> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					ArrayList<Predicate> list = new ArrayList<Predicate>();
					//name
					if (StringUtils.isNotBlank(model.getName())) {
						list.add(cb.like(root.get("name").as(String.class), "%"+model.getName()+"%"));
					} 
					//telephone
					if (StringUtils.isNotBlank(model.getTelephone())) {
						list.add(cb.equal(root.get( "telephone").as(String.class), model.getTelephone()));
					}
					//haspda
					if (model.getHaspda()!=null) {
						System.out.println(model.getHaspda());
						list.add(cb.equal(root.get("haspda").as(Integer.class), model.getHaspda()));
					}
					//standard
					if (StringUtils.isNotBlank(model.getStandard())) {
						list.add(cb.equal(root.get("standard").as(String.class), model.getStandard()));
					}
					Predicate[] ps = new Predicate[list.size()];
					return cb.and(list.toArray(ps));
				}
			};
			PageRequest pageable = new PageRequest(page-1, rows);
			Page<Staff> page = facedeService.getStaffService().findAll(specification,pageable);
			map.put("total", page.getTotalElements());
			map.put("rows", page.getContent());
			push(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@Action(value="delBatch")
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
		return "success";
	}
	@Action(value="startBatch")
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
		return "success";
	}
	
	
}
