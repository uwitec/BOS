package cn.guwei.bos.service.facede;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.guwei.bos.service.user.UserService;

@Service("facedeService")
public class FacedeService {

	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}
	
	
	
}
