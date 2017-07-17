package cn.guwei.bos.test.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-redis.xml"})
public class SDR {

	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Test
	public void test1(){
		redisTemplate.opsForValue().set("小鱼", "haha", 60, TimeUnit.SECONDS);
	}
	
	@Test
	public void test2(){
		String val = redisTemplate.opsForValue().get("小鱼");
		System.out.println(val);
	}
	
}
