package cn.guwei.bos.dao.bc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.guwei.bos.domain.bc.Staff;

public interface StaffDao extends JpaRepository<Staff, String> {

	@Modifying
	@Query("update Staff set deltag = 0 where id = ?1")
	void delBatch(String arr);

	@Modifying
	@Query("update Staff set deltag = 1 where id = ?1")
	void startBatch(String arr);


}
