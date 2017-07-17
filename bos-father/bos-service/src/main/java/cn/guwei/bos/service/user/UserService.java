package cn.guwei.bos.service.user;

import java.util.List;

import cn.guwei.bos.domain.User;



public interface UserService {
	public void save(User user);

	public void delete(User user);

	public User findUserById(Integer id);

	public List<User> findAll();

	// 业务 登录
	public User findUserByEmailAndPassword(String username, String password);
	
	public User findUserByTelephone(String telephone);

	public void updatePassword(String telephone, String password);
}
