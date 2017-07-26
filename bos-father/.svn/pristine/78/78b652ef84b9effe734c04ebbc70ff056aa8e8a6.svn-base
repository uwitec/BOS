package cn.guwei.bos.service.bc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.bc.StaffDao;
import cn.guwei.bos.domain.bc.Staff;
import cn.guwei.bos.service.bc.StaffService;
@Service("staffService")
@Transactional
public class StaffServiceImpl implements StaffService{

	@Autowired
	private StaffDao staffDao;
	
	@Override
	public Staff findStaffById(String id) {
		Staff staff = staffDao.findOne(id);
		return staff;
	}

	@Override
	public void saveStaff(Staff model) {
		staffDao.save(model);
	}

	@Override
	public Page<Staff> findAll(Specification<Staff> specification,PageRequest pageable) {
		return staffDao.findAll(specification,pageable);
	}

	@Override
	public void delBatch(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			staffDao.delBatch(arr[i]);
		}
	}

	@Override
	public void startBatch(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			staffDao.startBatch(arr[i]);
		}
	}

	@Override
	public List<Staff> ajaxListInUse() {
		return staffDao.findAllInUse();
	}

	@Override
	public Staff findOneById(String id) {
		return staffDao.findOne(id);
	}

	@Override
	public Staff findStaffBytel(String telephone) {
		System.out.println(staffDao.findByTelephone(telephone));
		return staffDao.findByTelephone(telephone);
	}

}
