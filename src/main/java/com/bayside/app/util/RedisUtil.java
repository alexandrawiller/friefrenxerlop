package com.bayside.app.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {
	private static ShardedJedisPool shardedJedisPool = null;
	/**
	 * <p>
	 * 方法名称：initialShardedPool
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @return
	 * @author yuangl
	 * @since 2016年5月25日
	 *        <p>
	 *        history 2016年5月25日 Administrator 创建
	 *        <p>
	 */
	public static ShardedJedis initialShardedPool(String ip, int port, int db, String password) {
		ShardedJedis shardedJedis = null;
		try {
			// 池基本配置
			JedisPoolConfig config = initJedisCon();
			
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			// shards.add(new JedisShardInfo("192.168.1.88", 6379, "master"));
			// shards.add(new JedisShardInfo("27.54.248.197", 6379, "master"));
			JedisShardInfo jedisinfo = new JedisShardInfo(ip, port, "master");
			jedisinfo.setConnectionTimeout(150000);
			jedisinfo.setSoTimeout(150000);
			if (password != null && !"".equals(password)) {
				jedisinfo.setPassword(password);
			}
			shards.add(jedisinfo);
			// 构造池
			if(shardedJedisPool!=null){
				shardedJedisPool.destroy();
			}
			ShardedJedisPool shardedJedisPool = new ShardedJedisPool(config, shards);
			shardedJedis = shardedJedisPool.getResource();
			// 选择数据库
			Collection<Jedis> collection = shardedJedis.getAllShards();
			Iterator<Jedis> jedis = collection.iterator();
			while (jedis.hasNext()) {
				jedis.next().select(db);// 选择db
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return shardedJedis;
	}

	/**
	 * <p>
	 * 方法名称：initialJedisPool
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @return
	 * @author sql
	 * @since 2016年5月25日
	 *        <p>
	 *        history 2016年5月25日 sql 创建
	 *        <p>
	 */
	public static JedisPool initialJedisPool() {
		// 池基本配置
		JedisPoolConfig config = initJedisCon();

		// JedisPool jedisPool = new JedisPool(config,"192.168.1.88",6379);
		JedisPool jedisPool = new JedisPool(config, "47.93.114.131", 6111);
		return jedisPool;
	}

	public static JedisPoolConfig initJedisCon() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(20);
		config.setMinIdle(5);
		config.setMaxWaitMillis(10000);
		config.setMaxTotal(500);
		config.setTestOnBorrow(false);
		return config;
	}

	public static void main(String[] args) {
		// ShardedJedis shardedJedis = RedisUtil.initialShardedPool();
		// System.out.println(shardedJedis.hget("weibodomainurls",
		// "1087770692"));
		Map<String, String> map = initialShardedPool("47.93.114.131", 6111, 0, "").hgetAll("subject");
		System.out.println(map);
	}
}
