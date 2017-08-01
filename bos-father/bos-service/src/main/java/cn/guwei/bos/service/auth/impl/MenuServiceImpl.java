package cn.guwei.bos.service.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.auth.MenuDao;
import cn.guwei.bos.domain.auth.Menu;
import cn.guwei.bos.service.auth.MenuService;
@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> findAll() {
		return menuDao.findAll();
	}

	@Override
	public Page<Menu> pageQuery(PageRequest pageRequest) {
		return menuDao.findAll(pageRequest);
	}

	@Override
	public void save(Menu model) {
		menuDao.save(model);
	}

	@Override
	public List<Menu> ajaxListHasSonMenus() {
		return menuDao.ajaxListHasSonMenus();
	}
}
