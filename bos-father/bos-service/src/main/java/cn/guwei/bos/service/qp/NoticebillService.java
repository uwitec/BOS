package cn.guwei.bos.service.qp;

import cn.guwei.bos.domain.customer.Customers;
import cn.guwei.bos.domain.qp.Noticebill;

public interface NoticebillService {

	Customers findCustomerByTel(String telephone);

	void saveNoticebill(Noticebill model, String province, String city, String county);

}
