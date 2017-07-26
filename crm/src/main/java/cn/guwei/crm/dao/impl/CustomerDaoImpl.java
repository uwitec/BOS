package cn.guwei.crm.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.guwei.crm.dao.CustomerDao;
import cn.guwei.crm.domain.Customers;
@Repository
@SuppressWarnings("all")
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {
	
	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public List<Customers> getNoAssociationsCD(){
		List<Customers> list = getHibernateTemplate().find(" from Customers where decidedzoneId is null ");
		return list.isEmpty()?null:list;
	}

	public List<Customers> getAssociationsCD(String decidedzoneId) {
		List<Customers> list = getHibernateTemplate().find(" from Customers  where decidedzoneId =? ",decidedzoneId);
		return list.isEmpty()?null:list;
	}

	public void assignedAssociationCD(String customerId, String decidedzoneId) {
		getSession().createQuery(" update Customers set decidedzoneId = ? where id = ? ").setParameter(0, decidedzoneId).setParameter(1, Integer.parseInt(customerId)).executeUpdate();
	}

	public void cancleAssociationCD(String decidedzoneId) {
		getSession().createQuery(" update Customers set decidedzoneId = null where decidedzoneId = ? ").setParameter(0, decidedzoneId).executeUpdate();
	}

	@Override
	public List<Customers> findCustomerByDid(String did) {
		List<Customers> list = getSession().createQuery(" from Customers where decidedzoneId = ?").setParameter(0, did).list();
		return list;
	}

}
