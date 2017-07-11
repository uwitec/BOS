package cn.guwei.Service;

import java.util.List;

import cn.guwei.bos.domain.user.User;

public interface UserService {
    public void save(User user);
    
    public void delete(Integer id);
    
    public User findUserById(Integer id);
    
    public List<User> findAll();
    
    public User findUserByNameAndPassword(String name,String password);
    
}
