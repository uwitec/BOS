package cn.guwei.bos.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.user.UserDao;
import cn.guwei.bos.domain.User;
import cn.guwei.bos.service.user.UserService;
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public User findUserById(Integer id) {
		return userDao.findOne(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		//关键字查询
//		User user = userDao.findUserByEmailAndPassword(username,password);
		//jpql语句查询  
		User user = userDao.findUserByUsernameAndPassword(username,password);
		
		
		return user;
				
	}

}
