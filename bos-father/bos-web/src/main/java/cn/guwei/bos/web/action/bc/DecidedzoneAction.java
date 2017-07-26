package cn.guwei.bos.web.action.bc;

import java.util.ArrayList;
import java.util.List;
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
import cn.guwei.bos.domain.customer.Customers;
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
	
	//获取未关联定区的客户
	@Action(value="getnoassociation",results={
			@Result(name="noAssociation",type="fastJson",params={"includeProperties","id,name"})})
	public String getnoassociation(){
		List<Customers> list = facedeService.getDecidedzoneService().getNoAssociation();
		push(list);
		return "noAssociation";
	}
	//获取已关联定区的客户
	@Action(value="getassociation",results={
			@Result(name="association",type="fastJson",params={"includeProperties","id,name"})})
	public String getassociation(){
		List<Customers> list = facedeService.getDecidedzoneService().getAssociation(model.getId());
		push(list);
		return "association";
	}
	//修改定区客户关系
	@Action(value="assignC2D",results={
			@Result(name="assignC2D",location="/WEB-INF/pages/base/decidedzone.jsp")})
	public String assignC2D(){
		String[] customerIds = ServletActionContext.getRequest().getParameterValues("customerIds");
		facedeService.getDecidedzoneService().assignC2D(customerIds,model.getId());
		return "assignC2D";
	}
	
	//查询客户
	@Action(value="findCustomer",results={
			@Result(name="findCustomer",type="fastJson",params={"includeProperties","id,name,station"})})
	public String findCustomer(){
		String did = getParameter("decidedzoneId");
		List<Customers> c = (List<Customers>)facedeService.getDecidedzoneService().findCustomer(did);
		push(c);
		
		return "findCustomer";
	}
}
