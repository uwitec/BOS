package cn.guwei.bos.dao.bc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.domain.bc.Subarea;

public interface SubareaDao extends JpaRepository<Subarea, String>,JpaSpecificationExecutor<Subarea>{

	@Query("from Subarea where decidedzone is null")
	List<Subarea> findAllInUse();

	@Modifying
	@Query("update Subarea set decidedzone =?2 where id = ?1")
	void addDecidedzoneId(String id, Decidedzone model);

}
