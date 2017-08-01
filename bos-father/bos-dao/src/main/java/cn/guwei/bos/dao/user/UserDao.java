package cn.guwei.bos.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.guwei.bos.domain.user.User;

public interface UserDao extends JpaRepository<User, Integer> {

	//关键字  不写语句
//	User findUserByEmailAndPassword(String username, String password);


	//jpql语句   直接在方法上 使用注解  (最常用)
	@Query("from User where email = ?1 and password = ?2")
	User login(String email, String password);

	@Query("from User where telephone = ?1")
	User findUserByTelephone(String telephone);

	@Modifying
	@Query("update User set password = ?2 where telephone = ?1")
	void updatePwd(String telephone, String password);

	@Modifying
	@Query("update User set password = ?2 where email = ?1")
	void editPwd(String email, String password);

	User findByEmail(String email);

	// 在目标查询实体类上  用jpql语句 @nameQuery  
//	User findUserByUsernameAndPassword(String username, String password);
	
	//sql查询
//	@Query(nativeQuery=true,value="select * from t_user where email=? and password=?")
//	User findUserByUsernameAndPassword(String username, String password);
	
	//占位符
//	@Query("from User where email=:email and password=:password")
//	User findUserByUsernameAndPassword(@Param("email")String username, @Param("password")String password);
	
}
