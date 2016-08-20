import redis.clients.jedis.Jedis;
import util.JedisUtil;

import java.util.List;

/**
 * Created by vino on 16/8/20.
 */
public class HelloJedis {
    public void hello(){
        Jedis redis = new Jedis ("127.0.0.1",6379);//连接redis
        //redis.auth("redis");//验证密码,如果需要验证的话

        /**
         * set key value
         */
        redis.set("name", "vino");
        redis.set("id", "123");
        redis.set("address", "hangzhou");

        /**
         * setex key seconds value(key的生存时间设为seconds)
         */
        redis.setex("foo", 5, "just 5 seconds");

        /**
         * mset key value [key value ...]同时设置一个或多个key-value对。
         */
        redis.mset("mkey1","111","mkey2","222");

        /**
         * 清空所有的key
         */
        //redis.flushAll();

        /**
         * dbSize是key的数量
         */
        System.out.println(redis.dbSize());

        /**
         *append key value
         */
        redis.append("foo", "00");

        /**
         * get key 返回key的value值
         */
        redis.get("foo");

        /**
         * mget key [key ...] 返回所有(一个或多个)给定key的值
         */
        List list = redis.mget("mkey1","mkey2");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
    public static void main(String[] args) {
        HelloJedis helloJedis = new HelloJedis();
        helloJedis.hello();

    }
}

