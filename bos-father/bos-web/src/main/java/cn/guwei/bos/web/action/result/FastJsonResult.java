package cn.guwei.bos.web.action.result;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;

//第一步:自定义结果集编码如下

@SuppressWarnings("all")

public class FastJsonResult implements Result {

	private String root;// 获取值栈 需要序列化json的对象
	public void setRoot(String root) {
		this.root = root;
	}

	protected Set<String> excludeProperties = Collections.emptySet();// 不需要序列化属性名添加
																		// 的集合
	protected Set<String> includeProperties = Collections.emptySet();// 需要序列化json字符串的集合添加

	public Set<String> getExcludeProperties() {
		return excludeProperties;
	}

	public void setExcludeProperties(String excludeProperties) {
		// 该TextParseUtil 由struts2 框架提供 用于切割 逗号分隔的字符串 该方法来源 struts2 自带的拦截器源码
		// MethodFilterIntercepter
		this.excludeProperties = TextParseUtil.commaDelimitedStringToSet(excludeProperties);
	}

	public Set<String> getIncludeProperties() {
		return includeProperties;
	}

	public void setIncludeProperties(String includeProperties) {
		this.includeProperties = TextParseUtil.commaDelimitedStringToSet(includeProperties);
	}

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		// HttpServletRequest request = (HttpServletRequest)
		// actionContext.get(StrutsStatics.HTTP_REQUEST);
		// 获取reponse对象
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

		try {
			Object rootObject;
			rootObject = findRootObject(invocation);// 来源于源码JsonResult插件结果集截取
													// 获取值栈中的序列化json的对象
			response.setContentType("text/json;charset=utf-8");// json字符串中文乱码处理
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter();// 阿里提供的自定义需要序列化json的属性对象
			if (includeProperties != null && includeProperties.size() != 0) {
				for (String in : includeProperties) {
					filter.getIncludes().add(in);// 将需要的属性添加即可
				}
			}
			if (excludeProperties != null && excludeProperties.size() != 0) {
				for (String ex : excludeProperties) {
					filter.getExcludes().add(ex);// 不需要序列化json字符串的集合添加
				}
			}
			String jsonString = JSON.toJSONString(rootObject, filter,SerializerFeature.DisableCircularReferenceDetect);// 阿里提供的序列化json字符串对象
																		// 使用构造方法
			// 构造方法将需要序列化的对象 以及相关自定义序列化Json的配置filter
			System.out.println("------fastjson 序列化的字段 = " + jsonString + "----序列化信息ok!");
			response.getWriter().write(jsonString);// 使用response对象将json字符串
													// 回送给ajax引擎
		} catch (Exception exception) {
			throw exception;
		}

	}

	protected Object findRootObject(ActionInvocation invocation) {
		Object rootObject;
		if (this.root != null) {
			ValueStack stack = invocation.getStack();
			rootObject = stack.findValue(root);
		} else {
			rootObject = invocation.getStack().peek(); // model overrides action
		}
		return rootObject;
	}
}
