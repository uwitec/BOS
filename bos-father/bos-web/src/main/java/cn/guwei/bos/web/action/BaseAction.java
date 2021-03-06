package cn.guwei.bos.web.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.guwei.bos.service.facede.FacedeService;
import cn.guwei.bos.utils.DownLoadUtils;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	
	//分页查询请求数据pageable的封装
	public PageRequest getPageRequest(){
		return new PageRequest(page-1, rows);
	}
	//结果集page
	private Page<T> pageData;
	protected void setPageData(Page<T> pageData){
		this.pageData = pageData;
	}
	//将map放到root栈
	public Map getMap(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		return map;
	}
	
	@Autowired
	protected FacedeService facedeService;
	
	@Autowired
	protected RedisTemplate<String,String> redisTemplate;
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	protected JmsTemplate jmsQueueTemplate;
	
	@Autowired
	@Qualifier("jmsTopicTemplate")
    protected JmsTemplate jmsTopicTemplate;
	
	protected T model;
	public T getModel(){
		return model;
	}
	
	public BaseAction() {
		// 对model进行实例化， 通过子类 类声明的泛型
		Type superclass = this.getClass().getGenericSuperclass();
		// 转化为参数化类型
		ParameterizedType parameterizedType = (ParameterizedType) superclass;
		// 获取一个泛型参数
		Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		try {
			model = modelClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	// 获取请求参数
		public String getParameter(String name) {
			return ServletActionContext.getRequest().getParameter(name);
		}

		// 获取Session Attribute
		public Object getSessionAttribute(String name) {
			return ServletActionContext.getRequest().getSession().getAttribute(name);
		}

		// 向session保存属性
		public void setSessionAttribute(String name, Object value) {
			ServletActionContext.getRequest().getSession().setAttribute(name, value);
		}

		// 向session移除对象
		public void removeSessionAttribute(String name) {
			ServletActionContext.getRequest().getSession().removeAttribute(name);
		}

		// 值栈操作 后续子类actions操作
		public void push(Object obj) {
			ActionContext.getContext().getValueStack().push(obj);// root
		}

		public void set(String key, Object obj) {
			ActionContext.getContext().getValueStack().set(key, obj);// root 将一个map集合 存放在栈顶
		}

		// 目标数据存放 值栈 下方 map结构
		public void put(String key, Object obj) {
			ActionContext.getContext().getValueStack().getContext().put(key, obj);
		}

		// json序列化 ....

		// 下载 封装 response对象
		public HttpServletResponse getResponse() {
			return ServletActionContext.getResponse();
		}
		
		//下载  两头一流
		public void download(String path){
			String filename = path.substring(path.lastIndexOf("\\"));
			String url = path.substring(0, path.lastIndexOf("\\"));
			HttpServletResponse response = getResponse();
			
			try {
				response.setHeader("Content-Disposition", "attachment;filename="+
					DownLoadUtils.getAttachmentFileName(filename, ServletActionContext.getRequest().getHeader("user-agent")));
				response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));
				
				ServletOutputStream outputStream = response.getOutputStream();
				InputStream in = new FileInputStream(url);
				int len;
				byte[] arr = new byte[1024*4];
				while((len = in.read(arr)) != -1){
					outputStream.write(arr, 0, len);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 分页请求属性驱动
		protected int page; // 页码
		protected int rows; // 每页 记录数

		public void setPage(int page) {
			this.page = page;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}
}
