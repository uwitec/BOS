package cn.guwei.bos.service.facede;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.service.user.UserService;

@Service("facedeService")
@Transactional
public class FacedeService {

	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}
	
	
	
}