package cn.guwei.bos.dao.qp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.guwei.bos.domain.qp.Noticebill;

public interface NoticebillDao extends JpaRepository<Noticebill, String>,JpaSpecificationExecutor<Noticebill> {

}
