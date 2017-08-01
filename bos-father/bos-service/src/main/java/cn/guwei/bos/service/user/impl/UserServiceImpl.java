package cn.guwei.bos.service.user.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.user.UserDao;
import cn.guwei.bos.domain.auth.Role;
import cn.guwei.bos.domain.user.User;
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
	public User findUserByEmailAndPassword(String email, String password) {
		//关键字查询
//		User user = userDao.findUserByEmailAndPassword(username,password);
		//jpql语句查询  
		User user = userDao.login(email,password);
		
		
		return user;
				
	}

	@Override
	public User findUserByTelephone(String telephone) {
		System.out.println(telephone);
		return userDao.findUserByTelephone(telephone);
	}

	@Override
	public void updatePassword(String telephone, String password) {
		System.out.println(telephone);
		System.out.println(password);
		userDao.updatePwd(telephone,password);
	}

	@Override
	public void editPassword(String email, String password) {
		userDao.editPwd(email,password);
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public void save(User model, String[] values) {
		userDao.saveAndFlush(model);
		if (values!=null&&values.length>0) {
			Set<Role> roles = model.getRoles();
			for (String roleid : values) {
				Role role = new Role();
				role.setId(roleid);
				roles.add(role);
			}
		}
	}

	@Override
	public Page<User> findAll(PageRequest pageRequest) {
		Page<User> page = userDao.findAll(pageRequest);
		return page;
	}

}
