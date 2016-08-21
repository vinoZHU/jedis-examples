import redis.clients.jedis.Jedis;
import util.JedisUtil;

import java.util.*;

/**
 * Created by vino on 16/8/21.
 */
public class TestHash {
    public static void main(String[] args){
        System.out.println("==Hash==");
        Jedis jedis = JedisUtil.getPool().getResource();
        try {
            Map<String, String> pairs = new HashMap<String, String>();
            pairs.put("name", "Akshi");
            pairs.put("age", "2");
            pairs.put("sex", "Female");
            jedis.hmset("kid", pairs);
            List<String> name = jedis.hmget("kid", "name");// 结果是个泛型的LIST
            // jedis.hdel("kid","age"); //删除map中的某个键值
            System.out.println(jedis.hmget("kid", "pwd")); // 因为删除了，所以返回的是null
            System.out.println(jedis.hlen("kid")); // 返回key为user的键中存放的值的个数
            System.out.println(jedis.exists("kid"));// 是否存在key为user的记录
            System.out.println(jedis.hkeys("kid"));// 返回map对象中的所有key
            System.out.println(jedis.hvals("kid"));// 返回map对象中的所有value

            Iterator<String> iter = jedis.hkeys("kid").iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                System.out.println(key + ":" + jedis.hmget("kid", key));
            }

            List<String> values = jedis.lrange("messages", 0, -1);
            values = jedis.hmget("kid", new String[] { "name", "age", "sex" });
            System.out.println(values);
            Set<String> setValues = jedis.zrange("hackers", 0, -1);
            setValues = jedis.hkeys("kid");
            System.out.println(setValues);
            values = jedis.hvals("kid");
            System.out.println(values);
            pairs = jedis.hgetAll("kid");
            System.out.println(pairs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.getPool().returnResource(jedis);
        }

        // 清空数据
        System.out.println(jedis.flushDB());
        // 添加数据
        jedis.hset("hashs", "entryKey", "entryValue");
        jedis.hset("hashs", "entryKey1", "entryValue1");
        jedis.hset("hashs", "entryKey2", "entryValue2");
        // 判断某个值是否存在
        System.out.println(jedis.hexists("hashs", "entryKey"));
        // 获取指定的值
        System.out.println(jedis.hget("hashs", "entryKey")); // 批量获取指定的值
        System.out.println(jedis.hmget("hashs", "entryKey", "entryKey1"));
        // 删除指定的值
        System.out.println(jedis.hdel("hashs", "entryKey"));
        // 为key中的域 field 的值加上增量 increment
        System.out.println(jedis.hincrBy("hashs", "entryKey", 123l));
        // 获取所有的keys
        System.out.println(jedis.hkeys("hashs"));
        // 获取所有的values
        System.out.println(jedis.hvals("hashs"));
    }
}
