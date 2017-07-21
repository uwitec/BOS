package cn.guwei.bos.test.redis.utils;



import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings("all")
public class JedisUtil {
    private static int maxTotal = 0;
    private static long maxWaitMillis = 0;
    private static String host = null;
    private static int port = 0;
    
    private static JedisPoolConfig  jedisPoolConfig = null;
    private static JedisPool jedisPool = null;
    
    static{
    	ResourceBundle rb = ResourceBundle.getBundle("redis");
    	int maxTotal = Integer.parseInt(rb.getString("maxTotal"));
    	int port = Integer.parseInt(rb.getString("port"));
    	long maxWaitMillis = Long.parseLong(rb.getString("maxWaitMillis"));
    	String host = rb.getString("host");
    	
    	jedisPoolConfig = new JedisPoolConfig();
    	jedisPoolConfig.setMaxTotal(maxTotal);
    	jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
    	jedisPool = new JedisPool(jedisPoolConfig,host,port);
    	
    }
    
    /**
     * 获取jedis会话
     * @return
     */
    public static Jedis getJedis(){
    	return jedisPool.getResource();
    }

    /**
     * 关闭jedis会话
     * @param j
     */
    public static void close(Jedis j){
    	if (j!=null) {
    		j.close();
		}
    }
    
    public static void main(String[] args) {
		System.out.println(getJedis());
	}
}
