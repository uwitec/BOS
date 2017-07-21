package cn.guwei.bos.service.bc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.bc.StandardDao;
import cn.guwei.bos.domain.bc.Standard;
import cn.guwei.bos.service.bc.StandardService;
@Service("standardService")
@Transactional
public class StandardServiceImpl implements StandardService{

	@Autowired
	private StandardDao standardDao;
	
	@Override
	public void save(Standard standard) {
		standardDao.save(standard);
	}

	@Override
	public Page<Standard> listStandardPage(Pageable pageable) {
		return standardDao.findAll(pageable);
	}

	@Override
	public void delBatch(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			standardDao.delBatch(Integer.parseInt(arr[i]));
		}
	}

	@Override
	public void startBatch(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			standardDao.startBatch(Integer.parseInt(arr[i]));
		}
	}

	@Override
	public List<Standard> findAll() {
		return standardDao.findAllStateOn();
	}

}
