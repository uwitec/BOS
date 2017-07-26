package cn.guwei.bos.service.bc.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.guwei.bos.dao.bc.RegionDao;
import cn.guwei.bos.domain.bc.Region;
import cn.guwei.bos.service.bc.RegionService;
import cn.guwei.bos.service.facede.FacedeService;
import cn.guwei.redis.utils.RedisCRUD;

@Service("regionService")
@Transactional
public class RegionServiceImppl implements RegionService {

	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private RedisCRUD redisCRUD;
	
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

	@Override
	public void add(Region model) {
		regionDao.save(model);
	}

	@Override
	public Page<Region> findAll(PageRequest pageRequest, Specification<Region> spec) {
		return regionDao.findAll(spec,pageRequest);
	}


	@Override
	public List<Region> findAll(String q) {
		return regionDao.findRegionLikeq("%"+q+"%");
	}

	@Override
	public List<Region> findAll() {
		return regionDao.findAll();
	}

	@Override
	public String pageQueryByRedis(PageRequest pageRequest, Specification<Region> spec) {
		int pageNumber = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		String pagekey = pageNumber+"_"+pageSize+"_"+spec.toString();
		String jsonString = redisCRUD.GetJSONStringFromRedis(pagekey);
		if (StringUtils.isBlank(jsonString)) {
			Page<Region> findAll = regionDao.findAll(spec, pageRequest);
			HashMap<String, Object> map = new HashMap<>();
			map.put("total", findAll.getTotalElements());
			map.put("rows", findAll.getContent());
			jsonString = JSON.toJSONString(map);
			redisCRUD.writeJSONStringToRedis(pagekey, jsonString);
		}		
			
		
		return jsonString;
	}
	
}
