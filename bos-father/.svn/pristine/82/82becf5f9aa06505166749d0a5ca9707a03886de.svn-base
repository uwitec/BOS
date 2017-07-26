package cn.guwei.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
@Component
public class RedisCRUDImpl implements RedisCRUD{

	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Override
	public void writeJSONStringToRedis(String key, String jsonString) {
		redisTemplate.opsForValue().set(key, jsonString);
	}

	@Override
	public void writeObjectToRedisByFastJSON(String key, Object obj) {
		String jsonString = JSON.toJSONString(obj);
		redisTemplate.opsForValue().set(key, jsonString);
	}

	@Override
	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj, SerializeFilter filter) {
		String jsonString = JSON.toJSONString(obj,filter);
		redisTemplate.opsForValue().set(key, jsonString);
	}

	@Override
	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj, String... inproperties) {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(inproperties);
		String jsonString = JSON.toJSONString(obj,filter);
		redisTemplate.opsForValue().set(key, jsonString);
	}

	@Override
	public String GetJSONStringFromRedis(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void deleteJSONStringFromRedisByKey(String key) {
		redisTemplate.delete(key);
	}

	
	
}
