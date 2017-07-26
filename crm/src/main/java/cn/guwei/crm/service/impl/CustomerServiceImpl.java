package cn.guwei.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.guwei.crm.dao.CustomerDao;
import cn.guwei.crm.domain.Customers;
import cn.guwei.crm.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public List<Customers> getNoAssociationsCD() {
		return customerDao.getNoAssociationsCD();
	}

	@Override
	public List<Customers> getAssociationsCD(String decidedzoneId) {
		return customerDao.getAssociationsCD(decidedzoneId);
	}

	@Override
	public void assignedAssociationCD(String customerId, String decidedzoneId) {
		customerDao.cancleAssociationCD(decidedzoneId);
		if ("none".equalsIgnoreCase(customerId)) {
			System.out.println("no 客户--");
			return; 
		}
		//isNoneBlank 
		if (StringUtils.isNoneBlank(customerId)) {
			String customerIds[] = customerId.split(",");
			for (String id : customerIds) {
				customerDao.assignedAssociationCD(id, decidedzoneId);
			}
		}
	}

	@Override
	public List<Customers> findCustomerByDid(String did) {
		return customerDao.findCustomerByDid(did);
	}


}
