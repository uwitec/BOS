package cn.guwei.bos.dao.bc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.guwei.bos.domain.bc.Region;

public interface RegionDao extends JpaRepository<Region, String>,JpaSpecificationExecutor<Region>{

	@Query("from Region where postcode = ?1")
	Region findByPostcode(String postcode);

}
