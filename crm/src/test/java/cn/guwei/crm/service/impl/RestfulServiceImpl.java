package cn.guwei.crm.service.impl;

import java.util.List;

import cn.guwei.crm.domain.Car;
import cn.guwei.crm.domain.Users;
import cn.guwei.crm.service.RestfulService;

public class RestfulServiceImpl implements RestfulService{

	@Override
	public void save(Users user) {
		System.out.println("save:"+user);
	}

	@Override
	public void delete(int id) {
		System.out.println("delete:"+id);
	}

	@Override
	public Users update(Users user) {
		System.out.println("update:"+user);
		return null;
	}

	@Override
	public Users findOne(int id, Car car) {
		System.out.println("findOne:"+car+"---"+id);
		return null;
	}

	@Override
	public List<Users> findAll() {
		System.out.println("findAll:");
		return null;
	}

	
}
