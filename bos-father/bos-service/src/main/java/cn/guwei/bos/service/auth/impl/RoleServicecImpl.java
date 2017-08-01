package cn.guwei.bos.service.auth.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.auth.RoleDao;
import cn.guwei.bos.domain.auth.Function;
import cn.guwei.bos.domain.auth.Menu;
import cn.guwei.bos.domain.auth.Role;
import cn.guwei.bos.service.auth.RoleService;
@Service("roleService")
@Transactional
public class RoleServicecImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;

	@Override
	public void save(Role model, String menusIds, String[] functionIds) {
		//3张表更新  role  role_menu  role_function
		//jpa 操作集合 ==> 操作中间表
		roleDao.saveAndFlush(model);
		
		if (StringUtils.isNotBlank(menusIds)) {
			Set<Menu> menus = model.getMenus();
			for (String id : menusIds.split(",")) {
				Menu menu = new Menu();
				menu.setId(id);
				menus.add(menu);
			}
		}
		
		if (functionIds!=null&&functionIds.length>0) {
			Set<Function> functions = model.getFunctions();
			for (String id : functionIds) {
				Function function = new Function();
				function.setId(id);
				functions.add(function);
			}
		}
	}

	@Override
	public Page<Role> findAll(PageRequest pageRequest) {
		return roleDao.findAll(pageRequest);
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}
}
