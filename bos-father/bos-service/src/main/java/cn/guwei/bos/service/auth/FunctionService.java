package cn.guwei.bos.service.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.guwei.bos.domain.auth.Function;

public interface FunctionService {

	void save(Function model);

	Page<Function> pageQuery(PageRequest pageRequest);

	List<Function> findAll();

}
