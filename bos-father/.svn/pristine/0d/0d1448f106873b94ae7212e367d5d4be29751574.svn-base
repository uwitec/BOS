package cn.guwei.bos.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolDemo {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();//加载配置文件
		
		poolConfig.setMaxTotal(20); //设置最大连接数，默认8个
		poolConfig.setMaxWaitMillis(10000);//获取连接等待时间
		
		JedisPool pool = new JedisPool(poolConfig,"localhost",6379);//创建连接池
		Jedis jedis = pool.getResource();//获得一个Jedis的会话对象
		
		String str = jedis.get("guwei");
		System.out.println(str);
		
		jedis.close();
		pool.close();
	}
}
