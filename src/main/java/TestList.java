import redis.clients.jedis.Jedis;
import util.JedisUtil;

import java.util.List;

/**
 * Created by vino on 16/8/21.
 */
public class TestList {
    public static void main(String[] args){
        System.out.println("==List==");
        Jedis jedis = JedisUtil.getPool().getResource();
        try {
            // 开始前，先移除所有的内容
            jedis.del("messages");
            jedis.rpush("messages", "Hello how are you?");
            jedis.rpush("messages", "Fine thanks. I'm having fun with redis.");
            jedis.rpush("messages", "I should look into this NOSQL thing ASAP");

            // 再取出所有数据jedis.lrange是按范围取出，
            // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
            List<String> values = jedis.lrange("messages", 0, -1);
            System.out.println(values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.getPool().returnResource(jedis);
        }

        // 清空数据
        System.out.println(jedis.flushDB());
        // 添加数据
        jedis.lpush("lists", "vector");
        jedis.lpush("lists", "ArrayList");
        jedis.lpush("lists", "LinkedList");
        // 数组长度
        System.out.println(jedis.llen("lists"));
        // 排序
        //System.out.println(jedis.sort("lists"));
        // 输出字串
        System.out.println(jedis.lrange("lists", 0, -1));
        // 修改列表中单个值
        jedis.lset("lists", 0, "hello list!");
        System.out.println(jedis.lrange("lists", 0, -1));
        // 获取列表指定下标的值
        System.out.println(jedis.lindex("lists", 1));
        // 删除列表指定下标的值
        System.out.println(jedis.lrem("lists", 1, "ArrayList"));
        System.out.println(jedis.lrange("lists", 0, -1));
        // 删除区间以外的数据
        System.out.println(jedis.ltrim("lists", 0, 0));
        System.out.println(jedis.lrange("lists", 0, -1));
        // 列表出栈
        System.out.println(jedis.lpop("lists"));
        // 整个列表值
        System.out.println(jedis.lrange("lists", 0, -1));
    }
}
