package cn.guwei.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import cn.guwei.crm.domain.Car;
import cn.guwei.crm.domain.Users;
@Produces("/")
public interface RestfulService {

	@POST
	@Path("/user")
	@Consumes({"application/xml","application/json"})
	public void save(Users user);
	
	@DELETE
	@Path("/user/{id}")
	@Consumes({"application/xml","application/json"})
	public void delete(@PathParam("id")int id);
	
	@PUT
	@Path("/user")
	@Consumes({"application/xml","application/json"})
	@Produces({"application/xml","application/json"})
	public Users update(Users user);
	
	@GET
	@Path("/user/{id}/{car}")
	@Consumes({"application/xml","application/json"})
	@Produces({"application/xml","application/json"})
	public Users findOne(@PathParam("id")int id,@PathParam("car")Car car);
	
	@GET
	@Path("/user")
	@Produces({"application/xml","application/json"})
	public List<Users> findAll();
}
