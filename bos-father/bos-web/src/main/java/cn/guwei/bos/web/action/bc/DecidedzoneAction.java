package cn.guwei.bos.web.action.bc;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.domain.bc.Staff;
import cn.guwei.bos.web.action.BaseAction;

@Controller("decidedzoneAction")
@Scope("prototype")
@Namespace("/decidedzone")
@ParentPackage("mavenbos")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	
	
	
	//分页查询
	@Action(value="pageQuery",results={
			@Result(name="pageQuery1",type="fastJson",params={"root","map"})})
	public String pageQuery(){
		Page<Decidedzone> page = facedeService.getDecidedzoneService().pageQuery(getSpec(),getPageRequest());
		setPageData(page);
		return "pageQuery1";
	}
	//抽取查询条件
	private Specification<Decidedzone> getSpec() {
		Specification<Decidedzone> spec = new Specification<Decidedzone>() {

			@Override
			public Predicate toPredicate(Root<Decidedzone> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> list = new ArrayList<>();
				//decidedzone
				if(StringUtils.isNotBlank(model.getId())){
					list.add(cb.like(root.get("id").as(String.class), "%"+model.getId()+"%"));
				}
				//staff
				if (model.getStaff()!=null) {
					Join<Decidedzone, Staff> joinStaff = root.join(root.getModel().getSingularAttribute("staff", Staff.class),JoinType.LEFT);
					if (StringUtils.isNotBlank(model.getStaff().getStation())) {
						list.add(cb.like(joinStaff.get("station").as(String.class), "%"+model.getStaff().getStation()+"%"));
					}
				}
				
				if (StringUtils.isNotBlank(getParameter("isAccsocitionSubarea"))) {
					if ("1".equals(getParameter("isAccsocitionSubarea"))) {
						list.add(cb.isNotEmpty(root.get("subareas").as(Set.class)));
					} else if ("0".equals(getParameter("isAccsocitionSubarea"))) {
						list.add(cb.isEmpty(root.get("subarea").as(Set.class)));
					}
				}
				
				Predicate[] predicates = new Predicate[list.size()];
				return cb.and(list.toArray(predicates));
			}
		};
		return spec;
	}
	
	//添加
	@Action(value="save",results={
			@Result(name="save",location="/WEB-INF/pages/base/decidedzone.jsp")})
	public String save(){
		String[] sid = ServletActionContext.getRequest().getParameterValues("sid");
		facedeService.getDecidedzoneService().save(sid,model);
		return "save";
	} 
	
//	//修改
//	@Action(value="updateField")
//	public String updateField(){
//		try {
//			Region region = new Region();
//			region.setId(getParameter("region[id]"));
//			//Decidedzone decidedzone = new Decidedzone();
//			//decidedzone.setId(getPageRequest("decidedzone[id]"));
//			model.setRegion(region);
//			facedeService.getSubareaService().save(model);
//			push(true);
//		} catch (Exception e) {
//			push(false);
//			e.printStackTrace();
//		}
//		return "success";
//	}
	
	
}