package cn.guwei.bos.dao.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.guwei.bos.domain.auth.Function;

public interface FunctionDao extends JpaRepository<Function, String>,JpaSpecificationExecutor<Function> {

}
