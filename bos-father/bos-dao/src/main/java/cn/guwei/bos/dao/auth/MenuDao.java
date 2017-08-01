package cn.guwei.bos.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.guwei.bos.domain.auth.Menu;

public interface MenuDao extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {

	@Query("from Menu where generatemenu = 1 order by zindex desc")
	List<Menu> ajaxListHasSonMenus();

}
