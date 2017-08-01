package cn.guwei.bos.service.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.guwei.bos.dao.auth.FunctionDao;
import cn.guwei.bos.domain.auth.Function;
import cn.guwei.bos.service.auth.FunctionService;

@Service("functionService")
@Transactional
public class FunctionServiceImpl implements FunctionService{

	@Autowired
	private FunctionDao functionDao;

	@Override
	public void save(Function model) {
		functionDao.save(model);
	}

	@Override
	public Page<Function> pageQuery(PageRequest pageRequest) {
		return functionDao.findAll(pageRequest);
	}

	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}
}
