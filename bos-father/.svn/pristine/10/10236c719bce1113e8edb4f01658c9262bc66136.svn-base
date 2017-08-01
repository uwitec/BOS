package cn.guwei.bos.service.qp.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.activemq.Message;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.bc.DecidedzoneDao;
import cn.guwei.bos.dao.bc.RegionDao;
import cn.guwei.bos.dao.qp.NoticebillDao;
import cn.guwei.bos.dao.qp.WorkbillDao;
import cn.guwei.bos.domain.bc.Decidedzone;
import cn.guwei.bos.domain.bc.Region;
import cn.guwei.bos.domain.bc.Staff;
import cn.guwei.bos.domain.bc.Subarea;
import cn.guwei.bos.domain.customer.Customers;
import cn.guwei.bos.domain.qp.Noticebill;
import cn.guwei.bos.domain.qp.Workbill;
import cn.guwei.bos.service.base.BaseInterface;
import cn.guwei.bos.service.qp.NoticebillService;
@SuppressWarnings("all")
@Service("noticebillService")
@Transactional
public class NoticebillServiceImpl implements NoticebillService {

	@Autowired
	private DecidedzoneDao decidedzoneDao;
	
	@Autowired
	private WorkbillDao workbillDao;
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private NoticebillDao noticebillDao;
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	protected JmsTemplate jmsQueueTemplate;
	
	@Override
	public Customers findCustomerByTel(String telephone) {
		String url = BaseInterface.CRM_BASE_URL+"/findCustomerByTel/"+telephone;
		Customers c = WebClient.create(url).accept(MediaType.APPLICATION_JSON).get(Customers.class);
		return c;
	}

	@Override
	public void saveNoticebill(final Noticebill model, String province, String city, String county) {
		
		noticebillDao.saveAndFlush(model);  //瞬时态 -->持久态  拥有了OID
		
		//1、更新客户信息，获得customerid
		if (StringUtils.isNotBlank(model.getCustomerId()+"")) {
			String url = BaseInterface.CRM_BASE_URL+"/updateCustomerById/"+model.getCustomerId()+"/"+model.getCustomerName()+"/"+model.getPickaddress();
			WebClient.create(url).put(null);
		} else {
			String url = BaseInterface.CRM_BASE_URL+"/saveCustomer";
			Customers customer = new Customers();
			customer.setName(model.getCustomerName());
			customer.setTelephone(model.getTelephone());
			customer.setAddress(model.getPickaddress());
			customer.setStation("英皇娱乐");
			Response post = WebClient.create(url).accept(MediaType.APPLICATION_JSON).post(customer);
			Customers entity = post.readEntity(Customers.class);
			model.setCustomerId(entity.getId());
		}
		//2、自动分单 
		//第一种方法：通过crm系统匹配
		String url = BaseInterface.CRM_BASE_URL+"/findCustomerByAddress/"+model.getPickaddress();
		List<Customers> customers = (List<Customers>) WebClient.create(url).accept(MediaType.APPLICATION_JSON).getCollection(Customers.class);
		if (customers!=null&&customers.size()>0) {
			for (Customers c : customers) {
				String decidedzoneId = c.getDecidedzoneId();
				if (StringUtils.isNotBlank(decidedzoneId)) {
					Decidedzone decidedzone = decidedzoneDao.findOne(decidedzoneId);
					Staff staff = decidedzone.getStaff();
					model.setStaff(staff);
					model.setOrdertype("自动录入");
					//自动分配完成    生成工单
					Workbill bill = new Workbill();
					bill.setAttachbilltimes(0);  //催单的次数
					bill.setBuildtime(new Date(System.currentTimeMillis()));
					bill.setPickstate("新单");
					bill.setNoticebill(model);  //生成bill他的外键是noticebill的主键，而此时noticebill的主键是自动生成的没有保存就没有主键，所以一开始把它变成持久态
					bill.setRemark(model.getRemark());
					bill.setStaff(staff);
					bill.setType("新");
					workbillDao.save(bill);  //工作单位录入完成
					
					// 发送短信mq
					jmsQueueTemplate.send("bos_sms", new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							MapMessage message = session.createMapMessage();
							message.setString("telephone", model.getTelephone());
							message.setString("code", "9999");
							return (Message) message;
						}
					});
					
					return ;
				}
			}
		}
		//第二种方法：通过区域完全匹配
		Region region = regionDao.findByPCC(province,city,county);
		Set<Subarea> subareas = region.getSubareas();
		if (subareas!=null&&subareas.size()>0) {
			for (Subarea subarea : subareas) {
				String addresskey = subarea.getAddresskey();
				if (model.getPickaddress().contains(addresskey)) {
					Decidedzone decidedzone = subarea.getDecidedzone();
					if (decidedzone!=null) {
						model.setStaff(decidedzone.getStaff());
						model.setOrdertype("自动录入");
						Workbill bill = new Workbill();
						bill.setAttachbilltimes(0);  //催单的次数
						bill.setBuildtime(new Date(System.currentTimeMillis()));
						bill.setPickstate("新单");
						bill.setNoticebill(model);
						bill.setRemark(model.getRemark());
						bill.setStaff(decidedzone.getStaff());
						bill.setType("新");
						workbillDao.save(bill);  //工作单位录入完成
						
						// 发送短信mq
						jmsQueueTemplate.send("bos_sms", new MessageCreator() {
							@Override
							public Message createMessage(Session session) throws JMSException {
								MapMessage message = session.createMapMessage();
								message.setString("telephone", model.getTelephone());
								message.setString("code", "8888");
								return (Message) message;
							}
						});
						
						return ;
					}
				}
			}
		}
		model.setOrdertype("人工录入");
	}
	
	

}
