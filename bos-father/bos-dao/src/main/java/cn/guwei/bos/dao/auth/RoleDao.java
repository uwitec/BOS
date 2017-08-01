package cn.guwei.bos.dao.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.guwei.bos.domain.auth.Role;

public interface RoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

}
