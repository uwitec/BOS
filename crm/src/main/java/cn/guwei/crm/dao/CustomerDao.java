package cn.guwei.crm.dao;

import java.util.List;

import javax.ws.rs.PathParam;

import cn.guwei.crm.domain.Customers;

public interface CustomerDao {

	public List<Customers> getNoAssociationsCD();
	
	public List<Customers> getAssociationsCD(@PathParam("decidedzoneId")String decidedzoneId);
	
	public void assignedAssociationCD(@PathParam("customerId")String customerId,@PathParam("decidedzoneId")String decidedzoneId);
	
	public void cancleAssociationCD(String decidedzoneId);

	public List<Customers> findCustomerByDid(String did);
}
