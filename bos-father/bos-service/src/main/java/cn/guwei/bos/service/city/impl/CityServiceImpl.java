package cn.guwei.bos.service.city.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.guwei.bos.dao.city.CityDao;
import cn.guwei.bos.domain.city.City;
import cn.guwei.bos.service.city.CityService;
import cn.guwei.redis.utils.RedisCRUD;
@Service
@Transactional
public class CityServiceImpl implements CityService{

	@Autowired
	private CityDao cityDao;

	@Autowired
	private RedisCRUD redisCRUD;
	
	@Override
	public String findByRedis(Integer pid) {
		String citykey = pid + "";
		String citystring = redisCRUD.GetJSONStringFromRedis(citykey);
		if (StringUtils.isBlank(citystring)) {
			List<City> citys = cityDao.findByPid(pid);
			citystring = JSON.toJSONString(citys);
			redisCRUD.writeJSONStringToRedis(citykey, citystring);
		}
		return citystring;
	}
	
	
}
