package cn.guwei.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import cn.guwei.crm.domain.Customers;

/**crm所提供服务：
 * 1、查询未被定区关联的客户
 * 2、查询已被定区关联的客户
 * 3、修改客户与定区的关联关系 
 * @author vian
 */
@Produces("*/*")
public interface CustomerService {
	@GET
	@Path("/customer/getNoAssociationsCD")
	@Produces({ "application/xml", "application/json" })
	public List<Customers> getNoAssociationsCD();
	
	@GET
	@Path("/customer/getAssociationsCD/{decidedzoneId}")
	@Produces({ "application/xml", "application/json" })
	public List<Customers> getAssociationsCD(@PathParam("decidedzoneId")String decidedzoneId);
	
	@PUT
	@Path("/customer/assignedAssociationCD/{customerId}/{decidedzoneId}")
	@Consumes({ "application/xml", "application/json" })
	public void assignedAssociationCD(@PathParam("customerId")String customerId,@PathParam("decidedzoneId")String decidedzoneId);
	
}
