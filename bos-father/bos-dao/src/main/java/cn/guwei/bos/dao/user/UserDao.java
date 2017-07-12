package cn.guwei.bos.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.guwei.bos.domain.User;

public interface UserDao extends JpaRepository<User, Integer> {

	//关键字  不写语句
	User findUserByEmailAndPassword(String username, String password);


	//jpql语句   直接在方法上 使用注解
//	@Query("from User where email = ?1 and password = ?2")
//	User findUserByUsernameAndPassword(String username, String password);

	// 在目标查询实体类上  用jpql语句 @nameQuery  (最常用)
	User findUserByUsernameAndPassword(String username, String password);
	
	//sql查询
//	@Query(nativeQuery=true,value="select * from t_user where email=? and password=?")
//	User findUserByUsernameAndPassword(String username, String password);
	
	//占位符
//	@Query("from User where email=:email and password=:password")
//	User findUserByUsernameAndPassword(@Param("email")String username, @Param("password")String password);
	
}
