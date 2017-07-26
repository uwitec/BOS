package cn.guwei.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.guwei.bos.domain.bc.Region;
import cn.guwei.bos.domain.bc.Subarea;

public interface SubareaService {

	Page<Subarea> findAll(PageRequest pageRequest, Specification<Subarea> spec);

	void save(Subarea model);

	void save(List<Subarea> subareas);

	List<Subarea> findBySpecification(Specification<Subarea> spec);



	List<Subarea> ajaxListInfo();

}
