package cn.guwei.bos.dao.bc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.guwei.bos.domain.bc.Standard;

public interface StandardDao extends JpaRepository<Standard, Integer>{

	@Modifying
	@Query("update Standard set deltag = 0 where id = ?1")
	void delBatch(int parseInt);

	@Modifying
	@Query("update Standard set deltag = 1 where id = ?1")
	void startBatch(int parseInt);

	@Query("from Standard where deltag = 1")
	List<Standard> findAllStateOn();

}
