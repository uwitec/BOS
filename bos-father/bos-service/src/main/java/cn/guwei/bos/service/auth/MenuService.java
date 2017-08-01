package cn.guwei.bos.service.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.guwei.bos.domain.auth.Menu;

public interface MenuService {

	List<Menu> findAll();

	Page<Menu> pageQuery(PageRequest pageRequest);

	void save(Menu model);

	List<Menu> ajaxListHasSonMenus();

}
