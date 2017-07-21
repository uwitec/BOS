package cn.guwei.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.guwei.bos.domain.bc.Region;

public interface RegionService {

	void save(List<Region> regions);

	Page<Region> findAll(PageRequest pageable);

	Region findRegionById(String id);

	Region findRegionByPostcode(String postcode);

}
