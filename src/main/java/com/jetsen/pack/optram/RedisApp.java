package com.jetsen.pack.optram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApp {

	public static void main(String[] args) {
	    SpringApplication.run(RedisApp.class,args);
	}
	
/*	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.137.135",6379);
		String hello = jedis.get("hello");
		System.out.println(hello);
		
		//connection pool
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL*5);
		poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE*3);
		poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE*2);
		poolConfig.setMaxWaitMillis(3000);
		JedisPool jedisPool = new JedisPool(poolConfig,"192.168.137.135",6379);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.get("hello");
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(jedis != null){
				jedis.close();
			}
		}
		
	}*/

}
