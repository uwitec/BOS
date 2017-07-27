package cn.guwei.bos.dao.bc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.guwei.bos.domain.bc.Decidedzone;

public interface DecidedzoneDao extends JpaRepository<Decidedzone, String>,JpaSpecificationExecutor<Decidedzone>{

	Decidedzone findById(String decidedzoneId);

}
