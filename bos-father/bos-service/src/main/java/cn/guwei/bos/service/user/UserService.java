package cn.guwei.bos.service.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.guwei.bos.domain.user.User;



public interface UserService {
	public void save(User user);

	public void delete(User user);

	public User findUserById(Integer id);

	public List<User> findAll();

	// 业务 登录
	public User findUserByEmailAndPassword(String username, String password);
	
	public User findUserByTelephone(String telephone);

	public void updatePassword(String telephone, String password);

	public void editPassword(String email, String password);

	public User findUserByEmail(String email);

	public void save(User model, String[] values);

	public Page<User> findAll(PageRequest pageRequest);
}
