package cn.guwei.bos.service.bc.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.bc.SubareaDao;
import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.domain.bc.Subarea;
import cn.guwei.bos.service.bc.SubareaService;
@Service("subarea")
@Transactional
public class SubareaServiceImpl implements SubareaService {

	@Autowired
	private SubareaDao subareaDao;

	@Override
	public Page<Subarea> findAll(PageRequest pageRequest, Specification<Subarea> spec) {
		Page<Subarea> page = subareaDao.findAll(spec, pageRequest);
		for (Subarea subarea : page.getContent()) {
			Hibernate.initialize(subarea.getRegion());
		}
		return page;
	}

	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	@Override
	public void save(List<Subarea> subareas) {
		subareaDao.save(subareas);
	}

	@Override
	public List<Subarea> findBySpecification(Specification<Subarea> spec) {
		List<Subarea> list = subareaDao.findAll(spec);
		for (Subarea s : list) {
			Hibernate.initialize(s.getRegion());
		}
		return list;
	}

	@Override
	public List<Subarea> ajaxListInfo() {
		return subareaDao.findAllInUse();
	}

	@Override
	public List<Subarea> findByDecidedzone(Decidedzone decidedzone) {
		List<Subarea> list = subareaDao.findByDecidedzone(decidedzone);
		if (list!=null && list.size()>0) {
			for (Subarea s : list) {
				Hibernate.initialize(s.getRegion());
			}
		} 
		return list;
	}

	
}
