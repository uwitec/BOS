package cn.guwei.bos.service.facede;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.service.bc.RegionService;
import cn.guwei.bos.service.bc.StaffService;
import cn.guwei.bos.service.bc.StandardService;
import cn.guwei.bos.service.user.UserService;

@Service("facedeService")
@Transactional
public class FacedeService {

	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}


	@Autowired
	private StandardService standardService;
	
	public StandardService getStandardService() {
		return standardService;
	}
	
	@Autowired
	private StaffService staffService;

	public StaffService getStaffService() {
		return staffService;
	}
	
	@Autowired
	private RegionService regionService;
	
	public RegionService getRegionService(){
		return regionService;
	}
	
}
