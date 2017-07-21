package cn.guwei.bos.service.bc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.bc.RegionDao;
import cn.guwei.bos.domain.bc.Region;
import cn.guwei.bos.service.bc.RegionService;

@Service("regionService")
@Transactional
public class RegionServiceImppl implements RegionService {

	@Autowired
	private RegionDao regionDao;

	@Override
	public void save(List<Region> regions) {
		regionDao.save(regions);
	}

	@Override
	public Page<Region> findAll(PageRequest pageable) {
		return regionDao.findAll(pageable);
	}

	@Override
	public Region findRegionById(String id) {
		return regionDao.findOne(id);
	}

	@Override
	public Region findRegionByPostcode(String postcode) {
		return regionDao.findByPostcode(postcode);
	}
	
}
