package cn.guwei.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.domain.customer.Customers;
import cn.guwei.bos.service.base.BaseInterface;

public interface DecidedzoneService extends BaseInterface{


	Page<Decidedzone> pageQuery(Specification<Decidedzone> spec, PageRequest pageRequest);

	void save(String[] sid, Decidedzone model);

	List<Customers> getNoAssociation();

	List<Customers> getAssociation(String string);

	void assignC2D(String[] customerIds, String id);

	List<Customers> findCustomer(String did);

}
