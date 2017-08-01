package cn.guwei.bos.web.action.bc;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.domain.bc.Region;
import cn.guwei.bos.domain.bc.Staff;
import cn.guwei.bos.domain.bc.Subarea;
import cn.guwei.bos.utils.DownLoadUtils;
import cn.guwei.bos.utils.PinYin4jUtils;
import cn.guwei.bos.web.action.BaseAction;

@Controller("subareaAction")
@Scope("prototype")
@Namespace("/subarea")
@ParentPackage("mavenbos")
public class SubareaAction extends BaseAction<Subarea>{
	
	private File upload;

	public void setUpload(File upload) {
		this.upload = upload;
	}

	//poi解析Excel
	@Action(value="oneclickupload")
	public String oneclickupload(){
		if (upload!=null) {
			try {
				//1 创建对excel文件的引用
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
				//2 创建对表格的引用
				HSSFSheet sheet = workbook.getSheetAt(0);
				//3 解析sheet 把除第一行数据之外的数据 封装到List中
				List<Subarea> subareas = new ArrayList<Subarea>();
				for (Row row : sheet) {
					//排除第一行
					int rowNum = row.getRowNum();
					if (rowNum==0) {
						continue;
					}
					Subarea subarea = new Subarea();
					String id = row.getCell(0).getStringCellValue();
					String rId = row.getCell(1).getStringCellValue();
					String addresskey = row.getCell(2).getStringCellValue();
					String startnum = row.getCell(3).getStringCellValue();
					String endnum = row.getCell(4).getStringCellValue();
					Character single = row.getCell(5).getStringCellValue().charAt(0);
					String position = row.getCell(6).getStringCellValue();
					
					subarea.setId(id);
					Region region = facedeService.getRegionService().findRegionById(rId);
					subarea.setRegion(region);
					subarea.setAddresskey(addresskey);
					subarea.setStartnum(startnum);
					subarea.setEndnum(endnum);
					subarea.setSingle(single);
					subarea.setPosition(position);
					
					subareas.add(subarea);
				}
				//4 将数据交给service
				facedeService.getSubareaService().save(subareas);
				push(true);
				return "success";
			} catch (Exception e) {
				push(false);
				throw new RuntimeException("文件解析失败"+e.getMessage());
			}
		} else {
			push(false);
			throw new RuntimeException("文件上传失败，请重新上传");
		}
	}
	
	//分页查询
	@Action(value="pageQuery",results={
			@Result(name="pageQuery",type="fastJson",params={"root","map"})})
	public String pageQuery(){
		Specification<Subarea> spec = getSpec();
		
		Page<Subarea> page = facedeService.getSubareaService().findAll(getPageRequest(),spec);
		setPageData(page);
		return "pageQuery";
	}
	//抽取查询条件
	private Specification<Subarea> getSpec() {
		Specification<Subarea> spec = new Specification<Subarea>() {
			@Override
			public Predicate toPredicate(Root<Subarea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> list = new ArrayList<>();
				//region
				if (model.getRegion()!=null) {
					Join<Subarea, Region> regjoin = root.join(root.getModel().getSingularAttribute("region",Region.class),JoinType.LEFT);
					if(StringUtils.isNotBlank(model.getRegion().getProvince())){
						list.add(cb.like(regjoin.get("province").as(String.class), "%"+model.getRegion().getProvince()+"%"));
					}
					if(StringUtils.isNotBlank(model.getRegion().getCity())){
						list.add(cb.like(regjoin.get("city").as(String.class), "%"+model.getRegion().getCity()+"%"));
					}
					if(StringUtils.isNotBlank(model.getRegion().getDistrict())){
						list.add(cb.like(regjoin.get("district").as(String.class), "%"+model.getRegion().getDistrict()+"%"));
					}
				}
				//decidedzone
				if (model.getDecidedzone()!=null&&StringUtils.isNotBlank(model.getDecidedzone().getId())) {
					list.add(cb.equal(root.get("decidedzone"),model.getDecidedzone()));
				}
				//subarea
				if (StringUtils.isNotBlank(model.getAddresskey())) {
					list.add(cb.like(root.get("addresskey").as(String.class),"%"+model.getAddresskey()+"%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return cb.and(list.toArray(predicates));
			}
		};
		return spec;
	}
	
	//添加
	@Action(value="save",results={
			@Result(name="save",location="/WEB-INF/pages/base/subarea.jsp")})
	public String save(){
		facedeService.getSubareaService().save(model);
		return "save";
	} 
	
	//修改
	@Action(value="updateField")
	public String updateField(){
		try {
			Region region = new Region();
			region.setId(getParameter("region[id]"));
			//Decidedzone decidedzone = new Decidedzone();
			//decidedzone.setId(getPageRequest("decidedzone[id]"));
			model.setRegion(region);
			facedeService.getSubareaService().save(model);
			push(true);
		} catch (Exception e) {
			push(false);
			e.printStackTrace();
		}
		return "success";
	}
	//查询定区对应的分区
	@Action(value="querySubareaBydid")
	public String querySubareaBydid(){
		String did = getParameter("decidedzoneId");
		Decidedzone decidedzone = new Decidedzone();
		decidedzone.setId(did);
		List<Subarea> Subarea = facedeService.getSubareaService().findByDecidedzone(decidedzone);
		push(Subarea);
		return "success";
	}
	
	//下载
	@Action(value="download")
	public String download(){
		try {
			List<Subarea> datas = facedeService.getSubareaService().findBySpecification(getSpec());
			//制作表格，设置数据  workbook sheet row cell
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet("分区数据1");
			// excel标题
			HSSFRow first = sheet.createRow(0);
			first.createCell(0).setCellValue("分区编号");
			first.createCell(1).setCellValue("省");
			first.createCell(2).setCellValue("市");
			first.createCell(3).setCellValue("区");
			first.createCell(4).setCellValue("关键字");
			first.createCell(5).setCellValue("起始号");
			first.createCell(6).setCellValue("终止号");
			first.createCell(7).setCellValue("单双号");
			first.createCell(8).setCellValue("位置");
			// 数据体
			if (datas != null && datas.size() != 0) {
				for (Subarea s : datas) {
					// 循环一次创建一行
					int lastRowNum = sheet.getLastRowNum();// 获取当前excel最后一行行号
					HSSFRow row = sheet.createRow(lastRowNum + 1);
					row.createCell(0).setCellValue(s.getId());
					row.createCell(1).setCellValue(s.getRegion().getProvince());
					row.createCell(2).setCellValue(s.getRegion().getCity());
					row.createCell(3).setCellValue(s.getRegion().getDistrict());
					row.createCell(4).setCellValue(s.getAddresskey());
					row.createCell(5).setCellValue(s.getStartnum());
					row.createCell(6).setCellValue(s.getEndnum());
					row.createCell(7).setCellValue(s.getSingle() + "");
					row.createCell(8).setCellValue(s.getPosition());
				}
				// 第一个sheet数据完成
			}
			// 下载
			String filename = "分区数据.xls";
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition	", "attachment;filename=" + DownLoadUtils.getAttachmentFileName(filename, ServletActionContext.getRequest().getHeader("user-agent")));
			response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));
			book.write(response.getOutputStream());
			return NONE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Action(value="ajaxListInfo",results={
			@Result(name="serIdKP",type="fastJson",params={"includeProperties","sid,addresskey,position"})})
	public String ajaxListInfo(){
		List<Subarea> subareas = facedeService.getSubareaService().ajaxListInfo();
		push(subareas);
		return "serIdKP";
	}
}
