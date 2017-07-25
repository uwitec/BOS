package cn.guwei.bos.web.action.bc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.guwei.bos.domain.bc.Region;
import cn.guwei.bos.utils.PinYin4jUtils;
import cn.guwei.bos.web.action.BaseAction;

@Controller("regionAction")
@Scope("prototype")
@Namespace("/region")
@ParentPackage("mavenbos")
public class RegionAction extends BaseAction<Region>{
	
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
				List<Region> regions = new ArrayList<Region>();
				for (Row row : sheet) {
					//排除第一行
					int rowNum = row.getRowNum();
					if (rowNum==0) {
						continue;
					}
					Region region = new Region();
					region.setId(row.getCell(0).getStringCellValue());
					String province = row.getCell(1).getStringCellValue();
					region.setProvince(province);
					String city = row.getCell(2).getStringCellValue();
					region.setCity(city);
					String district = row.getCell(3).getStringCellValue();
					region.setDistrict(district);
					region.setPostcode(row.getCell(4).getStringCellValue());
					
					//citycode
					city = city.substring(0, city.length()-1);
					region.setCitycode(PinYin4jUtils.hanziToPinyin(city));
					
					//shortcode
					province = province.substring(0, province.length()-1);
					district = district.substring(0, district.length()-1);
					String[] strings;
					if (province.equalsIgnoreCase(city)) {
						//直辖市
						strings = PinYin4jUtils.getHeadByString(province+district);
					} else {
						//非直辖市
						strings = PinYin4jUtils.getHeadByString(province+city+district);
					}
					String shortcode = getHeaderFromArray(strings);
					region.setShortcode(shortcode);
					regions.add(region);
				}
				//4 将数据交给service
				facedeService.getRegionService().save(regions);
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
	@Action(value="pageRegionByRedis")
	public String pageRegion(){
		Specification<Region> spec = new Specification<Region>() {

			@Override
			public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> list = new ArrayList<>();
				if (StringUtils.isNotBlank(model.getProvince())) {
					list.add(cb.like(root.get("province").as(String.class), "%"+model.getProvince()+"%"));
				}
				if (StringUtils.isNotBlank(model.getCity())) {
					list.add(cb.like(root.get("city").as(String.class), "%"+model.getCity()+"%"));
				}
				if (StringUtils.isNotBlank(model.getDistrict())) {
					list.add(cb.like(root.get("district").as(String.class),"%"+ model.getDistrict()+"%"));
				}
				if (StringUtils.isNotBlank(model.getShortcode())) {
					list.add(cb.like(root.get("shortcode").as(String.class),"%"+ model.getShortcode()+"%"));
				}
				if (StringUtils.isNotBlank(model.getPostcode())) {
					list.add(cb.equal(root.get("postcode").as(String.class), model.getPostcode()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				
				return cb.and(list.toArray(predicates));
			}
		};
//		if (spec.) {
			String pageString = facedeService.getRegionService().pageQueryByRedis(getPageRequest(),spec);
			try {
				HttpServletResponse response = getResponse();
				response.setContentType("text/json;charset=utf-8");
				response.getWriter().println(pageString);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return NONE;
//		} else {
//			Page<Region> page = facedeService.getRegionService().findAll(getPageRequest(),spec);
//			setPageData(page);
//			return "pageQuery";
//		}
	}
	
	
	
	//添加
	@Action(value="updateRegion",results={
			@Result(name="updateRegionsuccess",location="/WEB-INF/pages/base/region.jsp")})
	public String updateRegion(){
		String citycode = PinYin4jUtils.hanziToPinyin(model.getCity());
		model.setCitycode(citycode);
		facedeService.getRegionService().add(model);
		return "updateRegionsuccess";
	} 
	//id唯一性校验
	@Action(value="validRegionId")
	public String validRegionId(){
		Region r = facedeService.getRegionService().findRegionByPostcode(model.getPostcode());
		if (r.getId().equals(model.getId())) {
			push(true);
		} else {
			Region region = facedeService.getRegionService().findRegionById(model.getId());
			if (region==null) {
				push(true);
			} else {
				push(false);
			}
		}
		return "success";
	}
	//postcode唯一性校验
	@Action(value="validRegionPostcode")
	public String validRegionPostcode(){
		Region region = facedeService.getRegionService().findRegionById(model.getId());
		if (region.getPostcode()==model.getPostcode()) {
			push(true);
		}else {
			Region r = facedeService.getRegionService().findRegionByPostcode(model.getPostcode());
			if (r==null) {
				push(true);
			} else {
				push(false);
			}
		}
		
		return "success";
	}
	
	@Action(value="ajaxList",results={
			@Result(name="serPCD",type="fastJson",params={"includeProperties","id,name"})})
	public String ajaxList(){
		String q = getParameter("q");
		List<Region> list;
		if (StringUtils.isNotBlank(q)) {
			list = facedeService.getRegionService().findAll(q);
		} else {
			list = facedeService.getRegionService().findAll();
		}
		push(list);
		return "serPCD";
	}
	
	private String getHeaderFromArray(String[] strings) {
		if (strings!=null&&strings.length>0) {
			StringBuilder sb = new StringBuilder();
			for (String s : strings) {
				sb.append(s);
			} 
			return sb.toString();
		} else {
			return null;
		}
	}
}
