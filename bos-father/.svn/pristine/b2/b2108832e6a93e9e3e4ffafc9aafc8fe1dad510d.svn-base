package cn.guwei.bos.test.redis;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

public class JedisDemo {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost",6379);
		
		jedis.set("name", "guwei");
		System.out.println(jedis.get("name"));
		
		
		jedis.close();
	}
}
