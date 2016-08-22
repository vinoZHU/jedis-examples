import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import util.JedisUtil;

import java.util.Date;

/**
 * Created by vino on 16/8/21.
 */
public class TestPipeline {
    public static void main(String[] args){
        unusePipeline();

        usePipeline();
    }
    private static void usePipeline(){
        long start = new Date().getTime();

        Jedis jedis = JedisUtil.getPool().getResource();
        jedis.flushDB();
        Pipeline p = jedis.pipelined();
        for (int i = 0; i < 10000; i++) {
            p.set("age2" + i, i + "");
            System.out.println(p.get("age2" + i));
        }
        p.sync();// 这段代码获取所有的response

        long end = new Date().getTime();

        System.out.println("use pipeline cost:" + (end - start) + "ms");

        JedisUtil.getPool().returnResource(jedis);
    }
    private static void unusePipeline(){
        long start = new Date().getTime();

        Jedis jedis = JedisUtil.getPool().getResource();
        for (int i = 0; i < 10000; i++) {
            jedis.set("age1" + i, i + "");
            jedis.get("age1" + i);// 每个操作都发送请求给redis-server
        }
        long end = new Date().getTime();

        System.out.println("unuse pipeline cost:" + (end - start) + "ms");

        JedisUtil.getPool().returnResource(jedis);
    }
}
