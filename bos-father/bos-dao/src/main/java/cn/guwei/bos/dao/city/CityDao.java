package cn.guwei.bos.dao.city;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.guwei.bos.domain.city.City;

public interface CityDao extends JpaRepository<City, Integer>{

	List<City> findByPid(Integer pid);

}
