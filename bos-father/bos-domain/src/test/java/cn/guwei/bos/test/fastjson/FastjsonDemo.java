package cn.guwei.bos.test.fastjson;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import cn.guwei.bos.domain.user.User;

public class FastjsonDemo {

	public static void main(String[] args) {
		//run1();
		//run2();
		run3();
	}

	private static void run3() {
		User user = new User();
		user.setId(001);
		user.setEmail("gg@163.com");
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class);
		Set<String> excludes = filter.getExcludes();  //不序列化
		Set<String> includes = filter.getIncludes();  //序列化
		excludes.add("id");
		includes.add("email");
		String string = JSON.toJSONString(user,filter);
		System.out.println(string);
	}

	private static void run2() {
		User user = new User();
		user.setId(001);
		user.setEmail("gg@163.com");
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class,"email");
		String string = JSON.toJSONString(user,filter);
		System.out.println(string);
	}

	private static void run1() {
		User user = new User();
		user.setId(001);
		user.setEmail("gg@163.com");
		String string = JSON.toJSONString(user);
		System.out.println(string);
	}
	
}
