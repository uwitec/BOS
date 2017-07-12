import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.guwei.bos.dao.user.UserDao;
import cn.guwei.bos.domain.User;
import cn.guwei.bos.service.facede.FacedeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:applicationContext-domain.xml",
		"classpath:applicationContext-dao.xml",
		"classpath:applicationContext-service.xml"})
public class testUserService {

	@Autowired
	private FacedeService facedeService;
	
	@Test
	public void savetest(){
		User user = new User();
		user.setEmail("gw@qq.com");
		user.setPassword("111");
		user.setTelephone("123");
		facedeService.getUserService().save(user);
	}
	
	@Test
	public void logintest(){
		User user = facedeService.getUserService().findUserByUsernameAndPassword("guwei@qq.com", "233");
		System.out.println(user.getTelephone());
	}
	
}
