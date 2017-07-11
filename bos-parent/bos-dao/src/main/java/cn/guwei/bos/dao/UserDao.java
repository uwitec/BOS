package cn.guwei.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.guwei.bos.domain.user.User;

public interface UserDao extends JpaRepository<User, Integer>{


}
