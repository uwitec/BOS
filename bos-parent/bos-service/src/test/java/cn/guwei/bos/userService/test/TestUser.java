package cn.guwei.bos.userService.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.guwei.Service.UserService;
import cn.guwei.bos.domain.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:applicationContext-domain.xml",
		"classpath:applicationContext-dao.xml",
		"classpath:applicationContext-service.xml"})

public class TestUser {

	@Autowired
	private UserService userService;
	
	@Test
	public void testsave(){
		User user = new User();
		user.setEmail("111@163.com");
		user.setPassword("weqw");
		user.setTelephone("12123124");
		userService.save(user);
	}
	@Test
	public void delete(){
		userService.delete(1);
	}
	
	@Test
	public void findUserByNameAndPwd(){
		User user = userService.findUserByNameAndPassword("111@163.com", "123");
		System.out.println(user.getEmail());
	}
}
