package cn.guwei.bos.web.action.bc;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import cn.guwei.bos.domain.bc.Region;
import cn.guwei.bos.utils.DownLoadUtils;
import cn.guwei.bos.utils.PinYin4jUtils;
import cn.guwei.bos.web.action.BaseAction;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;

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
	@Action(value="pageQueryByRedis")
	public String pageQueryByRedis(){
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
		String pageString = facedeService.getRegionService().pageQueryByRedis(getPageRequest(),spec);
		try {
			HttpServletResponse response = getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().println(pageString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
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
	
//	//itext 后台代码生成 pdf
//	@Action(value="download")
//	public String download(){
//		List<Region> list = facedeService.getRegionService().findAll();
//		
//		try {
//			Document document = new Document();
//			// response
//			HttpServletResponse response = ServletActionContext.getResponse();
//			PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
//			writer.setEncryption("guwei".getBytes(), "guwei".getBytes(), PdfWriter.ALLOW_SCREENREADERS, PdfWriter.STANDARD_ENCRYPTION_128);
//			// 浏览器下载 ...两个头
//			String filename = new Date(System.currentTimeMillis()).toLocaleString() + "_区域列表.pdf";
//			response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));// mime 类型
//			response.setHeader("Content-Disposition", "attachment;filename=" + DownLoadUtils.getAttachmentFileName(filename, ServletActionContext.getRequest().getHeader("user-agent")));
//			// 打开文档
//			document.open();
//			Table table = new Table(5, list.size() + 1);// 5列 行号 0 开始
//			table.setBorderWidth(1f);
//			table.setAlignment(1);// // 其中1为居中对齐，2为右对齐，3为左对齐
//			// table.setBorder(1); // 边框
//			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); // 水平对齐方式
//			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP); // 垂直对齐方式
//			// 设置表格字体
//			BaseFont cn = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
//			Font font = new Font(cn, 10, Font.NORMAL, Color.BLUE);
//
//			// 表头
//			// table.addCell(buildCell("工作单编号", font));
//			table.addCell(buildCell("省", font));
//			table.addCell(buildCell("市", font));
//			table.addCell(buildCell("区", font));
//			table.addCell(buildCell("邮编", font));
//			table.addCell(buildCell("地区简码", font));
//
//			// 表格数据
//			for (Region r : list) {
//				// table.addCell(buildCell(workOrderManage.getId(), font));
//				table.addCell(buildCell(r.getProvince(), font));
//				table.addCell(buildCell(r.getCity(), font));
//				table.addCell(buildCell(r.getDistrict(), font));
//				table.addCell(buildCell(r.getPostcode(), font));
//				table.addCell(buildCell(r.getShortcode(), font));
//			}
//
//			// 向文档添加表格
//			document.add(table);
//			document.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return NONE;
//	}
//	private Cell buildCell(String content, Font font) throws BadElementException {
//		Phrase phrase = new Phrase(content, font);
//		Cell cell = new Cell(phrase);
//		// 设置垂直居中
//		cell.setVerticalAlignment(1);
//		// 设置水平居中
//		cell.setHorizontalAlignment(1);
//		return cell;
//	}

	//jasperReport 模板生成 pdf
	@Autowired
	private DataSource dataSource;
	@Action(value = "download")
	public String download() throws Exception {
		// 1: 加载设计文件 report2.jrxml
		String path = ServletActionContext.getServletContext().getRealPath("/jr/report1.jrxml");
		// 2: 报表 parameter 赋值 需要Map 集合
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("company", "黑马程序员");
		// 3: 编译该文件 JasperCompilerManager
		JasperReport report = JasperCompileManager.compileReport(path);
		// 4: JapserPrint = JasperFillManager.fillReport(report,map,connection)
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());
		// 5: 下载 准备一个流 两个头
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream outputStream = response.getOutputStream();
		String filename = "区域列表.pdf";
		response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));
		response.setHeader("Content-Disposition", "attachment;filename=" + DownLoadUtils.getAttachmentFileName(filename, ServletActionContext.getRequest().getHeader("user-agent")));
		// 6: JapdfExport   定义报表输出源
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		// 7: 导出
		exporter.exportReport();
		outputStream.close();
		return NONE;
	}
	
}
