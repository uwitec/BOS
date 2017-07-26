package cn.guwei.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.guwei.bos.domain.bc.Standard;

public interface StandardService {
	public void save(Standard standard);

	public Page<Standard> listStandardPage(Pageable pageable);

	public void delBatch(String[] arr);

	public void startBatch(String[] arr);

	public List<Standard> findAll();
}
