package cn.guwei.bos.service.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.guwei.bos.domain.auth.Role;

public interface RoleService {

	void save(Role model, String menusIds, String[] functionIds);

	Page<Role> findAll(PageRequest pageRequest);

	List<Role> findAll();

}
