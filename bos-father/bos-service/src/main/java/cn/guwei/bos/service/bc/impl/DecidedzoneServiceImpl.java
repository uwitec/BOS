package cn.guwei.bos.service.bc.impl;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.bc.DecidedzoneDao;
import cn.guwei.bos.dao.bc.SubareaDao;
import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.domain.customer.Customers;
import cn.guwei.bos.service.bc.DecidedzoneService;
@SuppressWarnings("all")
@Service("decidedzoneService")
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {

	@Autowired
	private DecidedzoneDao decidedzoneDao;

	@Autowired
	private SubareaDao subareaDao;
	

	@Override
	public Page<Decidedzone> pageQuery(Specification<Decidedzone> spec, PageRequest pageRequest) {
		Page<Decidedzone> page = decidedzoneDao.findAll(spec, pageRequest);
		List<Decidedzone> content = page.getContent();
		for (Decidedzone d : content) {
			Hibernate.initialize(d.getStaff());
		}
		return page;
	}

	@Override
	public void save(String[] sid, Decidedzone model) {
		decidedzoneDao.save(model);
		if (sid!=null&&sid.length!=0) {
			for(String id : sid){
				subareaDao.addDecidedzoneId(id,model);
			}
		}
	}

	@Override
	public List<Customers> getNoAssociation() {
		String url = CRM_BASE_URL+"/getNoAssociationsCD";
		List<Customers> list = 
				(List<Customers>) WebClient.create(url).accept(MediaType.APPLICATION_JSON).getCollection(Customers.class);
		return list;
	}

	@Override
	public List<Customers> getAssociation(String string) {
		String url = CRM_BASE_URL+"/getAssociationsCD/"+string;
		List<Customers> list = 
				(List<Customers>) WebClient.create(url).accept(MediaType.APPLICATION_JSON).getCollection(Customers.class);
		return list;
	}

	@Override
	public void assignC2D(String[] customerIds, String id) {
		String url = CRM_BASE_URL+"/assignedAssociationCD/";
		if (customerIds!=null&&customerIds.length>0) {
			StringBuffer sb = new StringBuffer();
			for (String cid : customerIds) {
				sb.append(cid).append(",");
			}
			String idstring = sb.substring(0, sb.length()-1);
			url = url+idstring+"/"+id;
		} else {
			url = url + "none/"+ id;
		}
		
		WebClient.create(url).put(null);
	}

	@Override
	public List<Customers> findCustomer(String did) {
		String url = CRM_BASE_URL+"/findCustomerByDid/"+did;
		List<Customers> list = (List<Customers>)WebClient.create(url).getCollection(Customers.class);
		return list;
	}

	
	
}
