package cn.guwei.redis.utils;

import com.alibaba.fastjson.serializer.SerializeFilter;

/**
 * redis数据库操作json字符串的crud
 * 
 * 基于fastjson
 * 
 * @author vian
 *
 */
public interface RedisCRUD {

	public void writeJSONStringToRedis(String key,String jsonString);
	
	public void writeObjectToRedisByFastJSON(String key, Object obj);

	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj, SerializeFilter filter);

	public void writeObjectToRedisByFastJSONIncludes(String key, Object obj, String... inproperties);

	public String GetJSONStringFromRedis(String key);

	public void deleteJSONStringFromRedisByKey(String key);
}
