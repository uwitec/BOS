package cn.guwei.bos.service.bc.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.bc.DecidedzoneDao;
import cn.guwei.bos.dao.bc.SubareaDao;
import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.service.bc.DecidedzoneService;
@Service("decidedzoneService")
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {

	@Autowired
	private DecidedzoneDao decidedzoneDao;

	@Autowired
	private SubareaDao subareaDao;
	

	@Override
	public Page<Decidedzone> pageQuery(Specification<Decidedzone> spec, PageRequest pageRequest) {
		Page<Decidedzone> page = decidedzoneDao.findAll(spec, pageRequest);
		List<Decidedzone> content = page.getContent();
		for (Decidedzone d : content) {
			Hibernate.initialize(d.getStaff());
		}
		return page;
	}

	@Override
	public void save(String[] sid, Decidedzone model) {
		decidedzoneDao.save(model);
		if (sid!=null&&sid.length!=0) {
			for(String id : sid){
				subareaDao.addDecidedzoneId(id,model);
			}
		}
	}

	
	
}
