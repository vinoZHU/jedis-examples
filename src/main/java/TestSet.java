import redis.clients.jedis.Jedis;
import util.JedisUtil;

import java.util.Set;

/**
 * Created by vino on 16/8/21.
 */
public class TestSet {
    public static void main(String[] args){
        System.out.println("==Set==");
        Jedis jedis = JedisUtil.getPool().getResource();
        try {
            jedis.sadd("myset", "1");
            jedis.sadd("myset", "2");
            jedis.sadd("myset", "3");
            jedis.sadd("myset", "4");
            Set<String> setValues = jedis.smembers("myset");
            System.out.println(setValues);

            // 移除noname
            jedis.srem("myset", "4");
            System.out.println(jedis.smembers("myset"));// 获取所有加入的value
            System.out.println(jedis.sismember("myset", "4"));// 判断 minxr
            // 是否是sname集合的元素
            System.out.println(jedis.scard("sname"));// 返回集合的元素个数
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.getPool().returnResource(jedis);
        }

        // 清空数据
        System.out.println(jedis.flushDB());
        // 添加数据
        jedis.sadd("sets", "HashSet");
        jedis.sadd("sets", "SortedSet");
        jedis.sadd("sets", "TreeSet");
        // 判断value是否在列表中
        System.out.println(jedis.sismember("sets", "TreeSet"));

        // 整个列表值
        System.out.println(jedis.smembers("sets"));
        // 删除指定元素
        System.out.println(jedis.srem("sets", "SortedSet"));
        // 出栈
        System.out.println(jedis.spop("sets"));
        System.out.println(jedis.smembers("sets"));
        //
        jedis.sadd("sets1", "HashSet1");
        jedis.sadd("sets1", "SortedSet1");
        jedis.sadd("sets1", "TreeSet");
        jedis.sadd("sets2", "HashSet2");
        jedis.sadd("sets2", "SortedSet1");
        jedis.sadd("sets2", "TreeSet1");
        // 交集
        System.out.println(jedis.sinter("sets1", "sets2"));
        // 并集
        System.out.println(jedis.sunion("sets1", "sets2"));
        // 差集
        System.out.println(jedis.sdiff("sets1", "sets2"));
    }
}
