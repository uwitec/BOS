package cn.guwei.bos.service.bc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.guwei.bos.domain.bc.Staff;

public interface StaffService {

	Staff findStaffById(String string);

	void saveStaff(Staff model);

	Page<Staff> findAll(PageRequest pageable);

	void delBatch(String[] arr);

	void startBatch(String[] arr);

}